package com.example.music.server;

import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NotificationUtils;
import com.example.music.Interface.OnDownLoadListener;
import com.example.music.R;
import com.example.music.http.Api;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.DownLoadInfo;
import com.example.music.model.DownLoadProgree;
import com.example.music.ui.MyApplication;
import com.example.music.ui.activity.DownloadCenterActivity;
import com.example.music.utils.greendao.DaoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Create By morningsun  on 2020-06-11
 */
public class TaskDispatcher {
    //Cpu核心个数
    private static final int CPU_NUM = Runtime.getRuntime().availableProcessors();
    private static final int CPRE_POOL_SIZE = CPU_NUM + 1;
    //最大下载任务数量
    private static final int DOWNLOAD_MAX = CPU_NUM * 2 + 1;
    //下载任务线程池
    private ExecutorService executorService;
    //正在下载的任务队列
    private Map<Long, List<DownloadTask>> queueTaskMap = Collections.synchronizedMap(new HashMap<Long, List<DownloadTask>>());
    //已经完成下载任务队列
    private List<Long> waitloaded = Collections.synchronizedList(new ArrayList<Long>());
    //已经完成下载任务队列
    private List<Long> downloaded = Collections.synchronizedList(new ArrayList<Long>());
    //单例对象
    private static TaskDispatcher instance;
    //数据库操作对象
    private DaoUtils daoUtils;
    //下载监听
    private OnDownLoadListener onDownLoadListener;



    private TaskDispatcher() {
        daoUtils = new DaoUtils(MyApplication.getContext());
        EventBus.getDefault().register(this);
    }

    /**
     * 线程安全单例模式
     */
    public static TaskDispatcher getInstance() {
        if (instance == null) {
            synchronized (TaskDispatcher.class) {
                if (instance == null) {
                    instance = new TaskDispatcher();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化线程池
     */
    private ExecutorService getExecutorService() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(CPRE_POOL_SIZE, DOWNLOAD_MAX,
                    60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                    Executors.defaultThreadFactory());
        }

        return executorService;
    }


    public synchronized void addListen(OnDownLoadListener onDownLoadListener) {
        this.onDownLoadListener = onDownLoadListener;
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void getDownLoadInfo(DownLoadProgree downLoadProgree) {
        Intent intent = new Intent(MyApplication.getContext(), DownloadCenterActivity.class);
        DownLoadInfo downLoadInfo = downLoadProgree.getDownLoadInfo();
        switch (downLoadProgree.getState()) {
            case DownloadTask.PENDING:
                //任务待进行
                break;
            case DownloadTask.LOADING:
                //任务准备中
                downLoadInfo.setState(DownloadTask.LOADING);
                daoUtils.updateDownload(downLoadInfo);
                NotificationUtils.create(MyApplication.getContext(), downLoadInfo.getId().intValue(), intent, R.drawable.ic_icon, "下载通知", downLoadInfo.getFilename() + "等待下载");
                if (onDownLoadListener != null) {
                    onDownLoadListener.OnLOADING(downLoadInfo);
                }
                break;
            case DownloadTask.PROGREE:
                //任务下载中
                int downloadSize = downLoadInfo.getDownloadSize() + downLoadProgree.getDownloadSize();
                downLoadInfo.setDownloadSize(downloadSize);
                downLoadInfo.setState(DownloadTask.PROGREE);
                daoUtils.updateDownload(downLoadInfo);
                NotificationUtils.create(MyApplication.getContext(), downLoadInfo.getId().intValue(), intent, R.drawable.ic_icon,
                        "下载通知", downLoadInfo.getFilename() + "正在下载,当前进度:" + downloadSize * 100 / downLoadInfo.getTotalSize() + "%");
                if (onDownLoadListener != null) {
                    onDownLoadListener.onProgree(downLoadInfo, downLoadInfo.getDownloadSize(), downLoadInfo.getTotalSize());
                }
                break;
            case DownloadTask.STOP:
                //任务停止
                downLoadInfo.setState(DownloadTask.STOP);
                daoUtils.updateDownload(downLoadInfo);
                NotificationUtils.create(MyApplication.getContext(), downLoadInfo.getId().intValue(), intent, R.drawable.ic_icon,
                        "下载通知", downLoadInfo.getFilename() + "停止下载,当前进度:" + downLoadInfo.getDownloadSize() * 100 / downLoadInfo.getTotalSize() + "%");
                if (onDownLoadListener != null) {
                    onDownLoadListener.onStop(downLoadInfo, downLoadInfo.getDownloadSize(), downLoadInfo.getTotalSize());
                }
                break;
            case DownloadTask.FAILED:
                //任务失败
                downLoadInfo.setState(DownloadTask.FAILED);
                daoUtils.updateDownload(downLoadInfo);
                NotificationUtils.create(MyApplication.getContext(), downLoadInfo.getId().intValue(), intent, R.drawable.ic_icon,
                        "下载通知", downLoadInfo.getFilename() + "下载失败");
                if (onDownLoadListener != null) {
                    onDownLoadListener.onFailed(downLoadInfo);
                }
                queueTaskMap.get(downLoadInfo.getId()).get(downLoadProgree.getTaskId().intValue()).setStop(true);
                break;
            case DownloadTask.FINISHED:
                //任务完成
                if (downLoadInfo.getDownloadSize() == downLoadInfo.getTotalSize()) {
                    finished(downLoadInfo.getId());
                    downLoadInfo.setState(DownloadTask.FINISHED);
                    daoUtils.updateDownload(downLoadInfo);
                    NotificationUtils.create(MyApplication.getContext(), downLoadInfo.getId().intValue(), intent, R.drawable.ic_icon,
                            "下载通知", downLoadInfo.getFilename() + "下载完成");
                    if (onDownLoadListener != null) {
                        onDownLoadListener.onComplet(downLoadInfo);
                    }
                }
                break;
            default:
                break;
        }
    }



    /**
     * 任务入列下载
     */
    public synchronized boolean enqueue(final Long id) {
        if (queueTaskMap.keySet().contains(id)) {
            return false;
        }
        final DownLoadInfo downLoadInfo = daoUtils.queryDownlodInfo(id);
        Api.getInstance().iRetrofit.download(downLoadInfo.getUrl(), "bytes=" + 0 + "-")
                .compose(ApiSubscribe.<ResponseBody>io_io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        int totalSize = (int) responseBody.contentLength();
                        int rateSzie = totalSize / 3;
                        downLoadInfo.setTotalSize(totalSize);
                        downLoadInfo.setState(DownloadTask.PENDING);
                        daoUtils.updateDownload(downLoadInfo);

                        List<DownloadTask> downloadTasks = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            DownloadTask downloadTask;
                            DownLoadProgree downLoadProgree = new DownLoadProgree();
                            downLoadProgree.setTaskId(downLoadInfo.getId());
                            downLoadProgree.setDownLoadInfo(downLoadInfo);
                            if (i == 2) {
                                downLoadProgree.setStart(i * rateSzie);
                                downLoadProgree.setEnd(totalSize);
                                downloadTask = new DownloadTask(daoUtils.insertDownloadTask(downLoadProgree));
                            } else {
                                downLoadProgree.setStart(i * rateSzie);
                                downLoadProgree.setEnd((i + 1) * rateSzie-1);
                                downloadTask = new DownloadTask( daoUtils.insertDownloadTask(downLoadProgree));
                            }
                            if (queueTaskMap.entrySet().size() < DOWNLOAD_MAX) {
                                getExecutorService().execute(downloadTask);
                                downloadTasks.add(downloadTask);
                            }else{
                                waitloaded.add(id);
                                break;
                            }
                        }
                        queueTaskMap.put(downLoadInfo.getId(),downloadTasks);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return true;
    }

    /**
     * 多个任务入列下载
     */
    public synchronized boolean enqueueAll(List<Long> idList) {
        for (Long id : idList) {
            enqueue(id);
        }
        return true;
    }



    /**
     * 任务停止
     */
    public synchronized void stop(long id) {
        List<DownloadTask> downloadTasks = queueTaskMap.get(id);
        for (DownloadTask downloadTask:downloadTasks){
            downloadTask.setStop(true);
        }
        promoteSyncTask();
    }

    /**
     * 任务下载完成
     */
    synchronized void finished(long id ) {
        queueTaskMap.remove(id);
        downloaded.add(id);
        promoteSyncTask();
    }


    /**
     * 删除下载任务，是否删除文件
     */
    public synchronized void deleteTask(DownLoadInfo downLoadInfo, boolean isDeleteFile) {
        if (downLoadInfo != null) {
            stop(downLoadInfo.getId());
            if (downloaded.contains(downLoadInfo.getId())) {
                downloaded.remove(downLoadInfo.getId());
                if (isDeleteFile) {
                    File file = new File(downLoadInfo.getFilepath(), downLoadInfo.getFilename());
                    file.delete();
                }
            }
            if (queueTaskMap.keySet().contains(downLoadInfo.getId())) {
                queueTaskMap.remove(downLoadInfo.getId());
                if (isDeleteFile) {
                    File file = new File(downLoadInfo.getFilepath(), downLoadInfo.getFilename());
                    file.delete();
                }
//                promoteSyncTask();
            }
        }
    }

    /**
     * 调度pending状态的任务，开始下载
     */
    private synchronized void promoteSyncTask() {
       if(waitloaded.isEmpty()){
           enqueueAll(waitloaded);
       }
    }


}

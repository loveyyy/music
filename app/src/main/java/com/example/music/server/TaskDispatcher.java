package com.example.music.server;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NotificationUtils;
import com.example.music.Interface.OnDownLoadListener;
import com.example.music.R;
import com.example.music.http.Api;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.DownLoadInfo;
import com.example.music.ui.MyApplication;
import com.example.music.ui.activity.DownloadCenterActivity;
import com.example.music.utils.greendao.DaoUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
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
    private Map<Long, DownloadTask> queueTaskMap = Collections.synchronizedMap(new HashMap<Long, DownloadTask>());
    //已经完成下载任务队列
    private List<Long> downloadedMap = Collections.synchronizedList(new ArrayList<Long>());
    //单例对象
    private static TaskDispatcher instance;
    //数据库操作对象
    private DaoUtils daoUtils;
    //下载监听
    private OnDownLoadListener onDownLoadListener;

    //轮训
    private Timer timer;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            DownLoadInfo downLoadInfo = daoUtils.queryDownlodInfo((Long) msg.obj);
            Intent intent = new Intent(MyApplication.getContext(), DownloadCenterActivity.class);
            switch (downLoadInfo.getState()) {
                case DownloadTask.PENDING:
                    //任务待进行
                    break;
                case DownloadTask.LOADING:
                    //任务准备中
                    NotificationUtils.create(MyApplication.getContext(),downLoadInfo.getId().intValue(),intent,R.drawable.ic_icon,"下载通知", downLoadInfo.getFilename() + "等待下载");
                    if (onDownLoadListener != null) {
                        onDownLoadListener.OnLOADING(downLoadInfo);
                    }
                    break;
                case DownloadTask.PROGREE:
                    //任务下载中
                    NotificationUtils.create(MyApplication.getContext(),downLoadInfo.getId().intValue(),intent,R.drawable.ic_icon,
                            "下载通知", downLoadInfo.getFilename() + "正在下载,当前进度:" + downLoadInfo.getDownloadsize() * 100 / downLoadInfo.getSize() + "%");
                    if (onDownLoadListener != null) {
                        onDownLoadListener.onProgree(downLoadInfo, downLoadInfo.getDownloadsize(), downLoadInfo.getSize());
                    }
                    break;
                case DownloadTask.STOP:
                    //任务停止
                    NotificationUtils.create(MyApplication.getContext(),downLoadInfo.getId().intValue(),intent,R.drawable.ic_icon,
                            "下载通知", downLoadInfo.getFilename() + "停止下载,当前进度:" + downLoadInfo.getDownloadsize() * 100 / downLoadInfo.getSize() + "%");
                    if (onDownLoadListener != null) {
                        onDownLoadListener.onStop(downLoadInfo, downLoadInfo.getDownloadsize(), downLoadInfo.getSize());
                    }

                    break;
                case DownloadTask.FAILED:
                    //任务失败
                    NotificationUtils.create(MyApplication.getContext(),downLoadInfo.getId().intValue(),intent,R.drawable.ic_icon,
                            "下载通知", downLoadInfo.getFilename() + "下载失败");
                    if (onDownLoadListener != null) {
                        onDownLoadListener.onFailed(downLoadInfo);
                    }
                    queueTaskMap.get(downLoadInfo.getId()).setStop(true);
                    break;
                case DownloadTask.FINISHED:
                    //任务完成
                    finished(downLoadInfo);
                    NotificationUtils.create(MyApplication.getContext(),downLoadInfo.getId().intValue(),intent,R.drawable.ic_icon,
                            "下载通知", downLoadInfo.getFilename() + "下载完成");
                    if (onDownLoadListener != null) {
                        onDownLoadListener.onComplet(downLoadInfo);
                    }
                    break;
                default:
                    break;
            }
        }
    };


    private TaskDispatcher() {
        daoUtils = new DaoUtils(MyApplication.getContext());
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

    /**
     * 任务入列下载
     */
    public synchronized boolean enqueue(final Long id) {
        try {
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
                            downLoadInfo.setSize(totalSize);
                            daoUtils.updateDownload(downLoadInfo);
                            DownloadTask downloadTask = new DownloadTask(id);
                            getExecutorService().execute(downloadTask);
                            queueTaskMap.put(id, downloadTask);
                            startDownLoad();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });


//            URL url = new URL(downLoadInfo.getUrl());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setRequestMethod("GET");
//            int totalSize = conn.getContentLength();
//            int rateSzie = totalSize / 3;
//            conn.disconnect();


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
//        startDownLoad();
        return true;
    }

    /**
     * 任务入列下载
     */
//    private synchronized boolean enqueue(DownLoadInfo downLoadInfo) {
//        try {
//            URL url = new URL(downLoadInfo.getUrl());
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5000);
//            conn.setRequestMethod("GET");
//            int totalSize;
//            int rateSzie;
//            if (conn.getResponseCode() == 200) {
//                totalSize = conn.getContentLength();
//                rateSzie = totalSize / 3;
//            } else {
//                //连接错误
//                conn.disconnect();
//                return false;
//            }
//            conn.disconnect();
//            List<DownloadTask> downloadTasks = new ArrayList<>();
//            for (int i = 0; i < 3; i++) {
//                DownloadTask downloadTask;
//                if (i + 1 == 3) {
//                    downloadTask = new DownloadTask(i * rateSzie, totalSize, i, downLoadInfo.getId());
//                } else {
//                    downloadTask = new DownloadTask(i * rateSzie, (i + 1) * rateSzie, i, downLoadInfo.getId());
//                }
//                if (queueTaskMap.entrySet().size() < DOWNLOAD_MAX) {
//                    getExecutorService().execute(downloadTask);
//                    downloadTasks.add(downloadTask);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        startDownLoad();
//        return true;
//    }

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
     * 开启轮训数据库
     */

    private void startDownLoad() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!queueTaskMap.entrySet().isEmpty()) {
                    for (Map.Entry<Long, DownloadTask> entry : queueTaskMap.entrySet()) {
                        Message message = new Message();
                        message.obj = entry.getKey();
                        handler.sendMessage(message);
                    }
                } else {
                    timer.cancel();
                    timer=null;
                }
            }
        }, 0, 1000);

    }


    /**
     * 任务停止
     */
    public synchronized void stop(long id) {
        DownloadTask downloadTasks = queueTaskMap.get(id);
        if (downloadTasks.getDownLoadInfo().getState() == DownloadTask.PROGREE)
            downloadTasks.setStop(true);
            promoteSyncTask();
    }

    /**
     * 任务下载完成
     */
    synchronized void finished(DownLoadInfo downLoadInfo) {
        queueTaskMap.remove(downLoadInfo.getId());
        downloadedMap.add(downLoadInfo.getId());
        promoteSyncTask();
    }


    /**
     * 删除下载任务，是否删除文件
     */
    public synchronized void deleteTask(DownLoadInfo downLoadInfo, boolean isDeleteFile) {
        if (downLoadInfo != null) {
            if (downloadedMap.contains(downLoadInfo.getId())) {
                downloadedMap.remove(downLoadInfo.getId());
                if (isDeleteFile) {
                    File file = new File(downLoadInfo.getFilepath(), downLoadInfo.getFilename());
                    file.delete();
                }
            }
            if (queueTaskMap.keySet().contains(downLoadInfo.getId())) {
                queueTaskMap.remove(downLoadInfo.getId());
                queueTaskMap.get(downLoadInfo.getId()).setStop(true);
                if (isDeleteFile) {
                    File file = new File(downLoadInfo.getFilepath(), downLoadInfo.getFilename());
                    file.delete();
                }
                promoteSyncTask();
            }
        }
    }

    /**
     * 调度pending状态的任务，开始下载
     */
    private synchronized void promoteSyncTask() {
        for (Map.Entry<Long, DownloadTask> task : queueTaskMap.entrySet()) {
            DownLoadInfo downLoadInfo = daoUtils.queryDownlodInfo(task.getKey());
            if (downLoadInfo.getState() == DownloadTask.PENDING) {
                enqueue(downLoadInfo.getId());
            }
        }
    }

}

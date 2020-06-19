package com.example.music.server;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.NotificationUtils;
import com.example.music.Interface.OnDownLoadListener;
import com.example.music.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create By morningsun  on 2020-06-11
 */
public class TaskDispatcher  implements OnDownLoadListener {
    //最大下载任务数量
    private static final int DOWNLOAD_MAX =3;
    //下载任务线程池
    private ExecutorService executorService;
    // 任务不能重复
    private List<String> taskIdSet = Collections.synchronizedList(new ArrayList<String>());
    // 任务不能重复
    private List<String> taskLoadIdSet = Collections.synchronizedList(new ArrayList<String>());

    //正在下载的任务队列
    private List<DownloadTask> queueTaskList = Collections.synchronizedList(new ArrayList<DownloadTask>());
    //已经完成下载任务队列
    private List<DownloadTask> downloadedList = Collections.synchronizedList(new ArrayList<DownloadTask>());
    //单例对象
    private static TaskDispatcher instance;


    private TaskDispatcher() {

    }
    /**
     *线程安全单例模式
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
            executorService = new ThreadPoolExecutor(0, DOWNLOAD_MAX,
                    60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
                    threadFactory("happybaby download dispatcher", false));
        }
        return executorService;
    }

    /**
     * 任务入列下载
     */
     public synchronized boolean enqueue(DownloadTask task) {
        try {
            if (taskIdSet.contains(task.getDownLoadInfo().getUrl())||taskLoadIdSet.contains(task.getDownLoadInfo().getUrl())) {
                return false;
            }
            task.setOnDownLoadListener(this);
            if (queueTaskList.size() < DOWNLOAD_MAX) {
                getExecutorService().execute(task);
                queueTaskList.add(task);
                taskIdSet.add(task.getDownLoadInfo().getUrl());
            } else {
                task.setState(DownloadTask.PENDING);
                queueTaskList.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public synchronized boolean enqueueAll(List<DownloadTask> task) {
        try {
            for (DownloadTask downloadTask : task) {
                if (taskIdSet.contains(downloadTask.getDownLoadInfo().getId())) {
                    stop(downloadTask);
                    return false;
                }
                if (queueTaskList.size() < DOWNLOAD_MAX) {
                    URL url = new URL(downloadTask.getDownLoadInfo().getUrl());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    //保存文件信息
                    taskIdSet.add(downloadTask.getDownLoadInfo().getUrl());
                    getExecutorService().execute(downloadTask);
                } else {
                    downloadTask.setState(DownloadTask.PENDING);
                }
                queueTaskList.add(downloadTask);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 任务停止
     */
    public synchronized void stop(DownloadTask task) {
        if (task!=null &&taskIdSet.contains(task.getDownLoadInfo().getId()) &&task.getState() == DownloadTask.LOADING) {
            task.setStop(true);
            promoteSyncTask();
        }
    }



    /**
     * 任务下载完成
     */
    synchronized void finished(DownloadTask task) {
        if (task != null && task.getState() == DownloadTask.FINISHED) {
            if (queueTaskList.remove(task)) {
                taskIdSet.remove(task.getDownLoadInfo().getUrl());
                taskLoadIdSet.add(task.getDownLoadInfo().getUrl());
                downloadedList.add(task);
                promoteSyncTask();
            }
        }
    }
    /**
     * 删除下载任务，是否删除文件
     */
    public synchronized void deleteTask(DownloadTask task, boolean isDeleteFile) {
        if (task != null) {
            if (task.getState() != DownloadTask.FINISHED) {
                queueTaskList.remove(task);
                promoteSyncTask();
                return;
            }
            downloadedList.remove(task);
        }
    }

    /**
     * 调度pending状态的任务，开始下载
     */
    private synchronized void promoteSyncTask() {
        for (DownloadTask task : queueTaskList) {
            if (task.getState() == DownloadTask.PENDING&&!taskIdSet.contains(task.getDownLoadInfo().getUrl())) {
                taskIdSet.add(task.getDownLoadInfo().getUrl());
                getExecutorService().execute(task);
                return;
            }
        }
    }




    public List<DownloadTask> getQueueTaskList() {
        return queueTaskList;
    }

    public List<DownloadTask> getDownloadedList() {
        return downloadedList;
    }


    private ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r, name);
                thread.setDaemon(daemon);
                return thread;
            }
        };
    }

    @Override
    public void OnIDEL(DownloadTask downloadTask) {

    }

    @Override
    public void onPending(DownloadTask downloadTask) {

    }

    @Override
    public void OnLOADING(DownloadTask downloadTask) {
        NotificationUtils.create(R.drawable.ic_icon,"下载通知",downloadTask.getDownLoadInfo().getFilename()+"等待下载");
    }

    @Override
    public void onProgree(DownloadTask downloadTask, int start, int size) {
        if(size!=0){
            NotificationUtils.create(R.drawable.ic_icon,
                    "下载通知",downloadTask.getDownLoadInfo().getFilename()+"正在下载,当前进度:"+start*100/size+"%");
        }
    }

    @Override
    public void onStop(DownloadTask downloadTask, int start, int size) {
        NotificationUtils.create(R.drawable.ic_icon,
                "下载通知",downloadTask.getDownLoadInfo().getFilename()+"停止下载,当前进度:"+start*100/size+"%");
    }

    @Override
    public void onComplet(DownloadTask downloadTask) {
        NotificationUtils.create(R.drawable.ic_icon,
                "下载通知",downloadTask.getDownLoadInfo().getFilename()+"下载完成");
    }

    @Override
    public void onFailed(DownloadTask downloadTask, String message) {
        NotificationUtils.create(R.drawable.ic_icon,
                "下载通知",downloadTask.getDownLoadInfo().getFilename()+"下载失败");
    }

}

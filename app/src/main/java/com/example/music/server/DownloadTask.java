package com.example.music.server;

import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.Interface.OnDownLoadListener;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.DownLoadInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;

/**
 * Create By morningsun  on 2020-06-11
 */
public class DownloadTask implements Runnable {
    //下载任务状态
    private int state;
    private Long Tag;
    private DownLoadInfo downLoadInfo;
    private boolean Stop=false;
    private int downloadLength;
    //下载任务进度监听器
    private OnDownLoadListener onDownLoadListener;
    private DownloadTask mySelf;

    public static final int IDLE=0;
    public static final int PENDING=1;
    public static final int LOADING = 2;
    public static final int PROGREE = 3;
    public static final int STOP = 4;
    public static final int FAILED = 5;
    public static final int FINISHED = 6;

    @Override
    public void run() {
        start();
    }


    public DownloadTask() {
        state = IDLE;
        mySelf = this;
        if(onDownLoadListener!=null){
            onDownLoadListener.OnIDEL(this);
        }
    }

    public void start() {
        if (state == LOADING) {
            return;
        }
        state = LOADING;
        downLoadInfo.setDownloadsize(downloadLength);
        downLoadInfo.setState(LOADING);
        if(onDownLoadListener!=null){
            onDownLoadListener.OnLOADING(this);
        }

        File fileDir=new File(downLoadInfo.getFilepath());
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }

        File file = new File(downLoadInfo.getFilepath(), downLoadInfo.getFilename());
        if(file.exists()){
            if(file.length()==downLoadInfo.getSize()){
                file.delete();
            }else{
                downloadLength = (int) file.length();
            }
        }else{
            try {
                URL url = new URL(downLoadInfo.getUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                downLoadInfo.setSize(conn.getContentLength());
                downloadLength=0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        download();
    }

    private void download() {

        Api.getInstance().iRetrofit.download(downLoadInfo.getUrl(),"bytes=" + downloadLength + "-")
                .compose(ApiSubscribe.<ResponseBody>io_io())
                .subscribe(new ApiResponse<ResponseBody>() {
                    @Override
                    public void success(ResponseBody data) {
                        try {
                            File file = new File(downLoadInfo.getFilepath(), downLoadInfo.getFilename());
                            InputStream is = null;
                            FileOutputStream fileOutputStream = null;
                            try {
                                is = data.byteStream();
                                fileOutputStream = new FileOutputStream(file, true);
                                byte[] buffer = new byte[1024];//缓冲数组2kB
                                int len;
                                while ((len = is.read(buffer)) != -1) {
                                    if (!Stop) {
                                        fileOutputStream.write(buffer, 0, len);
                                        downloadLength += len;
                                        downLoadInfo.setDownloadsize(downloadLength);
                                        downLoadInfo.setState(PROGREE);
                                        if (onDownLoadListener != null) {
                                            onDownLoadListener.onProgree(DownloadTask.this, downloadLength, downLoadInfo.getSize());
                                        }
                                    } else {
                                        is.close();
                                        fileOutputStream.close();
                                        downLoadInfo.setDownloadsize(downloadLength);
                                        downLoadInfo.setState(STOP);
                                        if (onDownLoadListener != null) {
                                            onDownLoadListener.onStop(DownloadTask.this, downloadLength, downLoadInfo.getSize());
                                        }
                                        return;
                                    }
                                }
                                if (onDownLoadListener != null) {
                                    onDownLoadListener.onComplet(DownloadTask.this);
                                }
                                downLoadInfo.setDownloadsize(downloadLength);
                                downLoadInfo.setState(FINISHED);
                                mySelf.state = FINISHED;
                                TaskDispatcher.getInstance().finished(DownloadTask.this);
                                fileOutputStream.flush();
                            } finally {
                                //关闭IO流
                                is.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtils.e("下载出错" + e);
                            if (onDownLoadListener != null) {
                                onDownLoadListener.onFailed(DownloadTask.this, e.getMessage());
                            }
                            downLoadInfo.setState(FAILED);
                            mySelf.state = FAILED;
                        }
                    }
                });

    }


    public  void setState(final int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }


    public Long getTag() {
        return Tag;
    }

    public void setTag(Long tag) {
        Tag = tag;
    }


    public boolean isStop() {
        return Stop;
    }

    public void setStop(boolean stop) {
        Stop = stop;
    }

    public DownLoadInfo getDownLoadInfo() {
        return downLoadInfo;
    }

    public void setDownLoadInfo(DownLoadInfo downLoadInfo) {
        this.downLoadInfo = downLoadInfo;
    }


    public void setOnDownLoadListener(OnDownLoadListener onDownLoadListener){
        this.onDownLoadListener =onDownLoadListener;
    }
}

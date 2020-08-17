package com.example.music.server;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.http.Api;
import com.example.music.http.RxHelper;
import com.example.music.model.DownLoadProgree;
import com.example.music.ui.MyApplication;
import com.example.music.utils.greendao.DaoUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

import static okhttp3.internal.Util.closeQuietly;

/**
 * Create By morningsun  on 2020-06-11
 */
public class DownloadTask extends Thread {
    //任务信息
    private DownLoadProgree downLoadProgree;
    //是否停止
    private boolean Stop=false;
    //当前任务下载进度
    private int off=0;
    //数据库操作类
    private DaoUtils daoUtils;

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


    public DownloadTask(Long taskId) {
        daoUtils=new DaoUtils(MyApplication.getContext());
        downLoadProgree=daoUtils.queryDownlodTask(taskId);
    }

    public void start() {
        downLoadProgree.setState(LOADING);
        daoUtils.updateDownloadTask(downLoadProgree);
        download();
    }

    private void download() {
        Api.getInstance().iRetrofit.download(downLoadProgree.getDownLoadInfo().getUrl(),"bytes=" + downLoadProgree.getStart() + "-" + downLoadProgree.getEnd())
                .compose(RxHelper.observableIO2Io2(MyApplication.getContext()))
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            File file = new File(downLoadProgree.getDownLoadInfo().getFilepath(), downLoadProgree.getDownLoadInfo().getFilename());
                            InputStream is = null;
                            FileOutputStream fileOutputStream = null;
                            try {
                                is = responseBody.byteStream();
                                fileOutputStream = new FileOutputStream(file);
                                byte[] buffer = new byte[8192];//缓冲数组2kB
                                int len;
                                while ((len = is.read(buffer)) != -1) {
                                    if (!Stop) {
                                        fileOutputStream.write(buffer, 0, len);
                                        off+= len;
                                        //查询数据库
                                        downLoadProgree.setState(PROGREE);
                                        downLoadProgree.setDownloadSize(len);
                                        daoUtils.updateDownloadTask(downLoadProgree);
                                        EventBus.getDefault().post(downLoadProgree);
                                    } else {
                                        is.close();
                                        fileOutputStream.close();
                                        //更新数据库
                                        downLoadProgree.setState(STOP);
                                        downLoadProgree.setDownloadSize(0);
                                        downLoadProgree.setStart(off);
                                        daoUtils.updateDownloadTask(downLoadProgree);
                                        EventBus.getDefault().post(downLoadProgree);
                                        return;
                                    }
                                }
                                downLoadProgree.setState(FINISHED);
                                downLoadProgree.setDownloadSize(off);
                                daoUtils.updateDownloadTask(downLoadProgree);
                                EventBus.getDefault().post(downLoadProgree);
                            } finally {
                                //关闭IO流
                                is.close();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtils.e("下载出错" + e);
                            downLoadProgree.setState(FAILED);
                            downLoadProgree.setDownloadSize(0);
                            daoUtils.updateDownloadTask(downLoadProgree);
                            EventBus.getDefault().post(downLoadProgree);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        downLoadProgree.setState(FAILED);
                        downLoadProgree.setDownloadSize(0);
                        daoUtils.updateDownloadTask(downLoadProgree);
                        EventBus.getDefault().post(downLoadProgree);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void download1() {
        Api.getInstance().iRetrofit.download(downLoadProgree.getDownLoadInfo().getUrl(),"bytes=" + downLoadProgree.getStart() + "-" + downLoadProgree.getEnd())
                .compose(RxHelper.observableIO2Io2(MyApplication.getContext()))
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            File file = new File(downLoadProgree.getDownLoadInfo().getFilepath(), downLoadProgree.getDownLoadInfo().getFilename());
                            BufferedSource bufferedSource = null;
                            BufferedSink bufferedSink = null;
                            try {
                                bufferedSource = responseBody.source();
                                bufferedSink = Okio.buffer(Okio.sink(file));
                                byte[] buffer = new byte[8192];//缓冲数组2kB
                                int len;
                                while ((len = bufferedSource.read(buffer)) != -1) {
                                    if (!Stop) {
                                        bufferedSink.write(buffer);
                                        off+= len;
                                        //查询数据库
                                        downLoadProgree.setState(PROGREE);
                                        downLoadProgree.setDownloadSize(len);
                                        daoUtils.updateDownloadTask(downLoadProgree);
                                        EventBus.getDefault().post(downLoadProgree);
                                    } else {
                                        closeQuietly(bufferedSource);
                                        closeQuietly(bufferedSink);
                                        //更新数据库
                                        downLoadProgree.setState(STOP);
                                        downLoadProgree.setDownloadSize(0);
                                        downLoadProgree.setStart(off);
                                        daoUtils.updateDownloadTask(downLoadProgree);
                                        EventBus.getDefault().post(downLoadProgree);
                                        return;
                                    }
                                }
                                downLoadProgree.setState(FINISHED);
                                downLoadProgree.setDownloadSize(off);
                                daoUtils.updateDownloadTask(downLoadProgree);
                                EventBus.getDefault().post(downLoadProgree);
                            } finally {
                                //关闭IO流
                                closeQuietly(bufferedSource);
                                closeQuietly(bufferedSink);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtils.e("下载出错" + e);
                            downLoadProgree.setState(FAILED);
                            downLoadProgree.setDownloadSize(0);
                            daoUtils.updateDownloadTask(downLoadProgree);
                            EventBus.getDefault().post(downLoadProgree);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        downLoadProgree.setState(FAILED);
                        downLoadProgree.setDownloadSize(0);
                        daoUtils.updateDownloadTask(downLoadProgree);
                        EventBus.getDefault().post(downLoadProgree);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }





    public void setStop(boolean stop) {
        Stop = stop;
    }


}

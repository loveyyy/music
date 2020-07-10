package com.example.music.server;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.http.Api;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.DownLoadInfo;
import com.example.music.ui.MyApplication;
import com.example.music.utils.greendao.DaoUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Create By morningsun  on 2020-06-11
 */
public class DownloadTask extends Thread {
    //任务标记
    private Long Tag;
    //任务信息
    private DownLoadInfo downLoadInfo;
    //是否停止
    private boolean Stop=false;
    //当前任务下载起点
    private int start;
    //当前任务下载终点
    private int end;
    //获取任务index
    private int index;
    //读线程下 下载文件目录
    private  String filDir ;
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


    public DownloadTask(int start,int end,int index,long downLoadId) {
        filDir= MyApplication.getContext().getExternalFilesDir(null).getAbsolutePath();
        daoUtils=new DaoUtils(MyApplication.getContext());
        downLoadInfo=daoUtils.queryDownlodInfo(downLoadId);
        this.end=end;
        this.start=start;
        this.index=index;
    }

    public void start() {
        downLoadInfo.setState(LOADING);
        daoUtils.updateDownload(downLoadInfo);
        //文件存在  断点下载
        File file = new File(filDir,downLoadInfo.getFilename().split("\\.")[0]+"."+index+"."+downLoadInfo.getFilename().split("\\.")[1]);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            start = (int) file.length();
        }
        download();
    }

    private void download() {
        Api.getInstance().iRetrofit.download(downLoadInfo.getUrl(),"bytes=" + start + "-" + end)
                .compose(ApiSubscribe.<ResponseBody>io_io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            File file = new File(filDir, downLoadInfo.getFilename().split("\\.")[0]+"."+index+"."+downLoadInfo.getFilename().split("\\.")[1]);
                            InputStream is = null;
                            FileOutputStream fileOutputStream = null;
                            try {
                                is = responseBody.byteStream();
                                fileOutputStream = new FileOutputStream(file, true);
                                byte[] buffer = new byte[1024];//缓冲数组2kB
                                int len;
                                while ((len = is.read(buffer)) != -1) {
                                    if (!Stop) {
                                        fileOutputStream.write(buffer, 0, len);
                                        //查询数据库
                                        downLoadInfo.setState(PROGREE);
                                        downLoadInfo.setDownloadsize(downLoadInfo.getDownloadsize()+len);
                                        daoUtils.updateDownload(downLoadInfo);
                                    } else {
                                        is.close();
                                        fileOutputStream.close();
                                        //更新数据库
                                        downLoadInfo.setState(PROGREE);
                                        downLoadInfo.setDownloadsize(downLoadInfo.getDownloadsize()+len);
                                        daoUtils.updateDownload(downLoadInfo);
                                        return;
                                    }
                                }
                                DownLoadInfo downLoadInfo1=daoUtils.queryDownlodInfo(downLoadInfo.getId());
                                downLoadInfo.setState(FINISHED);
                                downLoadInfo.setDownloadsize(downLoadInfo1.getSize());
                                daoUtils.updateDownload(downLoadInfo);
                                fileOutputStream.flush();
                            } finally {
                                //关闭IO流
                                is.close();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtils.e("下载出错" + e);
                            DownLoadInfo downLoadInfo1=daoUtils.queryDownlodInfo(downLoadInfo.getId());
                            downLoadInfo1.setState(FAILED);
                            downLoadInfo1.setDownloadsize(0);
                            daoUtils.updateDownload(downLoadInfo1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

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
}

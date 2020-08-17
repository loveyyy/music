package com.example.music.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.music.Interface.MvInterface;
import com.example.music.http.Api;
import com.example.music.http.RxHelper;
import com.example.music.server.PlayMvServer;
import com.example.music.ui.MyApplication;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Create By morningsun  on 2020-06-30
 */
public class PlayMvController {
    //bind 服务相关
    private ServiceConnection serviceConnection;
    private MvInterface mi;
    private boolean isBind = false;
    private ACache aCache;
    private PlayController.BindSuccess bindSuccess;
    private static PlayMvController instance;
    private int videoW;
    private int videoH;


    /**
     *线程安全单例模式
     */
    public static PlayMvController getInstance() {
        if (instance == null) {
            synchronized (PlayController.class) {
                if (instance == null) {
                    instance = new PlayMvController();
                }
            }
        }
        return instance;
    }

    private PlayMvController() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mi = (PlayMvServer.MvController) service;
                if(bindSuccess!=null){
                    bindSuccess.OnBindSuccess();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                MyApplication.getContext().unbindService(serviceConnection);
            }
        };
        if(!isBind){
            Intent intent = new Intent(MyApplication.getContext(), PlayMvServer.class);
            isBind = MyApplication.getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        aCache=ACache.get(MyApplication.getContext());
    }


    public  int playOrPause(){
        if(mi!=null){
            return mi.playOrPause();
        }else{
            return 3;
        }

    }

    public  void setPro(int pos){
        if(mi!=null){
             mi.setPro(pos);
        }
    }



    public MediaPlayer getMp(){
        if(mi!=null){
            return mi.getMp();
        }
       return null;
    }

    public void PlayMv(final SurfaceView surfaceView, final String rid){
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                Api.getInstance().iRetrofit.mv_info(
                        "mp4|mkv",rid,"url",
                        "convert_url",String.valueOf(System.currentTimeMillis()),aCache.getAsString("reqid")).
                        compose(RxHelper.observableIO2Main(MyApplication.getContext())).subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            mi.playMv(responseBody.source().readUtf8(),holder);
                        } catch (IOException e) {
                            e.printStackTrace();
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

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                videoW=width;
                videoH=height;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mi.release();
            }
        });
    }



    public  interface BindSuccess{
        void OnBindSuccess();
    }

    public void SetOnBindSuccess(PlayController.BindSuccess bindSuccess){
        this.bindSuccess=bindSuccess;
    }



    public int getVideoW() {
        return videoW;
    }


    public int getVideoH() {
        return videoH;
    }

}

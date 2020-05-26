package com.example.music.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.music.Interface.MusicInterface;
import com.example.music.ui.custom.PlayerView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/5/17.
 */

public class PlayServer extends Service  {
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private int i = 0;
    private int state=0;
    private boolean isstop;
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        myBroadcastReceiver=new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.music.lrc");
        registerReceiver(myBroadcastReceiver, filter);
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicController();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.pause();
        timer.cancel();
        return super.onUnbind(intent);
    }

    private void init() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            i = 0;
        }
    }


    //必须继承binder，才能作为中间人对象返回
    public class MusicController extends Binder implements MusicInterface {

        @Override
            public void Play(String Musicuri) {
                i=0;
                init();
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(Musicuri);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.setLooping(false);
                            mediaPlayer.start();
                        }
                    });
                    starttime();
                    state=1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        @Override
        public void PlayWithButton() {
            if(mediaPlayer!=null){
                if(state==1){
                    mediaPlayer.pause();
                    state=2;
                }else{
                    mediaPlayer.start();
                    state=1;
                }
            }
        }

        @Override
        public int get_play_state() {
            return state;
        }

        @Override
        public int get_plat_pro() {
            return i;
        }


        @Override
        public void PlayWithSb(int progress) {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(progress*1000);
                i = progress;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(myBroadcastReceiver!=null){
            unregisterReceiver(myBroadcastReceiver);
        }
    }

    private void starttime(){
        if(timer!=null){
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isstop){
                    i++;
                    Intent intent=new Intent();
                    intent.setAction("com.example.music.pro");
                    intent.putExtra("pro",i);
                    sendOrderedBroadcast(intent,null);
                }
            }
        }, 0, 1000);
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这里的intent可以获取发送广播时传入的数据
            int pos=intent.getIntExtra("current",0);
            mediaPlayer.seekTo(pos*1000);
            i=pos;
        }
    }
}

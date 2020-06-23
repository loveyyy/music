package com.example.music.server;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.example.music.Interface.MusicInterface;
import com.example.music.R;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.ui.activity.MainAcvity;
import com.example.music.ui.custom.PlayerView;
import com.example.music.utils.NotificationUtils;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/5/17.
 */

public class PlayServer extends Service {
    private MediaPlayer mediaPlayer;
    //计时器
    private Timer timer;
    //当前进度
    private int i = 0;
    //当前播放状态
    public static  final int PLAYING=0;
    public static  final int PAUSE=1;
    public static  final int STOP=2;

    private int state=2;
    //广播
    private MyBroadcastReceiver myBroadcastReceiver;


    @Override
    public void onCreate() {
        super.onCreate();
        //注册广播
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
        if(timer!=null){
            timer.cancel();
        }
        return super.onUnbind(intent);
    }

    private void init() {
        //初始化
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
                    state=PLAYING;
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        @Override
        public int PlayOrStop() {
            if(mediaPlayer!=null){
                if(state==PLAYING){
                    mediaPlayer.pause();
                    state=PAUSE;
                    timer.cancel();
                }else{
                    mediaPlayer.start();
                    state=PLAYING;
                    starttime();
                }
            }else{
                state=STOP;
            }
            return  state;
        }

        @Override
        public int get_play_state() {
            if(mediaPlayer==null){
                state=STOP;
            }
            return state;
        }

        @Override
        public int get_plat_pro() {
            return i;
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
                    i++;
                    Intent intent=new Intent();
                    intent.setAction("com.example.music.pro");
                    intent.putExtra("pro",i);
                    sendOrderedBroadcast(intent,null);

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

package com.example.music.Server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

import com.example.music.Interface.MusicInterface;
import com.example.music.View.lrcText;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/5/17.
 */

public class PlayServer extends Service implements lrcText.ScrollChange {
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private int i = 0;
    private int duration;
    public static SendProgress sendProgressListener;
    private boolean isstop;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        lrcText.SetScrollChange(this);
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
        i = 0;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        if (timer == null) {
            timer = new Timer();
        }
    }
    @Override
    public void ScrollProgree(Double progree) {
        mediaPlayer.seekTo(progree.intValue());
        i = progree.intValue();
    }

    //必须继承binder，才能作为中间人对象返回
    public class MusicController extends Binder implements MusicInterface {

        @Override
        public void Play(String Musicuri, int  d) {
            duration=d;
            PlayMusic(Musicuri,duration);
        }

        @Override
        public int PlayWithButton() {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    isstop=true;
                    return  1;
                }else{
                    mediaPlayer.start();
                    isstop=false;
                    return 0;
                }
        }

        @Override
        public void next(String Musicuri) {
            PlayMusic(Musicuri,duration);
        }

        @Override
        public void last(String Musicuri) {
            PlayMusic(Musicuri,duration);
        }

        @Override
        public void PlayWithSb(int progress) {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(progress);
                i = progress;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void PlayMusic(String musicuri,final int duration) {
        init();
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicuri);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.setLooping(false);
                    mediaPlayer.start();
                    starttime();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void starttime(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!isstop){
                    i++;
                    sendProgressListener.sendP(i, duration);
                }
            }
        }, 0, 1000);
    }
    public interface SendProgress {
        void sendP(int progress, int time);
    }

    public static void getSendProgress(SendProgress sendProgress) {
        sendProgressListener = sendProgress;
    }
}

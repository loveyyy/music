package com.example.music.server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.view.SurfaceHolder;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.Interface.MvInterface;
import com.example.music.model.MvProgree;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Create By morningsun  on 2020-06-30
 */
public class PlayMvServer extends Service {
    private MediaPlayer mediaPlayer;
    //计时器
    private Timer timer;
    //当前进度
    private int i = 0;
    //当前播放状态
    public static final int PLAYING = 0;
    public static final int PAUSE = 1;
    public static final int STOP = 2;
    //歌曲长度
    private int duration;

    private int state = 2;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MvController();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        return super.onUnbind(intent);
    }

    private void init() {
        //初始化
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset();
        }
        i = 0;
    }

    //必须继承binder，才能作为中间人对象返回
    public class MvController extends Binder implements MvInterface {
        @Override
        public void playMv(String MusicUri, final SurfaceHolder surfaceHolder) {
            init();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(MusicUri);
                mediaPlayer.setDisplay(surfaceHolder);
                mediaPlayer.prepare();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.setLooping(true);
                        mediaPlayer.start();
                        duration = mediaPlayer.getDuration();
                        mediaPlayer.setScreenOnWhilePlaying(true);
                        startTask();
                        state = PLAYING;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int playOrPause() {
            if (mediaPlayer != null) {
                LogUtils.e(mediaPlayer.isPlaying());
                if (state == PLAYING) {
                    mediaPlayer.pause();
                    state = PAUSE;
                    timer.cancel();
                } else {
                    mediaPlayer.start();
                    state = PLAYING;
                    startTask();
                }
            } else {
                state = STOP;
            }
            return state;
        }

        @Override
        public void release() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }

        }

        @Override
        public void setPro(int pos) {
            LogUtils.e(mediaPlayer.isPlaying());
            if (state==PLAYING) {
                i = duration * pos / 100000;
                mediaPlayer.seekTo(duration * pos / 100);
            }
        }

        @Override
        public MediaPlayer getMp() {
            if (mediaPlayer == null) {
                init();
            }
            return mediaPlayer;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private void startTask() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                i++;
                int a = 0;
                if (duration > 0) {
                    if(i*1000>duration){
                        i=0;
                    }else{
                        a = i * 100000 / duration;
                    }
                }
                MvProgree mvProgree = new MvProgree();
                mvProgree.setPro(a);
                EventBus.getDefault().postSticky(mvProgree);
            }
        }, 0, 1000);
    }

}

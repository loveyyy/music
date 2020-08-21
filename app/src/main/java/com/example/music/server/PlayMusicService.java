package com.example.music.server;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.Interface.MusicInterface;
import com.example.music.Interface.State;
import com.example.music.model.PlayInfo;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/5/17.
 */

public class PlayMusicService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener {
    private MediaPlayer mediaPlayer;
    //计时器
    private Timer timer;
    //当前进度
    private int i = 0;
    //当前状态
    private State state = State.STOP;

    @Override
    public void onCreate() {
        super.onCreate();
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
        if (timer != null) {
            timer.cancel();
        }
        return super.onUnbind(intent);
    }

    private void init() {
        //初始化
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                state=State.CHANGE;
            }
            mediaPlayer.reset();
        }
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);

        i = 0;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(timer!=null){
            timer.cancel();
        }
        state = State.FINISH;
        push();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        if (percent == 100) {
            LogUtils.e("缓冲完成");
            state = State.PLAYING;
            startTask();
            push();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        LogUtils.e("错误" + what);
        state = State.ERROR;
        push();
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        startTask();
    }

    //必须继承binder，才能作为中间人对象返回
    public class MusicController extends Binder implements MusicInterface {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void play(String MusicUri) {
            init();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(MusicUri);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.setLooping(false);
                        mediaPlayer.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void playOrPause() {
            if (state== State.PLAYING) {
                mediaPlayer.pause();
                state = State.PAUSE;
                if (timer != null) {
                    timer.cancel();
                }
            } else {
                mediaPlayer.start();
                state = State.PLAYING;
                startTask();
            }
            push();
        }

        @Override
        public State playState() {
            return state;
        }

        @Override
        public int playPro() {
            return i;
        }

        @Override
        public void seek(int pos) {
            if (timer != null) {
                timer.cancel();
            }
            mediaPlayer.seekTo(pos * 1000);
            i = pos;
        }

        @Override
        public void stop() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                state = State.STOP;
            }
            if (timer != null) {
                timer.cancel();
            }
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
        if(timer!=null){
            timer.cancel();
        }

    }

    private void push() {
        EventBus.getDefault().postSticky(state);
    }

    private void startTask() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @SuppressLint("NewApi")
            @Override
            public void run() {
                i++;
                LogUtils.e(mediaPlayer.getCurrentPosition());
                PlayInfo playInfo=new PlayInfo();
                playInfo.setPos(i);
                playInfo.setState(state);
                EventBus.getDefault().postSticky(playInfo);
            }
        }, 0, 1000);
    }



}

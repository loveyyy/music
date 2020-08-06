package com.example.music.server;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.Interface.MusicInterface;
import com.example.music.model.PlayingMusicBeens;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2018/5/17.
 */

public class PlayMusicServer extends Service implements MediaPlayer.OnCompletionListener {
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
        if(timer!=null){
            timer.cancel();
        }
        return super.onUnbind(intent);
    }

    private void init() {
        //初始化
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }else{
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
        }
        i = 0;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        EventBus.getDefault().postSticky(0);
        timer.cancel();
    }

    //必须继承binder，才能作为中间人对象返回
    public class MusicController extends Binder implements MusicInterface {
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
                        mediaPlayer.setOnCompletionListener(PlayMusicServer.this);
                        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                            @Override
                            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                                if(percent==100){
                                    LogUtils.e("缓冲完成");
                                    state = PLAYING;
                                    startTask();
                                }

                            }
                        });
                       mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                           @Override
                           public boolean onError(MediaPlayer mp, int what, int extra) {
                               LogUtils.e("错误"+what);
                               return false;
                           }
                       });
                       mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                           @Override
                           public void onSeekComplete(MediaPlayer mp) {
                               startTask();
                           }
                       });
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int playOrPause() {
            if(mediaPlayer!=null){
                if(state==PLAYING){
                    mediaPlayer.pause();
                    state=PAUSE;
                    if(timer!=null){
                        timer.cancel();
                    }
                }else{
                    mediaPlayer.start();
                    state=PLAYING;
                    startTask();
                }
            }else{
                state=STOP;
            }
            return  state;
        }

        @Override
        public int getPlayState() {
            return state;
        }

        @Override
        public int getPlayPro() {
            return i;
        }

        @Override
        public void setPro(int pos) {
            if(timer!=null){
                timer.cancel();
            }
            mediaPlayer.seekTo(pos*1000);
            i=pos;
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    private void startTask(){
        if(timer!=null){
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                i++;
                EventBus.getDefault().postSticky(i);
            }
        }, 0, 1000);
    }
}

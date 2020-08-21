//package com.example.music.server;
//
//import android.app.Service;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Binder;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//
//import com.blankj.utilcode.util.LogUtils;
//import com.example.music.Interface.MusicInterface;
//import com.google.android.exoplayer2.ExoPlaybackException;
//import com.google.android.exoplayer2.PlaybackParameters;
//import com.google.android.exoplayer2.Player;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.Timeline;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.ProgressiveMediaSource;
//import com.google.android.exoplayer2.source.TrackGroupArray;
//import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.util.Util;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * Create By morningsun  on 2020-08-19
// */
//public class ExoPlayMusicServer extends Service implements Player.EventListener {
//
//    private SimpleExoPlayer simpleExoPlayer;
//    //计时器
//    private Timer timer;
//    //当前进度
//    private int i = 0;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        init();
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return new MusicController();
//    }
//
//
//    public void init(){
//        simpleExoPlayer=new SimpleExoPlayer.Builder(getApplication().getBaseContext())
//                .build();
//        simpleExoPlayer.setPlayWhenReady(true);
//        simpleExoPlayer.addListener(this);
//    }
//
//
//    //必须继承binder，才能作为中间人对象返回
//    public class MusicController extends Binder implements MusicInterface {
//        @Override
//        public void play(String MusicUri) {
//            if(simpleExoPlayer!=null){
//                init();
//                if (simpleExoPlayer.isPlaying()){
//                    simpleExoPlayer.stop(true);
//                }
//            }
//            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(),
//                    Util.getUserAgent(getApplicationContext(), "MyApplication"));
//            MediaSource videoSource =
//                    new ProgressiveMediaSource.Factory(dataSourceFactory)
//                            .createMediaSource(Uri.parse(MusicUri));
//            simpleExoPlayer.prepare(videoSource);
//        }
//
//        @Override
//        public void playOrPause() {
//            if(simpleExoPlayer.isPlaying()){
//                simpleExoPlayer.setPlayWhenReady(false);
//            }else{
//                simpleExoPlayer.setPlayWhenReady(true);
//            }
//
//            return  simpleExoPlayer.getPlaybackState();
//        }
//
//        @Override
//        public int getPlayState() {
//            return simpleExoPlayer.getPlaybackState();
//        }
//
//        @Override
//        public int getPlayPro() {
//            return i;
//        }
//
//        @Override
//        public void setPro(int pos) {
//            if(timer!=null){
//                timer.cancel();
//            }
//            simpleExoPlayer.seekTo(pos*1000);
//            i=pos;
//        }
//    }
//
//    private void startTask(){
//        if(timer!=null){
//            timer.cancel();
//        }
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                i++;
//                EventBus.getDefault().postSticky(i);
//            }
//        }, 0, 1000);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if(simpleExoPlayer!=null){
//            simpleExoPlayer.release();
//        }
//        if(timer!=null){
//            timer.cancel();
//        }
//    }
//
//    @Override
//    public void onTimelineChanged(Timeline timeline, int reason) {
//        LogUtils.e("onTimelineChanged");
//    }
//
//    @Override
//    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray
//    trackSelections) {
//        LogUtils.e("onTracksChanged");
//    }
//
//    @Override
//    public void onLoadingChanged(boolean isLoading) {
//        LogUtils.e("onLoadingChanged"+"---"+isLoading);
//    }
//
//    @Override
//    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//
//        LogUtils.e("onPlayerStateChanged"+"---"+playWhenReady+"---"+playbackState);
//    }
//
//    @Override
//    public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {
//        LogUtils.e("onPlaybackSuppressionReasonChanged"+"---"+playbackSuppressionReason);
//    }
//
//    @Override
//    public void onIsPlayingChanged(boolean isPlaying) {
//        if(isPlaying){
//            startTask();
//        }else{
//            if(timer!=null){
//                timer.cancel();
//            }
//        }
//        LogUtils.e("onIsPlayingChanged"+"---"+isPlaying);
//    }
//
//    @Override
//    public void onRepeatModeChanged(int repeatMode) {
//        LogUtils.e("onRepeatModeChanged"+"---"+repeatMode);
//    }
//
//    @Override
//    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
//        LogUtils.e("onShuffleModeEnabledChanged"+"---"+shuffleModeEnabled);
//    }
//
//    @Override
//    public void onPlayerError(ExoPlaybackException error) {
//        LogUtils.e("onPlayerError"+"---"+error.getMessage());
//    }
//
//    @Override
//    public void onPositionDiscontinuity(int reason) {
//        LogUtils.e("onPositionDiscontinuity"+"---"+reason);
//    }
//
//    @Override
//    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
//        LogUtils.e("onPlaybackParametersChanged"+"---");
//    }
//
//    @Override
//    public void onSeekProcessed() {
//        LogUtils.e("onSeekProcessed");
//        startTask();
//    }
//
//
//
//
//}

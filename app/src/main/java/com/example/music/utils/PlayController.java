package com.example.music.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.music.Interface.MusicInterface;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.PlayMusicServer;
import com.example.music.ui.MyApplication;
import com.example.music.utils.greendao.DaoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create By morningsun  on 2019-12-10
 */
public class PlayController {
    //bind 服务相关
    private ServiceConnection serviceConnection;
    private MusicInterface mi;
    private boolean isBind = false;

    private ACache aCache;

    private BindSuccess bindSuccess;
    private PlayNextMusic playNextMusic;
    private StateChange stateChange;

    //当前播放列表
    private List<PlayingMusicBeens> playingMusicBeens=new ArrayList<>();
    //当前播放列表下标
    private int pos;

    private static PlayController instance;


    /**
     *线程安全单例模式
     */
    public static PlayController getInstance() {
        if (instance == null) {
            synchronized (PlayController.class) {
                if (instance == null) {
                    instance = new PlayController();
                }
            }
        }
        return instance;
    }

    private PlayController() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mi = (PlayMusicServer.MusicController) service;
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
            Intent intent = new Intent(MyApplication.getContext(), PlayMusicServer.class);
            isBind = MyApplication.getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        init();
    }

    private  void init(){
        aCache=ACache.get(MyApplication.getContext());
        playingMusicBeens= new DaoUtils(MyApplication.getContext()).queryAllMessage();

        if(aCache.getAsObject("pos")!=null){
            pos= (int) aCache.getAsObject("pos");
        }else{
            pos=0;
        }
    }


    public  int playOrPause(){
        if(mi!=null){
            int a=mi.playOrPause();
                NotificationUtils.getInstance().sendNotification(playingMusicBeens.get(pos),1,MyApplication.getContext());
            if(stateChange!=null){
                stateChange.OnStateChange(a);
            }
            return a;
        }else{
            return 3;
        }

    }

    public  int get_state(){
        if(mi!=null){
            return  mi.getPlayState();
        }else{
            return 3;
        }
    }


    public  PlayingMusicBeens play_Next(){
        pos++;
        if(pos==playingMusicBeens.size()){
            pos=0;
            play(playingMusicBeens.get(0));
        }else{
            play(playingMusicBeens.get(pos));
        }
        aCache.put("pos",pos);
        playNextMusic.OnPlayNextMusic(pos);
        return  playingMusicBeens.get(pos);
    }

    public void PlayModel(){
        int play_state=0;
        if(aCache.getAsObject("play_model")!=null){
            play_state=(int)aCache.getAsObject("play_model");
        }
        if(play_state==0){
           pos++;
           play(playingMusicBeens.get(pos));
        }else if(play_state==1){
            play(playingMusicBeens.get(pos));
        }else{
            Random random=new Random();
            pos=random.nextInt(playingMusicBeens.size());
            play(playingMusicBeens.get(pos));
        }
        if(playNextMusic!=null){
            aCache.put("pos",pos);
            playNextMusic.OnPlayNextMusic(pos);
        }

    }

    public void setPro(int pos){
        if(mi!=null){
            mi.setPro(pos);
        }
    }


    public PlayingMusicBeens play_last(){
        pos--;
        aCache.put("pos",pos);
        play(playingMusicBeens.get(pos));
        playNextMusic.OnPlayNextMusic(pos);
        return  playingMusicBeens.get(pos);
    }

    public void play(final PlayingMusicBeens playingMusicBeens){
        Api.getInstance().iRetrofit.music_info(
                "mp3",playingMusicBeens.getRid(),"url",
                "convert_url3",
                "128kmp3","web",String.valueOf(System.currentTimeMillis())," 4d09d450-174a-11ea-91a9-0b8d42e7dcee").
                compose(ApiSubscribe.<BaseRespon>io_main()).subscribe(new ApiResponse<BaseRespon>(){

            @Override
            public void success(BaseRespon data1) {
                //获得歌曲播放地址
                mi.play(data1.getUrl());
                NotificationUtils.getInstance().sendNotification(playingMusicBeens,1,MyApplication.getContext());
            }
        });
    }



    public  int getPlayPro(){
       return mi.getPlayPro();
    }




    public  interface BindSuccess{
        void OnBindSuccess();
    }

    public void SetOnBindSuccess(BindSuccess bindSuccess){
        this.bindSuccess=bindSuccess;
    }


    public  interface PlayNextMusic{
        void OnPlayNextMusic(int pos);
    }

    public void SetOnPlayNextMusic(PlayNextMusic playNextMusic){
        this.playNextMusic=playNextMusic;
    }

    public interface StateChange{
        void OnStateChange(int state);
    }

    public void SetStateChange(StateChange stateChange){
        this.stateChange=stateChange;
    }


    public List<PlayingMusicBeens> getPlayingMusicBeens() {
        return playingMusicBeens;
    }

    public void setPlayingMusicBeens(List<PlayingMusicBeens> playingMusicBeens) {
        this.playingMusicBeens = playingMusicBeens;
    }



    public void onDestory(){
        if (isBind) {
            MyApplication.getContext().unbindService(serviceConnection);
        }
    }


}

package com.example.music.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.Toast;

import com.example.music.Interface.MusicInterface;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.PlayServer;
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

    private Context context;
    private ACache aCache;
    //当前播放列表下标
    private int pos;
    private BindSuccess bindSuccess;
    private PlayNextMusic playNextMusic;

    //当前播放列表
    private List<PlayingMusicBeens> playingMusicBeens=new ArrayList<>();


    public PlayController(final Context context) {
        this.context=context;

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mi = (PlayServer.MusicController) service;
                if(bindSuccess!=null){
                    bindSuccess.OnBindSunccess();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                context.unbindService(serviceConnection);
            }
        };
        if(!isBind){
            Intent intent = new Intent(context, PlayServer.class);
            isBind = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        init();
    }

    private  void init(){
        aCache=ACache.get(context);
        playingMusicBeens= new DaoUtils(context).queryAllMessage();

        if(aCache.getAsObject("pos")!=null){
            pos= (int) aCache.getAsObject("pos");
        }else{
            pos=0;
        }

    }

    public  int play_Paush(){
        if(mi!=null){
            return mi.PlayOrStop();
        }else{
            return 3;
        }
    }

    public  int get_state(){
        if(mi!=null){
            return  mi.get_play_state();
        }else{
            return 3;
        }
    }

    public  PlayingMusicBeens play_Next(){
        pos++;
        if(pos==playingMusicBeens.size()){
            pos=0;
            play(playingMusicBeens.get(0).getRid());
        }else{
            play(playingMusicBeens.get(pos).getRid());
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
           play(playingMusicBeens.get(pos).getRid());
        }else if(play_state==1){
            play(playingMusicBeens.get(pos).getRid());
        }else{
            Random random=new Random();
            pos=random.nextInt(playingMusicBeens.size());
            play(playingMusicBeens.get(pos).getRid());
        }
        if(playNextMusic!=null){
            aCache.put("pos",pos);
            playNextMusic.OnPlayNextMusic(pos);
        }

    }


    public PlayingMusicBeens play_last(){
        pos--;
        aCache.put("pos",pos);
        play(playingMusicBeens.get(pos).getRid());
        return  playingMusicBeens.get(pos);
    }

    public void play(String rid){
        Api.getInstance().iRetrofit.music_info(
                "mp3",rid,"url",
                "convert_url3",
                "128kmp3","web",String.valueOf(System.currentTimeMillis())," 4d09d450-174a-11ea-91a9-0b8d42e7dcee").
                compose(ApiSubscribe.<BaseRespon>io_main()).subscribe(new ApiResponse<BaseRespon>(){

            @Override
            public void success(BaseRespon data1) {
                //获得歌曲播放地址
                mi.Play(data1.getUrl());
            }
        });
    }



    public  void set_pro(int pro){
        Intent intent=new Intent();
        intent.setAction("com.example.music.lrc");
        intent.putExtra("current",pro);
        context.sendBroadcast(intent);
    }

    public  int get_pro(){
       return mi.get_plat_pro();
    }


    public  interface BindSuccess{
        void OnBindSunccess();
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


    public void onDestory(){
        if (isBind) {
            context.unbindService(serviceConnection);
        }
    }



}

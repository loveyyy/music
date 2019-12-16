package com.example.music.utils;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.music.Interface.MusicInterface;
import com.example.music.R;
import com.example.music.entry.PlayingMusicBeens;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.server.PlayServer;
import com.example.music.ui.custom.PlayerView;

import java.util.ArrayList;

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
    //当前播放列表
    private ArrayList<PlayingMusicBeens> playingMusicBeens=new ArrayList<>();
    //当前播放的进度 毫秒
    private int pro;


    public PlayController(Context context) {
        this.context=context;
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mi = (PlayServer.MusicController) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
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

        if(aCache.getAsObject("play_music")!=null){
            playingMusicBeens= (ArrayList<PlayingMusicBeens>) aCache.getAsObject("play_music");
        }
        if(aCache.getAsObject("pos")!=null){
            pos= (int) aCache.getAsObject("pos");
        }

    }

    public  void play_Paush(){
        mi.PlayWithButton();
    }

    public  int get_state(){
        return mi.get_play_state();
    }

    public  void play_Next(){
        pos++;
        aCache.remove("pos");
        aCache.put("pos",pos);
        play(playingMusicBeens.get(pos).getRid());
    }

    public void play_last(){
        pos--;
        aCache.remove("pos");
        play(playingMusicBeens.get(pos).getRid());
    }

    public void play(String rid){
        Api.getInstance().iRetrofit.music_info(
                "mp3",rid,"url",
                "convert_url3",
                "128kmp3","web",String.valueOf(System.currentTimeMillis())," 4d09d450-174a-11ea-91a9-0b8d42e7dcee").
                compose(ApiSubscribe.<BaseRespon>io_main()).subscribe(new ApiResponse<BaseRespon>(context){

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


    public void onDestory(){
        if (isBind) {
            context.unbindService(serviceConnection);
        }
    }



}

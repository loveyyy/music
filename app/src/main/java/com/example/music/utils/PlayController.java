package com.example.music.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.music.Interface.MusicInterface;
import com.example.music.Interface.State;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.BaseRespon;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.PlayMusicService;
import com.example.music.ui.MyApplication;
import com.example.music.utils.greendao.DaoUtils;

import java.util.List;
import java.util.Random;

/**
 * Create By morningsun  on 2019-12-10
 */
public class PlayController  {
    //bind 服务相关
    private ServiceConnection serviceConnection;
    private MusicInterface mi;
    private boolean isBind = false;

    private ACache aCache;
    private DaoUtils daoUtils;

    private static PlayController instance;

    private OnMusicChange onMusicChange;


    /**
     * 线程安全单例模式
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
                mi = (PlayMusicService.MusicController) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                MyApplication.getContext().unbindService(serviceConnection);
            }
        };
        if (!isBind) {
            Intent intent = new Intent(MyApplication.getContext(), PlayMusicService.class);
            isBind = MyApplication.getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        init();
    }

    private void init() {
        aCache = ACache.get(MyApplication.getContext());
        daoUtils = new DaoUtils(MyApplication.getContext());
    }


    public void playOrPause() {
        if (mi != null) {
            if (mi.playState() == State.STOP) {
                play();
            } else {
                mi.playOrPause();
            }
            NotificationUtils.getInstance().sendNotification(getMusicInfo(), 1, MyApplication.getContext());
        }
    }

    public State getState() {
        if (mi != null) {
            return mi.playState();
        } else {
            return State.STOP;
        }
    }


    public PlayingMusicBeens playNext() {
        int index = getIndex();
        if (index + 1 > getPlayList().size() - 1) {
            index = 0;
        } else {
            index++;
        }
        setIndex(index);
        play();
        return getMusicInfo();
    }

    public void PlayModel() {
        int play_state = 0;
        if (aCache.getAsObject("play_model") != null) {
            play_state = (int) aCache.getAsObject("play_model");
        }
        int index = getIndex();

        if (play_state == 0) {
            if (index + 1 > getPlayList().size() - 1) {
                index = 0;
            } else {
                index++;
            }
        } else if (play_state == 2) {
            Random random = new Random();
            index = random.nextInt(getPlayList().size());
        }
        setIndex(index);
        play();
    }

    public void setPro(int pos) {
        if (mi != null) {
            mi.seek(pos);
        }
    }


    public void playLast() {
        int index = getIndex();
        if (index < 1) {
            index = getPlayList().size() - 1;
        } else {
            index--;
        }
        setIndex(index);
        play();
    }

    public void play() {
        Api.getInstance().iRetrofit.music_info(
                "mp3", getMusicInfo().getRid(), "url",
                "convert_url3",
                "128kmp3", "web", String.valueOf(System.currentTimeMillis()), " 4d09d450-174a-11ea-91a9-0b8d42e7dcee").
                compose(RxHelper.observableIO2Main(MyApplication.getContext())).subscribe(new ApiResponse<BaseRespon>() {

            @Override
            public void success(BaseRespon data1) {
                //获得歌曲播放地址
                mi.play(data1.getUrl());
                if(onMusicChange!=null){
                    onMusicChange.Change();
                }
                NotificationUtils.getInstance().sendNotification(getMusicInfo(), 1, MyApplication.getContext());
            }
        });
    }


    public int getPlayPro() {
        return mi.playPro();
    }

    //获取当前播放音乐信息
    public PlayingMusicBeens getMusicInfo() {
        return daoUtils.queryAllMessage().get(getIndex());
    }


    //获取播放下标
    public int getIndex() {
        Object pos = aCache.getAsObject("pos");
        if (pos == null) {
            pos = 0;
        }
        return (int) pos;
    }

    //设置播放列表
    public void setIndex(int pos) {
        aCache.put("pos", pos);
    }


    //获取播放列表
    public List<PlayingMusicBeens> getPlayList() {
        return daoUtils.queryAllMessage();
    }


    //设置播放列表
    public void setPlayList(List<PlayingMusicBeens> playingMusicList,int pos) {
        if (!daoUtils.queryAllMessage().isEmpty()) {
            daoUtils.deleteAll();
        }
        daoUtils.insertMultMuisc(playingMusicList);
        setIndex(pos);
        play();
    }


    public interface  OnMusicChange{
        void Change();
    }

    public void setOnMusicChange(OnMusicChange onMusicChange){
        this.onMusicChange= onMusicChange;
    }



}

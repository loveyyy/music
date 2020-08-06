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
    private DaoUtils daoUtils;

    private BindSuccess bindSuccess;
    private PlayChange playChange;
    private StateChange stateChange;


    private static PlayController instance;


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
                mi = (PlayMusicServer.MusicController) service;
                if (bindSuccess != null) {
                    bindSuccess.OnBindSuccess();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                MyApplication.getContext().unbindService(serviceConnection);
            }
        };
        if (!isBind) {
            Intent intent = new Intent(MyApplication.getContext(), PlayMusicServer.class);
            isBind = MyApplication.getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
        init();
    }

    private void init() {
        aCache = ACache.get(MyApplication.getContext());
        daoUtils = new DaoUtils(MyApplication.getContext());
    }


    public int playOrPause() {
        if (mi != null) {
            int a = mi.playOrPause();
            NotificationUtils.getInstance().sendNotification(getMusicInfo(), 1, MyApplication.getContext());
            if (stateChange != null) {
                stateChange.OnStateChange(a);
            }
            return a;
        } else {
            return 3;
        }

    }

    public int get_state() {
        if (mi != null) {
            return mi.getPlayState();
        } else {
            return 3;
        }
    }


    public PlayingMusicBeens play_Next() {
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
            mi.setPro(pos);
        }
    }


    public PlayingMusicBeens play_last() {
        int index = getIndex();
        if (index < 1) {
            index = getPlayList().size() - 1;
        } else {
            index--;
        }
        setIndex(index);
        play();
        return getMusicInfo();
    }

    public void play() {
        Api.getInstance().iRetrofit.music_info(
                "mp3", getMusicInfo().getRid(), "url",
                "convert_url3",
                "128kmp3", "web", String.valueOf(System.currentTimeMillis()), " 4d09d450-174a-11ea-91a9-0b8d42e7dcee").
                compose(ApiSubscribe.<BaseRespon>io_main()).subscribe(new ApiResponse<BaseRespon>() {

            @Override
            public void success(BaseRespon data1) {
                //获得歌曲播放地址
                mi.play(data1.getUrl());
                playChange.PlayChange(getMusicInfo());
                NotificationUtils.getInstance().sendNotification(getMusicInfo(), 1, MyApplication.getContext());
            }
        });
    }


    public int getPlayPro() {
        return mi.getPlayPro();
    }

    //获取当前播放音乐信息
    public PlayingMusicBeens getMusicInfo() {
        return daoUtils.queryAllMessage().get((Integer) aCache.getAsObject("pos"));
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
    public boolean setPlayList(List<PlayingMusicBeens> playingMusicList) {
        if (!daoUtils.queryAllMessage().isEmpty()) {
            daoUtils.deleteAll();
        }
        return daoUtils.insertMultMuisc(playingMusicList);
    }


    public interface BindSuccess {
        void OnBindSuccess();
    }

    public void SetOnBindSuccess(BindSuccess bindSuccess) {
        this.bindSuccess = bindSuccess;
    }


    public interface PlayChange {
        void PlayChange(PlayingMusicBeens playingMusicBeens);
    }

    public void SetOnPlayChange(PlayChange playChange) {
        this.playChange = playChange;
    }

    public interface StateChange {
        void OnStateChange(int state);
    }

    public void SetStateChange(StateChange stateChange) {
        this.stateChange = stateChange;
    }



}

package com.example.music.Interface;

import android.media.MediaPlayer;

import com.example.music.model.PlayingMusicBeens;

/**
 * Created by Administrator on 2018/5/17.
 */

public interface MusicInterface {

    void Play(String Musicuri);
    int PlayOrStop();
    int get_play_state();
    int get_plat_pro();
}

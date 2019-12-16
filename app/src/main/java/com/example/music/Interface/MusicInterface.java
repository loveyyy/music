package com.example.music.Interface;

import android.media.MediaPlayer;

/**
 * Created by Administrator on 2018/5/17.
 */

public interface MusicInterface {

    void Play(String Musicuri);
    void PlayWithButton();
    int get_play_state();
    void PlayWithSb(int progress);
}

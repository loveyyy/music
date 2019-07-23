package com.example.music.Interface;

/**
 * Created by Administrator on 2018/5/17.
 */

public interface MusicInterface {

    void Play(String Musicuri,int duration);
    int PlayWithButton();
    void next(String Musicuri);
    void last(String Musicuri);
    void PlayWithSb(int progress);
}

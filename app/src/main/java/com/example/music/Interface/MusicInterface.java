package com.example.music.Interface;

/**
 * Created by Administrator on 2018/5/17.
 */

public interface MusicInterface {

    void play(String MusicUri);

    int playOrPause();

    int getPlayState();

    int getPlayPro();

    void setPro(int pos);

}

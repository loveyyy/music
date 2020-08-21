package com.example.music.Interface;

/**
 * Created by Administrator on 2018/5/17.
 */

public interface MusicInterface {

    void play(String MusicUri);

    void playOrPause();

    State playState();

    int playPro();

    void seek(int pos);

    void stop();

}

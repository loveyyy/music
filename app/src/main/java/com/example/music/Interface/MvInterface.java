package com.example.music.Interface;

import android.media.MediaPlayer;
import android.view.SurfaceHolder;

/**
 * Create By morningsun  on 2020-06-30
 */
public interface MvInterface {

    void playMv(String MvUri, SurfaceHolder surfaceHolder);

    int playOrPause();

    void release();

    void setPro(int pos);

    MediaPlayer getMp();



}

package com.example.music.model;

import com.example.music.Interface.State;

/**
 * Create By morningsun  on 2020-08-21
 */
public class PlayInfo {
    private State state;//状态
    private int pos;//当前播放进度

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}

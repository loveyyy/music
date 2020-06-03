package com.example.music.model;

import java.util.List;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
@Data
public class BaseRespon<T> {
    private int code;
    private long curTime;
    private String msg;
    private String profileId;
    private String reqId;
    private String url;
    private T data;
    private Boolean success;
}

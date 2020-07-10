package com.example.music.Interface;

import com.example.music.model.DownLoadInfo;

/**
 * Create By morningsun  on 2020-06-12
 */
public interface OnDownLoadListener {

    void onPending(DownLoadInfo downLoadInfo);

    void OnLOADING(DownLoadInfo downLoadInfo);

    void onProgree(DownLoadInfo downLoadInfo, int start, int size);

    void onStop(DownLoadInfo downLoadInfo, int start, int size);

    void onComplet(DownLoadInfo downLoadInfo);

    void onFailed(DownLoadInfo downLoadInfo);


}

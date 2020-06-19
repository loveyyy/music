package com.example.music.Interface;

import com.example.music.server.DownloadTask;

/**
 * Create By morningsun  on 2020-06-12
 */
public interface OnDownLoadListener {

    void OnIDEL(DownloadTask downloadTask);

    void onPending(DownloadTask downloadTask);

    void OnLOADING(DownloadTask downloadTask);

    void onProgree(DownloadTask downloadTask, int start, int size);

    void onStop(DownloadTask downloadTask, int start, int size);

    void onComplet(DownloadTask downloadTask);

    void onFailed(DownloadTask downloadTask, String message);

}

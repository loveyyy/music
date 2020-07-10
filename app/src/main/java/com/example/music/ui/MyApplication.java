package com.example.music.ui;

import android.app.Application;
import android.content.Context;

import com.example.music.model.DownLoadInfo;
import com.example.music.utils.greendao.DaoManager;

/**
 * Create By morningsun  on 2019-11-21
 */
public class MyApplication  extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        initGreenDao();
    }

    public static Context getContext() {
        return context;
    }

    private void initGreenDao()
    {
        DaoManager mManager = DaoManager.getInstance();
        mManager.init(this);
    }
}

package com.example.music.UI;

import android.app.Application;
import android.content.Context;

/**
 * Create By morningsun  on 2019-11-21
 */
public class MyApplication  extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}

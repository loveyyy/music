package com.example.music.Server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.music.Utils.DownLoad;

public class DownloadServer extends Service {
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
        return null;
    }

        @Override
        public void onCreate() {
        super.onCreate();
    }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
        String adress=intent.getStringExtra("adress");
        String filename=intent.getStringExtra("filename");
        String name=intent.getStringExtra("name");
        String json=intent.getStringExtra("json");
        DownLoad downLoad=new DownLoad(getApplication());
        downLoad.downloadAPK(adress,filename,name,json);
        return super.onStartCommand(intent, flags, startId);
    }
        public void onDestroy() {
        super.onDestroy();
        stopSelf();
        Log.e("Tag","服务结束");
    }
    }

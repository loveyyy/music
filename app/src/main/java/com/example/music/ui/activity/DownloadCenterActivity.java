package com.example.music.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.R;
import com.example.music.databinding.DownloadcenteractivityBinding;
import com.example.music.server.TaskDispatcher;
import com.example.music.ui.adapter.DownLoadApt;
import com.example.music.ui.adapter.LocalMusicApt;
import com.example.music.utils.LocalMusicUtils;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Create By morningsun  on 2020-06-24
 */
public class DownloadCenterActivity extends Activity {
    private DownloadcenteractivityBinding downloadcenteractivityBinding;
    private Timer timer;
    private DownLoadApt downLoadApt;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
//            downloadcenteractivityBinding.btnDownload.setText("下载中(" + TaskDispatcher.getInstance().getQueueTaskList().size() + ")");
//            if(downLoadApt!=null){
//                downLoadApt.notifyData();
//            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadcenteractivityBinding= DataBindingUtil.setContentView(this,R.layout.downloadcenteractivity);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0, null);
        initdata();
    }

    private void initdata() {
        downloadcenteractivityBinding.rcvCollect.setVisibility(View.GONE);
        downloadcenteractivityBinding.rcvDown.setVisibility(View.GONE);
        downloadcenteractivityBinding.rcvLocal.setVisibility(View.VISIBLE);
        LocalMusicApt localMusicApt=new LocalMusicApt(getBaseContext(), LocalMusicUtils.getmusic(getBaseContext()));
        downloadcenteractivityBinding.rcvLocal.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
        downloadcenteractivityBinding.rcvLocal.setAdapter(localMusicApt);

        downloadcenteractivityBinding.btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadcenteractivityBinding.rcvCollect.setVisibility(View.GONE);
                downloadcenteractivityBinding.rcvDown.setVisibility(View.GONE);
                downloadcenteractivityBinding.rcvLocal.setVisibility(View.VISIBLE);
                LocalMusicApt localMusicApt=new LocalMusicApt(getBaseContext(), LocalMusicUtils.getmusic(getBaseContext()));
                downloadcenteractivityBinding.rcvLocal.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
                downloadcenteractivityBinding.rcvLocal.setAdapter(localMusicApt);
            }
        });

        downloadcenteractivityBinding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadcenteractivityBinding.rcvCollect.setVisibility(View.GONE);
                downloadcenteractivityBinding.rcvDown.setVisibility(View.VISIBLE);
                downloadcenteractivityBinding.rcvLocal.setVisibility(View.GONE);
//                downLoadApt=new DownLoadApt(getBaseContext(), TaskDispatcher.getInstance().getQueueTaskList());
                LocalMusicApt localMusicApt=new LocalMusicApt(getBaseContext(), LocalMusicUtils.getmusic(getBaseContext()));
                downloadcenteractivityBinding.rcvDown.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
                downloadcenteractivityBinding.rcvDown.setAdapter(localMusicApt);
            }
        });

        downloadcenteractivityBinding.btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void Start() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(10);
            }
        }, 0, 20);
    }

    public void scanFileAsync(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(timer!=null){
            timer.cancel();
        }
    }
}

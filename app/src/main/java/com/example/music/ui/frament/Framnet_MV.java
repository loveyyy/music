package com.example.music.ui.frament;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.R;
import com.example.music.databinding.FramentMineBinding;
import com.example.music.server.DownloadTask;
import com.example.music.server.TaskDispatcher;
import com.example.music.ui.adapter.DownLoadApt;
import com.example.music.ui.adapter.LocalMusicApt;
import com.example.music.ui.base.BaseFrament;
import com.example.music.utils.LocalMusicUtils;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Framnet_MV extends BaseFrament<FramentMineBinding> {
    private FramentMineBinding framentMineBinding;
    private  DownLoadApt downLoadApt;
    private Timer timer=new Timer();
    private int tag = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            framentMineBinding.btnMyDownload.setText("下载中("+TaskDispatcher.getInstance().getQueueTaskList().size()+")");
            framentMineBinding.btnMyDownloadFinish.setText("完成下载("+TaskDispatcher.getInstance().getDownloadedList().size()+")");
            if(tag==1){
                downLoadApt.notifyData();
            }
            if(tag==2){
                downLoadApt.notifyData1();
            }
        }
    };


    @Override
    public int getContentViewId() {
        return R.layout.frament_mine;
    }

    @Override
    protected void initview(FramentMineBinding bindView) {
        framentMineBinding=bindView;
    }

    @Override
    protected void SetVM() {

    }


    @Override
    protected void setonclick() {
        framentMineBinding.btnMyDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                framentMineBinding.rcvDownloadFinish.setVisibility(View.GONE);
                framentMineBinding.rcvCollection.setVisibility(View.GONE);
                framentMineBinding.rcvLocal.setVisibility(View.GONE);
                if(framentMineBinding.rcvDownload.getVisibility()==View.GONE){
                    framentMineBinding.rcvDownload.setVisibility(View.VISIBLE);
                    tag=1;
                }else {
                    framentMineBinding.rcvDownload.setVisibility(View.GONE);
                }

                framentMineBinding.rcvDownload.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                downLoadApt=new DownLoadApt(getContext(),TaskDispatcher.getInstance().getQueueTaskList());
                framentMineBinding.rcvDownload.setAdapter(downLoadApt);

                downLoadApt.setOnItemClick(new DownLoadApt.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        List<DownloadTask> downloadTasks=TaskDispatcher.getInstance().getQueueTaskList();
                        if(!downloadTasks.isEmpty()){
                            if(downloadTasks.get(pos).getState()!=DownloadTask.STOP){
                                TaskDispatcher.getInstance().stop(downloadTasks.get(pos));
                            }else{
                                TaskDispatcher.getInstance().enqueue(downloadTasks.get(pos));
                            }
                        }
                    }
                });

            }
        });

        framentMineBinding.btnMyLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                framentMineBinding.rcvDownloadFinish.setVisibility(View.GONE);
                framentMineBinding.rcvCollection.setVisibility(View.GONE);
                framentMineBinding.rcvDownload.setVisibility(View.GONE);
                if(framentMineBinding.rcvLocal.getVisibility()==View.GONE){
                    framentMineBinding.rcvLocal.setVisibility(View.VISIBLE);
                }else {
                    framentMineBinding.rcvLocal.setVisibility(View.GONE);
                }
                scanFileAsync(getContext(), Environment.getExternalStorageDirectory().getPath() + File.separator + "mv");

                framentMineBinding.rcvLocal.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                LocalMusicApt localMusicApt=new LocalMusicApt(getContext(),LocalMusicUtils.getmusic(getContext()));
                framentMineBinding.rcvLocal.setAdapter(localMusicApt);
            }
        });

        framentMineBinding.btnMyCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                framentMineBinding.rcvDownloadFinish.setVisibility(View.GONE);
                framentMineBinding.rcvLocal.setVisibility(View.GONE);
                framentMineBinding.rcvDownload.setVisibility(View.GONE);
                if(framentMineBinding.rcvCollection.getVisibility()==View.GONE){
                    framentMineBinding.rcvCollection.setVisibility(View.VISIBLE);
                }else {
                    framentMineBinding.rcvCollection.setVisibility(View.GONE);
                }


            }
        });

        framentMineBinding.btnMyDownloadFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                framentMineBinding.rcvLocal.setVisibility(View.GONE);
                framentMineBinding.rcvDownload.setVisibility(View.GONE);
                framentMineBinding.rcvCollection.setVisibility(View.GONE);
                if(framentMineBinding.rcvDownloadFinish.getVisibility()==View.GONE){
                    framentMineBinding.rcvDownloadFinish.setVisibility(View.VISIBLE);
                    tag=2;
                }else {
                    framentMineBinding.rcvDownloadFinish.setVisibility(View.GONE);
                }

                framentMineBinding.rcvDownloadFinish.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                downLoadApt=new DownLoadApt(getContext(),TaskDispatcher.getInstance().getDownloadedList());
                framentMineBinding.rcvDownloadFinish.setAdapter(downLoadApt);

            }
        });

        framentMineBinding.btnMySet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    private  void Start(){
        if(timer==null){
            timer=new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               handler.sendEmptyMessage(10);
            }
        },0,20);
    }

    public void scanFileAsync(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible){
            Start();
        }else{
            timer.cancel();
            timer=null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy");
        timer.cancel();
        timer=null;
    }
}

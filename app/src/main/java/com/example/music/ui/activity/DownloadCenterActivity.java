package com.example.music.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Interface.OnDownLoadListener;
import com.example.music.R;
import com.example.music.databinding.DownloadcenteractivityBinding;
import com.example.music.model.DownLoadInfo;
import com.example.music.server.DownloadTask;
import com.example.music.server.TaskDispatcher;
import com.example.music.ui.adapter.DownLoadApt;
import com.example.music.ui.base.BaseActivity;
import com.example.music.ui.base.BaseVM;
import com.example.music.utils.greendao.DaoUtils;
import com.google.android.material.tabs.TabLayout;

import java.io.File;

/**
 * Create By morningsun  on 2020-06-24
 */
public class DownloadCenterActivity extends BaseActivity<DownloadcenteractivityBinding, BaseVM> {
    private DownloadcenteractivityBinding downloadcenteractivityBinding;
    private DownLoadApt downLoadApt;
    @Override
    public int getLayout() {
        return R.layout.downloadcenteractivity;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(DownloadcenteractivityBinding bindView) {
        downloadcenteractivityBinding=bindView;
    }

    @Override
    protected void setVM(BaseVM vm) {

    }

    @Override
    protected void setListener() {

        TaskDispatcher.getInstance().addListen(new OnDownLoadListener() {
            @Override
            public void onPending(DownLoadInfo downLoadInfo) {
                if(downLoadApt!=null){
                    downLoadApt.notifyData(downLoadInfo);
                }
            }

            @Override
            public void OnLOADING(DownLoadInfo downLoadInfo) {
                if(downLoadApt!=null){
                    downLoadApt.notifyData(downLoadInfo);
                }
            }

            @Override
            public void onProgree(DownLoadInfo downLoadInfo, int start, int size) {
                if(downLoadApt!=null){
                    downLoadApt.notifyData(downLoadInfo);
                }

            }

            @Override
            public void onStop(DownLoadInfo downLoadInfo, int start, int size) {
                if(downLoadApt!=null){
                    downLoadApt.notifyData(downLoadInfo);
                }
            }

            @Override
            public void onComplet(DownLoadInfo downLoadInfo) {
                if(downLoadApt!=null){
                    downLoadApt.notifyData(downLoadInfo);
                }
            }

            @Override
            public void onFailed(DownLoadInfo downLoadInfo) {
            }
        });

        downloadcenteractivityBinding.tabUser.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    scanFileAsync(getContext(), Environment.getExternalStorageDirectory().getPath() + File.separator + "mv");
                    downloadcenteractivityBinding.rcvCollect.setVisibility(View.GONE);
                    downloadcenteractivityBinding.rcvLocal.setVisibility(View.VISIBLE);

                    downLoadApt=new DownLoadApt(getContext(),new DaoUtils(getContext()).queryDownloadInfoBuilder(DownloadTask.PROGREE));
                    downloadcenteractivityBinding.rcvLocal.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
                    downloadcenteractivityBinding.rcvLocal.setAdapter(downLoadApt);

                }else{
                    downloadcenteractivityBinding.rcvCollect.setVisibility(View.VISIBLE);
                    downloadcenteractivityBinding.rcvLocal.setVisibility(View.GONE);

                    downLoadApt=new DownLoadApt(getContext(),new DaoUtils(getContext()).queryDownloadInfoBuilder(DownloadTask.FINISHED));
                    downloadcenteractivityBinding.rcvCollect.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
                    downloadcenteractivityBinding.rcvCollect.setAdapter(downLoadApt);

//                    LocalMusicApt localMusicApt=new LocalMusicApt(getBaseContext(), LocalMusicUtils.getmusic(getBaseContext()));
//                    downloadcenteractivityBinding.rcvCollect.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
//                    downloadcenteractivityBinding.rcvCollect.setAdapter(localMusicApt);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void initData() {

    }



    public void scanFileAsync(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

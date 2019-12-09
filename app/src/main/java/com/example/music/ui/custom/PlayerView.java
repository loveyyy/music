package com.example.music.ui.custom;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.music.R;
import com.example.music.ui.activity.TextLrc;
import com.example.music.ui.adapter.VP_Paly_Apt;
import com.example.music.utils.ACache;
import com.example.music.databinding.PlayerBinding;
import com.example.music.entry.PlayingMusicBeens;
import com.example.music.Interface.MusicInterface;
import com.example.music.server.PlayServer;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;

import java.util.ArrayList;

public class PlayerView extends RelativeLayout {
    private ServiceConnection serviceConnection;
    private MusicInterface mi;
    private boolean isBind = false;
    private PlayerBinding playerBinding;
    private ACache aCache;
    private MyBroadcastReceiver myBroadcastReceiver;
    private String songurl;
    private int pos;
    private ArrayList<PlayingMusicBeens> playingMusicBeens=new ArrayList<>();
    private Context context;
    private  VP_Paly_Apt vp_paly_apt;


    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        playerBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.player,
                this,true);
        aCache=ACache.get(context);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mi = (PlayServer.MusicController) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        //绑定服务
        Intent intent = new Intent(context, PlayServer.class);
        isBind = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        this.context=context;
        myBroadcastReceiver=new MyBroadcastReceiver();
         IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.music.pro");
        context.registerReceiver(myBroadcastReceiver, filter);
        refresh();
        setonclick();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public  void refresh(){
        if(aCache.getAsObject("play_music")!=null){
            playingMusicBeens= (ArrayList<PlayingMusicBeens>) aCache.getAsObject("play_music");
        }
        if(aCache.getAsObject("pos")!=null){
            pos= (int) aCache.getAsObject("pos");
        }
        vp_paly_apt=new VP_Paly_Apt(context,playingMusicBeens);
        playerBinding.vpPlay.setAdapter(vp_paly_apt);
        playerBinding.vpPlay.setCurrentItem(pos);
    }


    private void setonclick() {
        playerBinding.vpPlay.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos=position;
                aCache.remove("pos");
                aCache.put("pos",position);
                playmusci(playingMusicBeens.get(pos).getRid());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        playerBinding.ibtnPlayS.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mi.PlayWithButton()==1){
                    //已暂停
                    playerBinding.ibtnPlayS.setImage(R.drawable.play);
                }else {
                    //已播放
                    playerBinding.ibtnPlayS.setImage(R.drawable.stop);
                    playmusci(playingMusicBeens.get(pos).getRid());
                }
            }
        });
//        ibtn_download.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //下载
//                Intent intent1 = new Intent(context, DownloadServer.class);
//                intent1.putExtra("adress", songurl);
//                intent1.putExtra("filename",playingMusicBeens.get(pos).getMusicname()+".map");
//                intent1.putExtra("name",playingMusicBeens.get(pos).getMusicname());
//                intent1.putExtra("json",new Gson().toJson(playingMusicBeens.get(pos),PlayingMusicBeens.class));
//                context.startService(intent1);
//            }
//        });
//
//
        playerBinding.ibtnDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int progress = playerBinding.ibtnPlayS.getProgress();
                intent.putExtra("musicid", playingMusicBeens.get(pos).getRid());
                intent.putExtra("progress", progress);
                intent.putExtra("image",playingMusicBeens.get(pos).getAlbumpic());
                intent.setClass(context, TextLrc.class);
                context.startActivity(intent);
            }
        });

    }

    public   void play(final ArrayList<PlayingMusicBeens> playingMusicBeens , final int pos){
        aCache.remove("play_music");
        aCache.remove("pos");
        aCache.put("play_music", playingMusicBeens);
        aCache.put("pos",pos);
        this.playingMusicBeens=playingMusicBeens;
        this.pos=pos;
        playmusci(playingMusicBeens.get(pos).getRid());
        vp_paly_apt=new VP_Paly_Apt(context,playingMusicBeens);
        playerBinding.vpPlay.setAdapter(vp_paly_apt);
        playerBinding.vpPlay.setCurrentItem(pos);
    }

    private void playmusci(String rid){
        Api.getInstance().iRetrofit.music_info(
                "mp3",rid,"url",
                "convert_url3",
                "128kmp3","web",String.valueOf(System.currentTimeMillis())," 4d09d450-174a-11ea-91a9-0b8d42e7dcee").
                compose(ApiSubscribe.<BaseRespon>io_main()).subscribe(new ApiResponse<BaseRespon>(context){

            @Override
            public void success(BaseRespon data1) {
                //获得歌曲播放地址
                songurl=data1.getUrl();
                mi.Play(songurl);
                playerBinding.ibtnPlayS.setImage(R.drawable.stop);
            }
        });
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
     @Override
    public void onReceive(Context context, Intent intent) {
     //这里的intent可以获取发送广播时传入的数据
         int i=intent.getIntExtra("pro",0);
        playerBinding.ibtnPlayS.setProgress(i*100/playingMusicBeens.get(pos).getDuration());
     }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isBind) {
            context.unbindService(serviceConnection);
        }
        if(myBroadcastReceiver!=null){
            context.unregisterReceiver(myBroadcastReceiver);
        }

    }

}

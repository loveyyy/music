package com.example.music.ui.custom;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.music.R;
import com.example.music.server.DownloadServer;
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
import com.example.music.utils.PlayController;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PlayerView extends RelativeLayout {
    private PlayerBinding playerBinding;
    private ACache aCache;
    private MyBroadcastReceiver myBroadcastReceiver;
    private int pos;
    private ArrayList<PlayingMusicBeens> playingMusicBeens=new ArrayList<>();
    private Context context;
    private  VP_Paly_Apt vp_paly_apt;
    private PlayController playController;
    private boolean isdown=false;
    private boolean play=true;


    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        playerBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.player,
                this,true);
        aCache=ACache.get(context);
        playController=new PlayController(getContext());
        this.context=context;
        myBroadcastReceiver=new MyBroadcastReceiver();
         IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.music.pro");
        filter.setPriority(1000);
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
        play=false;
        playerBinding.vpPlay.setCurrentItem(pos);
        if(playController.get_state()==1){
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
        }else{
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setonclick() {
        playerBinding.vpPlay.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                play=true;
            }

            @Override
            public void onPageSelected(int position) {
                pos=position;
                aCache.remove("pos");
                aCache.put("pos",position);
                if(play){
                    playController.play(playingMusicBeens.get(pos).getRid());
                    playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        playerBinding.ivPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playController.get_state()==2){
                    //正暂停
                    playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
                    playController.play(playingMusicBeens.get(pos).getRid());
                }else if(playController.get_state()==1){
                    //已播放
                    playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
                    playController.play_Paush();
                }

            }
        });

        playerBinding.vpPlay.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    isdown=true;
                    return true;
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(isdown){
                        isdown=false;
                        Intent intent = new Intent();
                        intent.putExtra("musicid", playingMusicBeens.get(pos).getRid());
                        intent.putExtra("image",playingMusicBeens.get(pos).getAlbumpic());
                        intent.setClass(context, TextLrc.class);
                        context.startActivity(intent);
                        return true;
                    }
                }
                isdown=false;
                return false;
            }
        });

        playerBinding.ibtnDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, DownloadServer.class);
//                intent1.putExtra("adress", songurl);
//                intent1.putExtra("filename",playingMusicBeens.get(pos).getMusicname()+".map");
//                intent1.putExtra("name",playingMusicBeens.get(pos).getMusicname());
//                intent1.putExtra("json",new Gson().toJson(playingMusicBeens.get(pos),PlayingMusicBeens.class));
                context.startService(intent1);
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
        vp_paly_apt=new VP_Paly_Apt(context,playingMusicBeens);
        playerBinding.vpPlay.setAdapter(vp_paly_apt);
        play=true;
        playerBinding.vpPlay.setCurrentItem(pos);
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {
     @Override
    public void onReceive(Context context, Intent intent) {
     //这里的intent可以获取发送广播时传入的数据
         int i=intent.getIntExtra("pro",0);
         if(i!=0){
             if(i/(playingMusicBeens.get(pos).getDuration()*10)==0){
                 playerBinding.cirPro.setProgress(1);
             }else{
                 playerBinding.cirPro.setProgress(i/(playingMusicBeens.get(pos).getDuration()*10));
             }
         }
      }
    }



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        playController.onDestory();
        if(myBroadcastReceiver!=null){
            context.unregisterReceiver(myBroadcastReceiver);
        }

    }

}

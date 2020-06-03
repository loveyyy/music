package com.example.music.ui.custom;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.music.R;
import com.example.music.databinding.PlayerBinding;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.PlayServer;
import com.example.music.ui.activity.TextLrc;
import com.example.music.ui.adapter.VP_Paly_Apt;
import com.example.music.utils.ACache;
import com.example.music.utils.PlayController;
import com.example.music.utils.greendao.DaoUtils;

import java.util.ArrayList;
import java.util.List;

public class PlayerView extends RelativeLayout implements PlayController.BindSuccess, PlayController.PlayNextMusic {
    private PlayerBinding playerBinding;
    private ACache aCache;
    private MyBroadcastReceiver myBroadcastReceiver;
    private int pos;
    private List<PlayingMusicBeens> playingMusicBeens=new ArrayList<>();
    private Context context;
    private  VP_Paly_Apt vp_paly_apt;
    private PlayController playController;
    private boolean isdown=false;
    private boolean play=false;
    private DaoUtils daoUtils;
    private showList showList;


    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        playerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.player,
                this,true);
        aCache=ACache.get(context);
        playController=new PlayController(getContext());
        playController.SetOnBindSuccess(this);
        playController.SetOnPlayNextMusic(this);
        this.context=context;
        daoUtils=new DaoUtils(context);
        myBroadcastReceiver=new MyBroadcastReceiver();
         IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.music.pro");
        filter.setPriority(1000);
        context.registerReceiver(myBroadcastReceiver, filter);
        setonclick();
        refresh();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public  void refresh(){
         playingMusicBeens=daoUtils.queryAllMessage();

        if(aCache.getAsObject("pos")!=null){
            pos= (int) aCache.getAsObject("pos");
        }else{
            pos=0;
        }

        vp_paly_apt=new VP_Paly_Apt(context,playingMusicBeens);
        playerBinding.vpPlay.setAdapter(vp_paly_apt);
        play=false;
        playerBinding.vpPlay.setCurrentItem(pos);
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
                aCache.put("pos",pos);
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
                int state =playController.play_Paush();
                if(state== PlayServer.PLAYING){
                    //正暂停
                    playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
                }else if(state==PlayServer.PAUSE){
                    //已播放
                    playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
                }else{
                    playController.play(playingMusicBeens.get(pos).getRid());
                    playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
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
                        intent.putExtra("pos", pos);
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
                //显示播放列表
                if(showList!=null){
                    showList.OnShowList(playingMusicBeens);
                }
            }
        });
    }

    public   void play(final List<PlayingMusicBeens> playingMusicBeens , final int pos){
        if(daoUtils.queryAllMessage().isEmpty()){
            daoUtils.insertMultMuisc(playingMusicBeens);
        }else{
            daoUtils.deleteAll();
            daoUtils.insertMultMuisc(playingMusicBeens);
        }
        aCache.put("pos",pos);
        this.playingMusicBeens=playingMusicBeens;
        this.pos=pos;
        vp_paly_apt=new VP_Paly_Apt(context,playingMusicBeens);
        playerBinding.vpPlay.setAdapter(vp_paly_apt);
        play=true;
        playerBinding.vpPlay.setCurrentItem(pos);
        if(pos==0){
            playController.play(playingMusicBeens.get(pos).getRid());
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
        }
    }

    @Override
    public void OnBindSunccess() {
        if(playController.get_state()==PlayServer.PLAYING){
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
            playerBinding.cirPro.setProgress(playController.get_pro()*100/(playingMusicBeens.get(pos).getDuration()));
        }else if(playController.get_state()==PlayServer.PAUSE){
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
            playerBinding.cirPro.setProgress(1);
        }else{
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
        }
    }

    @Override
    public void OnPlayNextMusic(int pos) {
        refresh();
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {
     @Override
    public void onReceive(Context context, Intent intent) {
     //这里的intent可以获取发送广播时传入的数据
         int i=intent.getIntExtra("pro",0);
         if(i!=0){
             if(i!=playingMusicBeens.get(pos).getDuration()){
                 playerBinding.cirPro.setProgress(i*100/(playingMusicBeens.get(pos).getDuration()));
             }else{
                playController.PlayModel();
             }
         }
      }
    }

    public interface showList{
        void OnShowList(List<PlayingMusicBeens> playingMusicBeens);
    }

    public void SetShowList(showList showList){
        this.showList=showList;
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

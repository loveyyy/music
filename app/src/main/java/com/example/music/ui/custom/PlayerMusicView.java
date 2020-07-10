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

import com.blankj.utilcode.util.ToastUtils;
import com.example.music.R;
import com.example.music.databinding.PlayerBinding;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.PlayMusicServer;
import com.example.music.ui.activity.TextLrc;
import com.example.music.ui.adapter.VP_Paly_Apt;
import com.example.music.utils.ACache;
import com.example.music.utils.PlayController;
import com.example.music.utils.greendao.DaoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class PlayerMusicView extends RelativeLayout implements PlayController.BindSuccess, PlayController.PlayNextMusic, PlayController.StateChange {
    private PlayerBinding playerBinding;
    private ACache aCache;
    private int pos;
    private List<PlayingMusicBeens> playingMusicBeens=new ArrayList<>();
    private Context context;
    private  VP_Paly_Apt vp_paly_apt;
    private PlayController playController;
    private boolean isdown=false;
    private boolean play=false;
    private DaoUtils daoUtils;
    private showList showList;


    public PlayerMusicView(Context context) {
        super(context);
    }

    public PlayerMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        playerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.player,
                this,true);
        aCache=ACache.get(context);
        playController=PlayController.getInstance();
        playController.SetOnBindSuccess(this);
        playController.SetOnPlayNextMusic(this);
        playController.SetStateChange(this);
        this.context=context;
        daoUtils=new DaoUtils(context);
        EventBus.getDefault().register(this);
        setonclick();
        refresh();
    }

    public PlayerMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        vp_paly_apt.setOnItemClick(new VP_Paly_Apt.onItemClick() {
            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent();
                intent.putExtra("pos", pos);
                intent.setClass(context, TextLrc.class);
                context.startActivity(intent);
            }
        });
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
                    playController.play(playingMusicBeens.get(pos));
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
                if(!playingMusicBeens.isEmpty()){
                    int state =playController.get_state();
                    if(state== PlayMusicServer.STOP){
                        playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
                        playController.play(playingMusicBeens.get(pos));
                    }else {
                        if(playController.playOrPause()==PlayMusicServer.PLAYING){
                            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
                        }else{
                            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
                        }
                    }
                }else{
                    ToastUtils.showShort("请选择歌曲进行播放");
                }


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
            playController.play(playingMusicBeens.get(pos));
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
        }
    }

    @Override
    public void OnBindSuccess() {
        if(playController.get_state()== PlayMusicServer.PLAYING){
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
            playerBinding.cirPro.setProgress(playController.getPlayPro()*100/(playingMusicBeens.get(pos).getDuration()));
        }else if(playController.get_state()==PlayMusicServer.PAUSE){
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

    @Override
    public void OnStateChange(int state) {
        if(state==PlayMusicServer.PLAYING){
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
        }else{
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void GetMusicPro(Integer a){
        if(a!=0){
            if(a!=playingMusicBeens.get(pos).getDuration()){
                playerBinding.cirPro.setProgress(a*100/(playingMusicBeens.get(pos).getDuration()));
            }else{
                playController.PlayModel();
                playerBinding.cirPro.setProgress(0);
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
        EventBus.getDefault().unregister(this);
    }

}

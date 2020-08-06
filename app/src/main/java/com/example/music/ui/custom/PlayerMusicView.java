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

public class PlayerMusicView extends RelativeLayout implements PlayController.BindSuccess, PlayController.PlayChange, PlayController.StateChange {
    private PlayerBinding playerBinding;
    private Context context;
    private PlayController playController;
    private showList showList;
    private boolean isScroller=false;


    public PlayerMusicView(Context context) {
        super(context);
    }

    public PlayerMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        playerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.player,
                this, true);
        playController = PlayController.getInstance();
        playController.SetOnBindSuccess(this);
        playController.SetOnPlayChange(this);
        playController.SetStateChange(this);
        this.context = context;
        EventBus.getDefault().register(this);
        setonclick();
        refresh();
    }

    public PlayerMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void refresh() {
        VP_Paly_Apt vp_paly_apt = new VP_Paly_Apt(context, playController.getPlayList());
        playerBinding.vpPlay.setAdapter(vp_paly_apt);
        playerBinding.vpPlay.setCurrentItem(playController.getIndex());
        playerBinding.vpPlay.setCurrentItem(playController.getIndex());

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

            }

            @Override
            public void onPageSelected(int position) {
                if(isScroller){
                    playController.setIndex(position);
                    playController.play();
                    playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
                    isScroller=false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                isScroller=true;
            }
        });

        playerBinding.ivPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playController.getPlayList().isEmpty()) {
                    int state = playController.get_state();
                    if (state == PlayMusicServer.STOP) {
                        playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
                        playController.play();
                    } else {
                        if (playController.playOrPause() == PlayMusicServer.PLAYING) {
                            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
                        } else {
                            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
                        }
                    }
                } else {
                    ToastUtils.showShort("请选择歌曲进行播放");
                }
            }
        });


        playerBinding.ibtnDownload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示播放列表
                if (showList != null) {
                    showList.OnShowList(playController.getPlayList());
                }
            }
        });
    }

    public void play(List<PlayingMusicBeens> playingMusicBeens,final int pos) {
        playController.setPlayList(playingMusicBeens);
        playController.setIndex(pos);
        playController.play();
        refresh();
    }

    @Override
    public void OnBindSuccess() {
        if (playController.get_state() == PlayMusicServer.PLAYING) {
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
            playerBinding.cirPro.setProgress(playController.getPlayPro() * 100 / (playController.getMusicInfo().getDuration()));
        } else if (playController.get_state() == PlayMusicServer.PAUSE) {
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
            playerBinding.cirPro.setProgress(1);
        } else {
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
        }
    }

    @Override
    public void PlayChange(PlayingMusicBeens playingMusicBeens) {
        refresh();
        if (playController.get_state() == PlayMusicServer.PLAYING) {
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
            playerBinding.cirPro.setProgress(playController.getPlayPro() * 100 / (playController.getMusicInfo().getDuration()));
        } else if (playController.get_state() == PlayMusicServer.PAUSE) {
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
            playerBinding.cirPro.setProgress(1);
        } else {
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
        }
    }

    @Override
    public void OnStateChange(int state) {
        if (state == PlayMusicServer.PLAYING) {
            playerBinding.ivPlay.setBackgroundResource(R.drawable.stop);
        } else {
            playerBinding.ivPlay.setBackgroundResource(R.drawable.play);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void GetMusicPro(Integer a) {
        if (a != 0) {
            playerBinding.cirPro.setProgress(a * 100 / (playController.getMusicInfo().getDuration()));
        } else {
            playController.PlayModel();
            playerBinding.cirPro.setProgress(0);
        }
    }


    public interface showList {
        void OnShowList(List<PlayingMusicBeens> playingMusicBeens);
    }

    public void SetShowList(showList showList) {
        this.showList = showList;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

}

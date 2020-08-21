package com.example.music.ui.activity;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.music.R;
import com.example.music.databinding.LrcBinding;
import com.example.music.model.BaseRespon;
import com.example.music.model.LrcBeen;
import com.example.music.model.PlayInfo;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.PlayMusicService;
import com.example.music.ui.MyApplication;
import com.example.music.ui.base.BaseActivity;
import com.example.music.utils.PlayController;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;
import com.example.music.viewmodel.LrcVM;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Administrator on 2018/7/3.
 */

public class TextLrc extends BaseActivity<LrcBinding, LrcVM> {
    private LrcBinding lrcBinding;
    private LrcVM lrcVM;
    private PlayController playController;
    private PlayingMusicBeens playingMusicBeens;

    @Override
    public int getLayout() {
        return R.layout.lrc;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(LrcBinding bindView) {
        lrcBinding = bindView;
        playController = PlayController.getInstance();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void setVM(LrcVM vm) {
        lrcVM = vm;

        lrcVM.lrc.observe(this, new Observer<BaseRespon<LrcBeen>>() {
            @Override
            public void onChanged(BaseRespon<LrcBeen> baseRespon) {
                lrcBinding.lrctext.init(baseRespon.getData().getLrclist());
                RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
                Glide.with(getApplicationContext()).load(playingMusicBeens.getAlbumpic()).apply(requestOptions).into(lrcBinding.ivBac);
            }
        });
    }

    @Override
    protected void setListener() {

        lrcBinding.ibLrcLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playingMusicBeens = playController.playNext();
                lrcVM.getLrc(playingMusicBeens.getRid());
            }
        });
        lrcBinding.ibLrcNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playingMusicBeens = playController.playNext();
                lrcVM.getLrc(playingMusicBeens.getRid());
            }
        });
        lrcBinding.ibLrcPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playController.playOrPause();
            }
        });
    }

    @Override
    protected void initData() {
        playingMusicBeens = playController.getMusicInfo();
        RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
        Glide.with(this).load(playingMusicBeens.getAlbumpic()).apply(requestOptions).into(lrcBinding.ivBac);
        lrcVM.getLrc(playingMusicBeens.getRid());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void playResult(PlayInfo playInfo) {
        switch (playInfo.getState()){
            case PLAYING:
                lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
                break;
            case PAUSE:
                lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_play);
                break;
            case STOP:
            case BUFFER:
                lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
                break;
            case ERROR:
                Toast.makeText(MyApplication.getContext(), "播放错误,自动为您播放下一曲", Toast.LENGTH_SHORT).show();
                playController.PlayModel();
                break;
            case FINISH:
                playController.PlayModel();
                break;
            case CHANGE:
                RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
                Glide.with(getContext()).load(playController.getMusicInfo().getAlbumpic()).apply(requestOptions).into(lrcBinding.ivBac);
                lrcVM.getLrc(playController.getMusicInfo().getRid());
                break;
        }
    }

}

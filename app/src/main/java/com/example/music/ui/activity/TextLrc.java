package com.example.music.ui.activity;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.music.R;
import com.example.music.databinding.LrcBinding;
import com.example.music.model.BaseRespon;
import com.example.music.model.LrcBeen;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.PlayMusicServer;
import com.example.music.ui.base.BaseActivity;
import com.example.music.utils.PlayController;
import com.example.music.utils.greendao.DaoUtils;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;
import com.example.music.viewmodel.LrcVM;


/**
 * Created by Administrator on 2018/7/3.
 */

public class TextLrc extends BaseActivity<LrcBinding, LrcVM> implements PlayController.PlayChange {
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
        playController.SetOnPlayChange(this);
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
                playingMusicBeens = playController.play_last();
                lrcVM.getLrc(playingMusicBeens.getRid());
            }
        });
        lrcBinding.ibLrcNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playingMusicBeens = playController.play_Next();
                lrcVM.getLrc(playingMusicBeens.getRid());
            }
        });
        lrcBinding.ibLrcPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = playController.playOrPause();
                if (state == PlayMusicServer.PLAYING) {
                    lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
                } else if (state == PlayMusicServer.PAUSE) {
                    lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_play);
                } else {
                    lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
                }
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int pos = intent.getIntExtra("pos", 0);
        if (playController.get_state() == PlayMusicServer.PLAYING) {
            lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
        } else if (playController.get_state() == PlayMusicServer.PAUSE) {
            lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_play);
        } else {
            lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
        }
        playingMusicBeens = new DaoUtils(this).queryAllMessage().get(pos);
        RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
        Glide.with(this).load(playingMusicBeens.getAlbumpic()).apply(requestOptions).into(lrcBinding.ivBac);
        lrcVM.getLrc(playingMusicBeens.getRid());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Glide.with(getContext()).pauseRequests();
    }


    @Override
    public void PlayChange(PlayingMusicBeens playingMusicBeens) {
        RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
        Glide.with(getContext()).load(playingMusicBeens.getAlbumpic()).apply(requestOptions).into(lrcBinding.ivBac);
        lrcVM.getLrc(playingMusicBeens.getRid());
    }
}

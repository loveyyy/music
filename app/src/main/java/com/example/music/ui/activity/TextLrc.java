package com.example.music.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.music.R;
import com.example.music.databinding.LrcBinding;
import com.example.music.model.LrcBeen;
import com.example.music.model.BaseRespon;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.PlayServer;
import com.example.music.utils.PlayController;
import com.example.music.utils.greendao.DaoUtils;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;
import com.example.music.viewmodel.Lrc_VM;
import com.jaeger.library.StatusBarUtil;


/**
 * Created by Administrator on 2018/7/3.
 */

public class TextLrc extends AppCompatActivity implements PlayController.PlayNextMusic {
    private LrcBinding lrcBinding;
    private Lrc_VM lrc_vm;
    private PlayController playController;
    private PlayingMusicBeens playingMusicBeens;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lrcBinding= DataBindingUtil.setContentView(this,R.layout.lrc);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        lrc_vm= ViewModelProviders.of(this).get(Lrc_VM.class);
        playController=new PlayController(this);
        playController.SetOnPlayNextMusic(this);
        setonclick();
        initdata();
    }

    public void setonclick(){
        lrc_vm.lrc.observe(this, new Observer<BaseRespon<LrcBeen>>() {
            @Override
            public void onChanged(BaseRespon<LrcBeen> baseRespon) {
                lrcBinding.lrctext.init(baseRespon.getData().getLrclist());
                RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
                Glide.with(getApplicationContext()).load(playingMusicBeens.getAlbumpic()).apply(requestOptions).into(lrcBinding.ivBac);
            }
        });
        lrcBinding.ibLrcLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playingMusicBeens=playController.play_last();
                lrc_vm.Get_lrc(playingMusicBeens.getRid(),"9e7818b0-1a2e-11ea-88f6-3d2b7f71a652");
            }
        });
        lrcBinding.ibLrcNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playingMusicBeens=playController.play_Next();
                lrc_vm.Get_lrc(playingMusicBeens.getRid(),"9e7818b0-1a2e-11ea-88f6-3d2b7f71a652");
            }
        });
        lrcBinding.ibLrcPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int state = playController.play_Paush();
                if(state== PlayServer.PLAYING){
                    lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
                }else if(state==PlayServer.PAUSE){
                    lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_play);
                }else{
                    lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
                }
            }
        });
    }

    public void initdata() {
        Intent intent =getIntent();
        int pos=intent.getIntExtra("pos",0);
        if(playController.get_state()==PlayServer.PLAYING){
            lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
        }else if(playController.get_state()==PlayServer.PAUSE){
            lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_play);
        }else{
            lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
        }
        playingMusicBeens = new DaoUtils(this).queryAllMessage().get(pos);
        RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
        Glide.with(this).load(playingMusicBeens.getAlbumpic()).apply(requestOptions).into(lrcBinding.ivBac);
        lrc_vm.Get_lrc(playingMusicBeens.getRid(),"9e7818b0-1a2e-11ea-88f6-3d2b7f71a652");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        playController.onDestory();
    }

    @Override
    public void OnPlayNextMusic(int pos) {
        playingMusicBeens = new DaoUtils(this).queryAllMessage().get(pos);
        RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
        Glide.with(this).load(playingMusicBeens.getAlbumpic()).apply(requestOptions).into(lrcBinding.ivBac);
        lrc_vm.Get_lrc(playingMusicBeens.getRid(),"9e7818b0-1a2e-11ea-88f6-3d2b7f71a652");
    }
}

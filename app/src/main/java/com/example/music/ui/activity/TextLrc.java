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
import com.example.music.entry.LrcBeen;
import com.example.music.model.BaseRespon;
import com.example.music.utils.PlayController;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;
import com.example.music.viewmodel.Lrc_VM;


/**
 * Created by Administrator on 2018/7/3.
 */

public class TextLrc extends AppCompatActivity {
    private LrcBinding lrcBinding;
    private Lrc_VM lrc_vm;
    private PlayController playController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lrcBinding= DataBindingUtil.setContentView(this,R.layout.lrc);
        lrc_vm= ViewModelProviders.of(this).get(Lrc_VM.class);
        playController=new PlayController(this);
        setonclick();
        initdata();
    }

    public void setonclick(){
        lrc_vm.lrc.observe(this, new Observer<BaseRespon<LrcBeen>>() {
            @Override
            public void onChanged(BaseRespon<LrcBeen> baseRespon) {
                lrcBinding.lrctext.send(baseRespon.getData().getLrclist());
            }
        });
        lrcBinding.ibLrcLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lrc_vm.Get_lrc(getBaseContext(),playController.play_last(),"9e7818b0-1a2e-11ea-88f6-3d2b7f71a652");
            }
        });
        lrcBinding.ibLrcNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lrc_vm.Get_lrc(getBaseContext(),playController.play_Next(),"9e7818b0-1a2e-11ea-88f6-3d2b7f71a652");
            }
        });
        lrcBinding.ibLrcPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playController.play_Paush();
                if(playController.get_state()==1){
                    lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_stop);
                }else{
                    lrcBinding.ibLrcPlay.setBackgroundResource(R.drawable.ic_lrc_play);
                }

            }
        });
    }

    public void initdata() {
        Intent intent =getIntent();
        String musicid=intent.getStringExtra("musicid");
        RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
        Glide.with(this).load(intent.getStringExtra("image")).apply(requestOptions).into(lrcBinding.ivBac);
        lrc_vm.Get_lrc(this,String.valueOf(musicid),"9e7818b0-1a2e-11ea-88f6-3d2b7f71a652");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

package com.example.music.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.music.R;
import com.example.music.entry.LrcBeen;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;
import com.example.music.view.lrcText;
import com.example.music.viewmodel.Lrc_VM;


/**
 * Created by Administrator on 2018/7/3.
 */

public class TextLrc extends AppCompatActivity {
    private lrcText lrctext;
    private ImageView iv_bac;
    private Lrc_VM lrc_vm;
    private int progress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lrc);
        lrc_vm= ViewModelProviders.of(this).get(Lrc_VM.class);
        initview();
        setonclick();
        initdata();
    }

    public void initview() {
        lrctext=findViewById(R.id.lrctext);
        iv_bac=findViewById(R.id.iv_bac);
    }

    public void setonclick(){
        lrc_vm.lrc.observe(this, new Observer<BaseRespon<LrcBeen>>() {
            @Override
            public void onChanged(BaseRespon<LrcBeen> baseRespon) {
                lrctext.send(baseRespon.getData().getLrclist());
                lrctext.sendp(progress);
            }
        });
    }

    public void initdata() {
        Intent intent =getIntent();
        String musicid=intent.getStringExtra("musicid");
        progress=intent.getIntExtra("progress",0);
        Glide.with(this).load(intent.getStringExtra("image")).into(iv_bac);
        lrc_vm.Get_lrc(this,String.valueOf(musicid),"9e7818b0-1a2e-11ea-88f6-3d2b7f71a652");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

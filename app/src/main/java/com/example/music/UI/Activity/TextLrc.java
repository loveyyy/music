package com.example.music.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.music.Beens.LrcBeen;
import com.example.music.R;
import com.example.music.Utils.Rereofit.Api;
import com.example.music.Utils.Rereofit.ApiResponse;
import com.example.music.Utils.Rereofit.ApiSubscribe;
import com.example.music.View.lrcText;


/**
 * Created by Administrator on 2018/7/3.
 */

public class TextLrc extends BaseActivity {
    private lrcText lrctext;
    private ImageView iv_bac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int onlayout() {
        return R.layout.lrc;
    }

    @Override
    public void initview() {
        lrctext=findViewById(R.id.lrctext);
        iv_bac=findViewById(R.id.iv_bac);
    }

    @Override
    public void initdata() {
        Intent intent =getIntent();
        int musicid=intent.getIntExtra("musicid",0);
        final int progress=intent.getIntExtra("progress",0);
        String url=intent.getStringExtra("image");
        Glide.with(this).load(url).into(iv_bac);
        Api.getInstance().iRetrofit.getmusic_lrc("http://www.kuwo.cn/newh5/singles/songinfoandlrc",
                musicid,"4ea6be30-a9eb-11e9-b6f5-ab365f00f113").compose(ApiSubscribe.<LrcBeen>io_main()).
                subscribe(new ApiResponse<LrcBeen>(this) {
                    @Override
                    public void success(LrcBeen data) {
                        lrctext.send(data.getData());
                        lrctext.sendp(progress);
                    }
                });

    }

    @Override
    public void setlistener() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

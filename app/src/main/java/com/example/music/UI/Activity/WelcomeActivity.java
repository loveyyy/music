package com.example.music.UI.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.music.R;

/**
 * Created by Administrator on 2018/7/7.
 */

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_wlcome;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(WelcomeActivity.this,MainHostActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    @Override
    public int onlayout() {
        return R.layout.welcome;

    }

    @Override
    public void initview() {
    iv_wlcome=findViewById(R.id.Iv_wlcome);
    }

    @Override
    public void initdata() {
    }

    @Override
    public void setlistener() {
        iv_wlcome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Iv_wlcome:
                break;
        }
    }
}

package com.example.music.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.music.R;
import com.example.music.databinding.WelcomeBinding;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/7/7.
 */

public class WelcomeActivity extends AppCompatActivity {
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent=new Intent();
            intent.setClass(WelcomeActivity.this,MainAcvity.class);
            startActivity(intent);
            finish();
        }
    };
    private WelcomeBinding welcomeBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeBinding= DataBindingUtil.setContentView(this,R.layout.welcome);
        welcomeBinding.IvWlcome.setBackgroundResource(R.drawable.welcome);

        Api.getInstance().iRetrofit.all().compose(ApiSubscribe.<ResponseBody>io_main())
                .subscribe(new ApiResponse<ResponseBody>(this) {
                    @Override
                    public void success(ResponseBody data) {
                        handler.sendEmptyMessageDelayed(10,2000);
                    }
                });


    }


}

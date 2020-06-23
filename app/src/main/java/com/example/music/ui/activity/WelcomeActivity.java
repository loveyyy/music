package com.example.music.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.R;
import com.example.music.databinding.WelcomeBinding;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.jaeger.library.StatusBarUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        welcomeBinding.IvWlcome.setBackgroundResource(R.drawable.welcome);

        Api.getInstance().iRetrofit.all().compose(ApiSubscribe.<ResponseBody>io_main())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        handler.sendEmptyMessageDelayed(10,2000);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


}

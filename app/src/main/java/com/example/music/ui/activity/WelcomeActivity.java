package com.example.music.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.music.R;
import com.example.music.databinding.WelcomeBinding;
import com.example.music.http.Api;
import com.example.music.http.RxHelper;
import com.example.music.ui.base.BaseActivity;
import com.example.music.ui.base.BaseVM;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/7/7.
 */

public class WelcomeActivity extends BaseActivity<WelcomeBinding, BaseVM> {
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent=new Intent();
            intent.setClass(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };

    @Override
    public int getLayout() {
        return R.layout.welcome;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(WelcomeBinding bindView) {
    }

    @Override
    protected void setVM(BaseVM vm) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Api.getInstance().iRetrofit.all().compose(RxHelper.observableIO2Main(getContext()))
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
                        handler.sendEmptyMessageDelayed(10,2000);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}

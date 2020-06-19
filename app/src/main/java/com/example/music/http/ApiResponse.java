package com.example.music.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.music.model.BaseRespon;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public abstract class ApiResponse<T> implements Observer<T> {
    private Disposable d;

    public ApiResponse() {

    }

    public abstract void success(T data);


    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(T t) {
        if (d.isDisposed()) {
            d.dispose();
        }
        success(t);
    }

    @Override
    public void onError(Throwable e) {
        if (d.isDisposed()) {
            d.dispose();
        }
        LogUtils.e(e.getMessage());
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
    }

}

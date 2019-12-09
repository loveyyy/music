package com.example.music.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ApiResponse<T> implements Observer<T> {
    private Context mContext;
    private Disposable d;

    public ApiResponse(Context context) {
        mContext=context;
    }

    public abstract void success(T data);


    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (!NetworkUtils.isConnected()) {
            Toast.makeText(mContext,"未连接网络",Toast.LENGTH_SHORT).show();
            if (d.isDisposed()) {
                d.dispose();
            }
        }
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
        Log.e(mContext.getClass().getName(),e.getMessage());
        Toast.makeText(mContext,ApiException.exceptionHandler(e),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
    }

}

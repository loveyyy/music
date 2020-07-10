package com.example.music.http;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.model.BaseRespon;
import com.example.music.ui.MyApplication;
import com.example.music.utils.ACache;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ApiResponse<T extends BaseRespon> implements Observer<T> {
    private Disposable d;
    private ACache aCache = ACache.get(MyApplication.getContext());
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
        try {
            aCache.put("reqid", t.getReqId());
        }catch (NullPointerException e){
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

package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.RxHelper;
import com.example.music.model.LrcBeen;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-09
 */
public class LrcVM extends BaseVM {
    public MutableLiveData<BaseRespon<LrcBeen>> lrc=new MutableLiveData<>();

    public LrcVM(@NonNull Application application) {
        super(application);
    }

    public void getLrc(String music_id){
        Api.getInstance().iRetrofit.music_lrc(music_id,getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<LrcBeen>>() {
                    @Override
                    public void success(BaseRespon<LrcBeen> data) {
                        lrc.setValue(data);
                    }
                });
    }

}

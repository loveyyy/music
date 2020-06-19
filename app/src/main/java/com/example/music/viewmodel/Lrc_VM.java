package com.example.music.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.model.LrcBeen;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-09
 */
public class Lrc_VM  extends BaseVM {
    public MutableLiveData<BaseRespon<LrcBeen>> lrc=new MutableLiveData<>();

    public Lrc_VM(@NonNull Application application) {
        super(application);
    }

    public void Get_lrc(String music_id, String reqId){
        Api.getInstance().iRetrofit.music_lrc(music_id,reqId).compose(ApiSubscribe.<BaseRespon<LrcBeen>>io_main())
                .subscribe(new ApiResponse<BaseRespon<LrcBeen>>() {
                    @Override
                    public void success(BaseRespon<LrcBeen> data) {
                        lrc.setValue(data);
                    }
                });
    }

}

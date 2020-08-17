package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.BangMusicList;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-05
 */
public class BangMenuVm extends BaseVM {

    public MutableLiveData<BaseRespon<BangMusicList>> bangMusicList=new MutableLiveData<>();

    public BangMenuVm(@NonNull Application application) {
        super(application);
    }

    public void getBangMusicList(String bangId,String pn,String rn){
        Api.getInstance().iRetrofit.bangMusicList(bangId,pn,rn,getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<BangMusicList>>() {
                    @Override
                    public void success(BaseRespon<BangMusicList> data) {
                        bangMusicList.setValue(data);
                    }
                });
    }

}

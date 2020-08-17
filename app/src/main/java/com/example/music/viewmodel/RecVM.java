package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.BaseRespon;
import com.example.music.model.RecListInfo;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-10
 */
public class RecVM extends BaseVM {
    public MutableLiveData<BaseRespon<RecListInfo>> recListInfo=new MutableLiveData<>();

    public RecVM(@NonNull Application application) {
        super(application);
    }

    public void getRecListInfo(String pid,  String pn, String rn){
        Api.getInstance().iRetrofit.musicListInfo(pid,pn,rn,getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<RecListInfo>>() {
                    @Override
                    public void success(BaseRespon<RecListInfo> data) {
                        recListInfo.setValue(data);
                    }
                });
    }
}

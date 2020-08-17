package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.BaseRespon;
import com.example.music.model.RecList;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-10
 */
public class FragmentMusicVM extends BaseVM {
    public MutableLiveData<BaseRespon<RecList>> musicListMore=new MutableLiveData<>();
    public FragmentMusicVM(@NonNull Application application) {
        super(application);
    }

    public void getMusicListMore(String loginUid,String loginSid, String pn, String rn, String order){
        Api.getInstance().iRetrofit.musicListMore(loginUid,loginSid,pn,rn,order,getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<RecList>>() {
                    @Override
                    public void success(BaseRespon<RecList> data) {
                        musicListMore.setValue(data);
                    }
                });
    }



}

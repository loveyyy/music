package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.ArtistMv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2020-06-22
 */
public class FragmentMvVM extends BaseVM {
    public MutableLiveData<BaseRespon<ArtistMv>> mvList=new MutableLiveData<>();
    public FragmentMvVM(@NonNull Application application) {
        super(application);
    }

    public void getMvList(String pid,String pn,String rn){
        Api.getInstance().iRetrofit.mvList(pid,pn,rn,"1",getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<ArtistMv>>() {
                    @Override
                    public void success(BaseRespon<ArtistMv> data) {
                        mvList.setValue(data);
                    }
                });
    }
}

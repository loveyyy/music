package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.Bang_Music_list;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Bang_Meau_Vm  extends BaseVM {

    public MutableLiveData<BaseRespon<Bang_Music_list>> Bang_Music_list=new MutableLiveData<>();

    public Bang_Meau_Vm(@NonNull Application application) {
        super(application);
    }

    public void Get_Bang_Music_list(String bangId,String pn,String rn){
        Api.getInstance().iRetrofit.Bang_Music_list(bangId,pn,rn,getaCache().getAsString("reqid")).compose(ApiSubscribe.<BaseRespon<Bang_Music_list>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Bang_Music_list>>() {
                    @Override
                    public void success(BaseRespon<Bang_Music_list> data) {
                        Bang_Music_list.setValue(data);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

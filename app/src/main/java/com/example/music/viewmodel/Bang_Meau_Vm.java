package com.example.music.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.Bang_Music_list;
import com.example.music.model.BaseRespon;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Bang_Meau_Vm extends ViewModel {
    public MutableLiveData<BaseRespon<Bang_Music_list>> Bang_Music_list=new MutableLiveData<>();

    public void Get_Bang_Music_list(Context context , String bangId,String pn,String rn,String reqId){
        Api.getInstance().iRetrofit.Bang_Music_list(bangId,pn,rn,reqId).compose(ApiSubscribe.<BaseRespon<Bang_Music_list>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Bang_Music_list>>(context) {
                    @Override
                    public void success(BaseRespon<Bang_Music_list> data) {
                        Bang_Music_list.setValue(data);
                    }
                });
    }

}

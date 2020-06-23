package com.example.music.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.model.Music_list;
import com.example.music.model.Rec_List;
import com.example.music.ui.MyApplication;
import com.example.music.ui.base.BaseVM;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-10
 */
public class Frament_Music_VM extends BaseVM {
    public MutableLiveData<BaseRespon<Rec_List>> Music_list_more=new MutableLiveData<>();
    public Frament_Music_VM(@NonNull Application application) {
        super(application);
    }

    public void Get_Music_list_more(String loginUid,String loginSid, String pn, String rn, String order){
        Api.getInstance().iRetrofit.Music_list_more(loginUid,loginSid,pn,rn,order,getaCache().getAsString("reqid")).compose(ApiSubscribe.<BaseRespon<Rec_List>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Rec_List>>() {
                    @Override
                    public void success(BaseRespon<Rec_List> data) {
                        Music_list_more.setValue(data);
                    }
                });
    }



}

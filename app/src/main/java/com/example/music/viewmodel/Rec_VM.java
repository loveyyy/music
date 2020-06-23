package com.example.music.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.model.Rec_List;
import com.example.music.model.Rec_List_Info;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-10
 */
public class Rec_VM extends BaseVM {
    public MutableLiveData<BaseRespon<Rec_List_Info>> rec_list_info=new MutableLiveData<>();

    public Rec_VM(@NonNull Application application) {
        super(application);
    }

    public void Get_rec_list_info(String pid,  String pn, String rn){
        Api.getInstance().iRetrofit.Music_list_info(pid,pn,rn,getaCache().getAsString("reqid")).compose(ApiSubscribe.<BaseRespon<Rec_List_Info>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Rec_List_Info>>() {
                    @Override
                    public void success(BaseRespon<Rec_List_Info> data) {
                        rec_list_info.setValue(data);
                    }
                });
    }
}

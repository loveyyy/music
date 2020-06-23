package com.example.music.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.Artist_list;
import com.example.music.model.Bananer;
import com.example.music.model.Bang_list;
import com.example.music.model.BaseRespon;
import com.example.music.model.Music_list;
import com.example.music.ui.MyApplication;
import com.example.music.ui.base.BaseVM;
import com.example.music.utils.ACache;

import java.util.List;

/**
 * Create By morningsun  on 2019-11-29
 */
public class Frament_Rec_VM extends BaseVM {

    public MutableLiveData<BaseRespon<List<Bananer>>> Bananer_list=new MutableLiveData<>();
    public Frament_Rec_VM(@NonNull Application application) {
        super(application);
    }

    public void Get_Bananer_list(){
        Api.getInstance().iRetrofit.Bananer_list(getaCache().getAsString("reqid")).compose(ApiSubscribe.<BaseRespon<List<Bananer>>>io_main())
                .subscribe(new ApiResponse<BaseRespon<List<Bananer>>>() {
                    @Override
                    public void success(BaseRespon<List<Bananer>> data) {
                        Bananer_list.setValue(data);
                    }
                });
    }


    public MutableLiveData<BaseRespon<Music_list>> Music_list=new MutableLiveData<>();

    public void Get_Music_list(String loginUdi){
        Api.getInstance().iRetrofit.Music_list(loginUdi,getaCache().getAsString("reqid")).compose(ApiSubscribe.<BaseRespon<Music_list>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Music_list>>() {
                    @Override
                    public void success(BaseRespon<Music_list> data) {
                        Music_list.setValue(data);
                    }
                });
    }

    public MutableLiveData<BaseRespon<List<Bang_list>>> Bang_meau=new MutableLiveData<>();

    public void Get_Bang_list(){
        Api.getInstance().iRetrofit.Bang_list(getaCache().getAsString("reqid")).compose(ApiSubscribe.<BaseRespon<List<Bang_list>>>io_main())
                .subscribe(new ApiResponse<BaseRespon<List<Bang_list>>>() {
                    @Override
                    public void success(BaseRespon<List<Bang_list>> data) {
                        Bang_meau.setValue(data);
                    }
                });
    }


    public MutableLiveData<BaseRespon<Artist_list>> Artist_list=new MutableLiveData<>();

    public void Get_Artist_list(String category,String pn,String rn){
        Api.getInstance().iRetrofit.Artist_list(category,pn,rn,getaCache().getAsString("reqid")).compose(ApiSubscribe.<BaseRespon<Artist_list>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Artist_list>>() {
                    @Override
                    public void success(BaseRespon<Artist_list> data) {
                        Artist_list.setValue(data);
                    }
                });
    }

}

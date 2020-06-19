package com.example.music.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.Artist_list;
import com.example.music.model.Bananer;
import com.example.music.model.Bang_list;
import com.example.music.model.BaseRespon;
import com.example.music.model.Music_list;
import com.example.music.ui.base.BaseVM;

import java.util.List;

/**
 * Create By morningsun  on 2019-11-29
 */
public class Frament_Rec_VM extends BaseVM {


    public MutableLiveData<BaseRespon<List<Bananer>>> Bananer_list=new MutableLiveData<>();

    public Frament_Rec_VM(@NonNull Application application) {
        super(application);
    }

    public void Get_Bananer_list(String reqId){
        Api.getInstance().iRetrofit.Bananer_list(reqId).compose(ApiSubscribe.<BaseRespon<List<Bananer>>>io_main())
                .subscribe(new ApiResponse<BaseRespon<List<Bananer>>>() {
                    @Override
                    public void success(BaseRespon<List<Bananer>> data) {
                        Bananer_list.setValue(data);
                    }
                });
    }


    public MutableLiveData<BaseRespon<Music_list>> Music_list=new MutableLiveData<>();

    public void Get_Music_list(String reqId,String loginUdi){
        Api.getInstance().iRetrofit.Music_list(loginUdi,reqId).compose(ApiSubscribe.<BaseRespon<Music_list>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Music_list>>() {
                    @Override
                    public void success(BaseRespon<Music_list> data) {
                        Music_list.setValue(data);
                    }
                });
    }

    public MutableLiveData<BaseRespon<List<Bang_list>>> Bang_meau=new MutableLiveData<>();

    public void Get_Bang_list(String reqId){
        Api.getInstance().iRetrofit.Bang_list(reqId).compose(ApiSubscribe.<BaseRespon<List<Bang_list>>>io_main())
                .subscribe(new ApiResponse<BaseRespon<List<Bang_list>>>() {
                    @Override
                    public void success(BaseRespon<List<Bang_list>> data) {
                        Bang_meau.setValue(data);
                    }
                });
    }


    public MutableLiveData<BaseRespon<Artist_list>> Artist_list=new MutableLiveData<>();

    public void Get_Artist_list(Context context,String category,String pn,String rn,String reqId){
        Api.getInstance().iRetrofit.Artist_list(category,pn,rn,reqId).compose(ApiSubscribe.<BaseRespon<Artist_list>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Artist_list>>() {
                    @Override
                    public void success(BaseRespon<Artist_list> data) {
                        Artist_list.setValue(data);
                    }
                });
    }

}

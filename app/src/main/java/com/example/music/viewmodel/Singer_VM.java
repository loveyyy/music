package com.example.music.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.Arisit_Info;
import com.example.music.model.Artist_Album;
import com.example.music.model.Artist_Music;
import com.example.music.model.Artist_Mv;
import com.example.music.model.Artist_list;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Singer_VM extends BaseVM {
    public MutableLiveData<BaseRespon<Artist_list>> Artist_list=new MutableLiveData<>();

    public Singer_VM(@NonNull Application application) {
        super(application);
    }

    public void Get_Artist_list(String category,String pn,String rn,String reqId){
        Api.getInstance().iRetrofit.Artist_list(category,pn,rn,reqId).compose(ApiSubscribe.<BaseRespon<Artist_list>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Artist_list>>() {
                    @Override
                    public void success(BaseRespon<com.example.music.model.Artist_list> data) {
                        Artist_list.setValue(data);
                    }
                });
    }


    public MutableLiveData<BaseRespon<Arisit_Info>> Artist_info=new MutableLiveData<>();

    public void Get_Artist_info(String artistid,String reqId){
        Api.getInstance().iRetrofit.Artist_info(artistid,reqId).compose(ApiSubscribe.<BaseRespon<Arisit_Info>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Arisit_Info>>() {
                    @Override
                    public void success(BaseRespon<Arisit_Info> data) {
                        Artist_info.setValue(data);
                    }
                });
    }

    public MutableLiveData<BaseRespon<Artist_Music>> Artist_Music=new MutableLiveData<>();

    public void Get_Artist_Music(String artistid,String pn,String rn,String reqId){
        Api.getInstance().iRetrofit.Artist_Music(artistid,pn,rn,reqId).compose(ApiSubscribe.<BaseRespon<Artist_Music>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Artist_Music>>() {
                    @Override
                    public void success(BaseRespon<Artist_Music> data) {
                        Artist_Music.setValue(data);
                    }
                });
    }

    public MutableLiveData<BaseRespon<Artist_Album>> Artist_Album=new MutableLiveData<>();

    public void Get_Artist_Album(String artistid,String pn,String rn,String reqId){
        Api.getInstance().iRetrofit.Artist_Album(artistid,pn,rn,reqId).compose(ApiSubscribe.<BaseRespon<Artist_Album>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Artist_Album>>() {
                    @Override
                    public void success(BaseRespon<Artist_Album> data) {
                        Artist_Album.setValue(data);
                    }
                });
    }

    public MutableLiveData<BaseRespon<Artist_Mv>> Artist_Mv=new MutableLiveData<>();

    public void Get_Artist_Mv(String artistid,String pn,String rn,String reqId){
        Api.getInstance().iRetrofit.Artist_Mv(artistid,pn,rn,reqId).compose(ApiSubscribe.<BaseRespon<Artist_Mv>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Artist_Mv>>() {
                    @Override
                    public void success(BaseRespon<Artist_Mv> data) {
                        Artist_Mv.setValue(data);
                    }
                });
    }


}

package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.Artist_Mv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2020-06-22
 */
public class FramentMv_VM extends BaseVM {
    public MutableLiveData<BaseRespon<Artist_Mv>> mv_list=new MutableLiveData<>();
    public FramentMv_VM(@NonNull Application application) {
        super(application);
    }

    public void Get_Mv_List(String pid,String pn,String rn){
        Api.getInstance().iRetrofit.MvList(pid,pn,rn,"1",getaCache().getAsString("reqid"))
                .compose(ApiSubscribe.<BaseRespon<Artist_Mv>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Artist_Mv>>() {
                    @Override
                    public void success(BaseRespon<Artist_Mv> data) {
                        mv_list.setValue(data);
                    }
                });
    }
}

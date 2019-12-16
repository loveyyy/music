package com.example.music.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.model.Rec_List;
import com.example.music.model.Rec_List_Info;

/**
 * Create By morningsun  on 2019-12-10
 */
public class Rec_VM extends ViewModel {
    public MutableLiveData<BaseRespon<Rec_List_Info>> rec_list_info=new MutableLiveData<>();

    public void Get_rec_list_info(Context context, String pid,  String pn, String rn, String reqid){
        Api.getInstance().iRetrofit.Music_list_info(pid,pn,rn,reqid).compose(ApiSubscribe.<BaseRespon<Rec_List_Info>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Rec_List_Info>>(context) {
                    @Override
                    public void success(BaseRespon<Rec_List_Info> data) {
                        rec_list_info.setValue(data);
                    }
                });
    }
}

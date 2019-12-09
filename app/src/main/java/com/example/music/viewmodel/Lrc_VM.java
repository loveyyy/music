package com.example.music.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.entry.LrcBeen;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;

/**
 * Create By morningsun  on 2019-12-09
 */
public class Lrc_VM  extends ViewModel {
    public MutableLiveData<BaseRespon<LrcBeen>> lrc=new MutableLiveData<>();

    public void Get_lrc(Context context, String music_id, String reqId){
        Api.getInstance().iRetrofit.music_lrc(music_id,reqId).compose(ApiSubscribe.<BaseRespon<LrcBeen>>io_main())
                .subscribe(new ApiResponse<BaseRespon<LrcBeen>>(context) {
                    @Override
                    public void success(BaseRespon<LrcBeen> data) {
                        lrc.setValue(data);
                    }
                });
    }

}

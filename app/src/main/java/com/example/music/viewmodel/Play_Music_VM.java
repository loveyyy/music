package com.example.music.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;

/**
 * Create By morningsun  on 2019-12-06
 */
public class Play_Music_VM extends ViewModel {

    public MutableLiveData<BaseRespon> play_music=new MutableLiveData<>();

    public void Get_Play_music(Context context, String rid, String time, String reqId){
        Api.getInstance().iRetrofit.music_info("mp3",rid,"url","convert_url3","128kmp3",
                "web",time,reqId).compose(ApiSubscribe.<BaseRespon>io_main())
                .subscribe(new ApiResponse<BaseRespon>(context) {
                    @Override
                    public void success(BaseRespon data) {
                        play_music.setValue(data);
                    }
                });
    }
}

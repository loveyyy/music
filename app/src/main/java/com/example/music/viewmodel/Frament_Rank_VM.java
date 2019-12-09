package com.example.music.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.Bang_meau;
import com.example.music.model.BaseRespon;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Frament_Rank_VM extends ViewModel {

    public MutableLiveData<BaseRespon<List<Bang_meau>>> Bang_Menu=new MutableLiveData<>();

    public void Get_Bang_Menu(Context context ,String reqId){
        Api.getInstance().iRetrofit.Bang_Menu(reqId).compose(ApiSubscribe.<BaseRespon<List<Bang_meau>>>io_main())
                .subscribe(new ApiResponse<BaseRespon<List<Bang_meau>>>(context) {
                    @Override
                    public void success(BaseRespon<List<Bang_meau>> data) {
                        Bang_Menu.setValue(data);
                    }
                });
    }
}

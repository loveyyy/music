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
import com.example.music.model.Bang_meau;
import com.example.music.model.BaseRespon;
import com.example.music.ui.MyApplication;
import com.example.music.ui.base.BaseVM;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Frament_Rank_VM extends BaseVM {

    public MutableLiveData<BaseRespon<List<Bang_meau>>> Bang_Menu=new MutableLiveData<>();

    public Frament_Rank_VM(@NonNull Application application) {
        super(application);
    }

    public void Get_Bang_Menu(){
        Api.getInstance().iRetrofit.Bang_Menu(getaCache().getAsString("reqid")).compose(ApiSubscribe.<BaseRespon<List<Bang_meau>>>io_main())
                .subscribe(new ApiResponse<BaseRespon<List<Bang_meau>>>() {
                    @Override
                    public void success(BaseRespon<List<Bang_meau>> data) {
                        Bang_Menu.setValue(data);
                    }
                });
    }
}

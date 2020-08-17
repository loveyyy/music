package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.BangMenu;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class FragmentRankVM extends BaseVM {

    public MutableLiveData<BaseRespon<List<BangMenu>>> Bang_Menu=new MutableLiveData<>();

    public FragmentRankVM(@NonNull Application application) {
        super(application);
    }

    public void getBangMenu(){
        Api.getInstance().iRetrofit.bangMenu(getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<List<BangMenu>>>() {
                    @Override
                    public void success(BaseRespon<List<BangMenu>> data) {
                        Bang_Menu.setValue(data);
                    }
                });
    }
}

package com.example.music.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.Bang_Music_list;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;
import com.example.music.ui.base.IModelView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Bang_Meau_Vm  extends BaseVM {

    public MutableLiveData<BaseRespon<Bang_Music_list>> Bang_Music_list=new MutableLiveData<>();

    public Bang_Meau_Vm(@NonNull Application application) {
        super(application);
    }

    public void Get_Bang_Music_list(String bangId,String pn,String rn,String reqId){
        Api.getInstance().iRetrofit.Bang_Music_list(bangId,pn,rn,reqId).compose(ApiSubscribe.<BaseRespon<Bang_Music_list>>io_main())
                .subscribe(new ApiResponse<BaseRespon<Bang_Music_list>>() {
                    @Override
                    public void success(BaseRespon<Bang_Music_list> data) {
                        Bang_Music_list.setValue(data);
                    }
                });
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
        Log.e("LifeCycle","onAny");
    }

    @Override
    public void onCreate() {
        Log.e("LifeCycle","onCreate");
    }

    @Override
    public void onDestroy() {
        Log.e("LifeCycle","onDestroy");
    }

    @Override
    public void onStart() {
        Log.e("LifeCycle","onStart");
    }

    @Override
    public void onStop() {
        Log.e("LifeCycle","onStop");
    }

    @Override
    public void onResume() {
        Log.e("LifeCycle","onResume");
    }

    @Override
    public void onPause() {
        Log.e("LifeCycle","onPause");
    }
}

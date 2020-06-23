package com.example.music.ui.base;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.example.music.ui.MyApplication;
import com.example.music.utils.ACache;

/**
 * Create By morningsun  on 2020-06-11
 */
public class BaseVM extends AndroidViewModel implements IModelView {
   public ACache aCache;

    public BaseVM(@NonNull Application application) {
        super(application);
     aCache=ACache.get(application);
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    public ACache getaCache(){
        return aCache;
    }
}

package com.example.music.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.music.utils.ACache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.disposables.Disposable;

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseVM> extends Fragment {
    private Context mContext;
    private VM vm;
    private ACache aCache;
    private boolean isFirstLoad = true;
    private boolean needRefresh =false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DB db = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getContentViewId(), container, false);
        db.setLifecycleOwner(this.getViewLifecycleOwner());
        this.mContext = getActivity();

        aCache = ACache.get(mContext);
        if (vm == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseVM.class;
            }
            vm = (VM) new ViewModelProvider(this).get(modelClass);
        }
        initView(db);
        SetVM(vm);
        setOnClick();
        initData();
        getLifecycle().addObserver(vm);
        needRefresh=getNeedRefresh();
        return db.getRoot();
    }

    protected abstract int getContentViewId();

    protected abstract void initView(DB bindView);

    protected abstract void SetVM(VM vm);

    protected abstract void setOnClick();

    protected abstract void initData();

    protected abstract  boolean getNeedRefresh();

    public Context getMContext() {
        return mContext;
    }

    public void onLoadRetry() {
    }


    public  void LazyLoad(){

    }
    public  void  Refresh(){

    }


    public ACache getACache() {
        return aCache;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            isFirstLoad = false;
            LazyLoad();
        }else{
            if(needRefresh){
                Refresh();
            }
        }

    }



}

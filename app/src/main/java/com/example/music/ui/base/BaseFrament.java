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

import com.example.music.utils.ACache;

import io.reactivex.disposables.Disposable;

public abstract  class BaseFrament<DB extends ViewDataBinding> extends Fragment {
    private Disposable dis;
    private boolean hasCreateView;
    private boolean isFragmentVisible;
    private Context mContext;
    private DB db;
    private ACache aCache;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initVariable();
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db=DataBindingUtil.inflate(LayoutInflater.from(getContext()), getContentViewId(),container,false);
        this.mContext = getActivity();

        aCache=ACache.get(mContext);
        initview(db);
        SetVM();
        setonclick();
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
        return db.getRoot();
    }
    public abstract int getContentViewId();
    protected abstract void initview(DB bindView);
    protected abstract void SetVM();
    protected abstract void setonclick();
    public Context getMContext() {
        return mContext;
    }

    protected boolean isAttachedContext(){
        return getActivity() != null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(db==null){
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }
    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }
    protected void onFragmentVisibleChange(boolean isVisible) {
        if(!isVisible){
            if(dis!=null&&!dis.isDisposed()){
                dis.dispose();
            }
        }
    }
    public ACache getaCache(){
        return aCache;
    }
    public void  set_dis(Disposable dis){
        this.dis=dis;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dis!=null&&!dis.isDisposed()){
            dis.dispose();
        }
    }
}

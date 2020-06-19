package com.example.music.ui.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.music.R;
import com.example.music.utils.ACache;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<DB extends ViewDataBinding , VM extends BaseVM> extends AppCompatActivity {
    private Disposable dis;
    private ACache aCache;
    private VM vm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if(isLight()){
            StatusBarUtil.setLightMode(this);
            StatusBarUtil.setColor(this, this.getResources().getColor(R.color.colorPrimary),0);
        }else{
            StatusBarUtil.setTranslucentForImageViewInFragment(this,0, null);
        }

        DB db = DataBindingUtil.setContentView(this, getLayout());
        aCache=ACache.get(this);
        if (vm == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseVM.class;
            }
            vm = (VM) ViewModelProviders.of(this).get(modelClass);
        }
        getLifecycle().addObserver(vm);
        initView(db);
        setVM(vm);
        setListener();
        initData();
    }
    public abstract int getLayout();
    public abstract boolean isLight();
    protected abstract void initView(DB bindView);
    protected abstract void setVM(VM vm);
    protected abstract void setListener();
    protected abstract void initData();


    public Context getContext(){
        return BaseActivity.this;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public ACache getaCache(){
       return aCache;
    }

    public void setDid(Disposable dis){
        this.dis=dis;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dis!=null&&!dis.isDisposed()){
            dis.dispose();
        }
    }
}

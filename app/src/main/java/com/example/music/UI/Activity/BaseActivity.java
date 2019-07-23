package com.example.music.UI.Activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.Window;

/**
 * Created by Administrator on 2018/7/7.
 */

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(onlayout());
        initview();
        initdata();
        setlistener();
    }

    public abstract int  onlayout();
    public abstract void initview();
    public abstract void initdata();
    public  abstract void setlistener();
}

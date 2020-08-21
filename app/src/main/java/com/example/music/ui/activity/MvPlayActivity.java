package com.example.music.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.view.KeyEvent;
import android.view.OrientationEventListener;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.Interface.State;
import com.example.music.R;
import com.example.music.databinding.MvplayBinding;
import com.example.music.server.PlayMusicService;
import com.example.music.ui.base.BaseActivity;
import com.example.music.ui.base.BaseVM;
import com.example.music.utils.PlayController;

/**
 * Create By morningsun  on 2020-06-28
 */
public class MvPlayActivity extends BaseActivity<MvplayBinding, BaseVM> {
    private MvplayBinding mvplayBinding;
    private OrientationEventListener mOrientationListener;

    @Override
    public int getLayout() {
        return R.layout.mvplay;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(MvplayBinding bindView) {
        mvplayBinding=bindView;
    }

    @Override
    protected void setVM(BaseVM vm) {

    }

    @Override
    protected void setListener() {
        mOrientationListener=new OrientationEventListener(this,SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return; // 手机平放时，检测不到有效的角度
                }
                // 只检测是否有四个角度的改变
                if (orientation > 350 || orientation < 10) {
                    // 0度：手机默认竖屏状态（home键在正下方）
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    LogUtils.e( "下");
                } else if (orientation > 80 && orientation < 100) {
                    // 90度：手机顺时针旋转90度横屏（home建在左侧）
                    LogUtils.e( "左");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (orientation > 170 && orientation < 190) {
                    // 180度：手机顺时针旋转180度竖屏（home键在上方）
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    LogUtils.e( "上");
                } else if (orientation > 260 && orientation < 280) {
                    // 270度：手机顺时针旋转270度横屏，（home键在右侧）
                    LogUtils.e( "右");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        };

    }

    @Override
    protected void initData() {
        mvplayBinding.playMv.setActivity(this);
        Intent intent=getIntent();
        if(PlayController.getInstance().getState()== State.PLAYING){
            PlayController.getInstance().playOrPause();
        }
        mvplayBinding.playMv.play(intent.getStringExtra("rid"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //变成竖屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }else{
                finish();
            }
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //变成横屏了
            mvplayBinding.playMv.setVideoParams(mvplayBinding.playMv,true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //变成竖屏了
            mvplayBinding.playMv.setVideoParams(mvplayBinding.playMv,false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrientationListener.enable();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mOrientationListener.disable();
    }
}

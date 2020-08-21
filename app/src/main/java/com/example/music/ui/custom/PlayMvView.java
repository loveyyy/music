package com.example.music.ui.custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.Interface.State;
import com.example.music.R;
import com.example.music.databinding.PlayermvBinding;
import com.example.music.model.MvProgree;
import com.example.music.server.PlayMusicService;
import com.example.music.utils.PlayController;
import com.example.music.utils.PlayMvController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Create By morningsun  on 2020-06-30
 */
public class PlayMvView extends RelativeLayout implements  GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener {
    private PlayermvBinding playermvBinding;
    private AudioManager mAudioManager;
    private Activity activity;
    private PlayMvController playMvController;
    private Timer timer;
    private GestureDetector detector;
    //方向变化时记录起始坐标
    private MotionEvent e1;
    //判断方向变化
    private float distanceY;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            playermvBinding.rlMv.setVisibility(GONE);
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    };


    public PlayMvView(Context context) {
        super(context);
    }

    public PlayMvView(Context context, AttributeSet attrs) {
        super(context, attrs);
        playermvBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.playermv,
                this, true);
        playMvController = PlayMvController.getInstance();
        detector = new GestureDetector(context, this);
        detector.setIsLongpressEnabled(false);
        detector.setOnDoubleTapListener(this);
        EventBus.getDefault().register(this);
        setonclick();
        mAudioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        if (PlayController.getInstance().getState() == State.PLAYING) {
            PlayController.getInstance().playOrPause();
        }
    }

    public PlayMvView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setonclick() {

        playermvBinding.ivMvPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playMvController.playOrPause();
            }
        });


        playermvBinding.ivMvBig.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    //变成竖屏
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //变成横屏了
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });

        playermvBinding.sbMv.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                playermvBinding.sbMv.setProgress(seekBar.getProgress());
                playMvController.setPro(seekBar.getProgress());
            }
        });

    }

    public void play(String rid) {
        playMvController.PlayMv(playermvBinding.sfv, rid);
        startTask();
    }


    private void startTask() {
        if (timer == null) {
            timer = new Timer();
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(10);
            }
        }, 5000, 1000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getMvPro(MvProgree mvProgree) {
        playermvBinding.sbMv.setProgress(mvProgree.getPro());
    }


    /**
     * 设置SurfaceView的参数
     */
    public void setVideoParams(RelativeLayout view, boolean isLand) {
        //获取surfaceView父布局的参数
        ViewGroup.LayoutParams rl_paramters = view.getLayoutParams();
        //获取SurfaceView的参数
        ViewGroup.LayoutParams sv_paramters = playermvBinding.sfv.getLayoutParams();
        //设置宽高比为16/9
        float screen_widthPixels = getResources().getDisplayMetrics().widthPixels;
        float screen_heightPixels = getResources().getDisplayMetrics().widthPixels * 9f / 16f;
        //取消全屏
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (isLand) {
            screen_heightPixels = getResources().getDisplayMetrics().heightPixels;
            //设置全屏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        rl_paramters.width = (int) screen_widthPixels;
        rl_paramters.height = (int) screen_heightPixels;

        //获取MediaPlayer的宽高
        int videoWidth = playMvController.getVideoW();
        int videoHeight = playMvController.getVideoH();

        if (videoWidth != 0) {
            float video_por = videoWidth / videoHeight;
            float screen_por = screen_widthPixels / screen_heightPixels;
            //16:9    16:12
            if (screen_por > video_por) {
                sv_paramters.height = (int) screen_heightPixels;
                sv_paramters.width = (int) (screen_heightPixels * screen_por);
            } else {
                //16:9  19:9
                sv_paramters.width = (int) screen_widthPixels;
                sv_paramters.height = (int) (screen_widthPixels / screen_por);
            }
            view.setLayoutParams(rl_paramters);
            playermvBinding.sfv.setLayoutParams(sv_paramters);
        } else {
            view.setLayoutParams(rl_paramters);
            playermvBinding.sfv.setLayoutParams(sv_paramters);
        }

    }

    /**
     * 2.设置 APP界面屏幕亮度值方法
     **/
    private void setAppScreenBrightness(int birghtessValue) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = birghtessValue / 255.0f;
        window.setAttributes(lp);
    }

    /**
     *获取APP界面屏幕亮度值
     **/
    private int getAppScreenBrightness() {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        return Math.round(lp.screenBrightness);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (playermvBinding.rlMv.getVisibility() == VISIBLE) {
            playermvBinding.rlMv.setVisibility(GONE);
        } else {
            playermvBinding.rlMv.setVisibility(VISIBLE);
            startTask();
        }
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVoluem =mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int light =getAppScreenBrightness();
        int progree = playermvBinding.sbMv.getProgress();
        this.e1=e1;
        this.distanceY=distanceY;
        if(this.distanceY>0&&distanceY<0||this.distanceY<0&&distanceY>0){
            this.e1=e2;
        }
        //向上滑动 调节音量
        if (Math.abs(e1.getY() - e2.getY()) > playermvBinding.sfv.getHeight()/maxVoluem && e1.getX()>playermvBinding.sfv.getWidth()/2 && e2.getX()>playermvBinding.sfv.getWidth()/2 && distanceY > 0) {
            volume++;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
        }
        // 向下滑动 调节音量
        if (Math.abs(e2.getY() - this.e1.getY()) > playermvBinding.sfv.getHeight()/maxVoluem && e1.getX()>playermvBinding.sfv.getWidth()/2 && e2.getX()>playermvBinding.sfv.getWidth()/2  && distanceY < 0) {
            volume--;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
        }
        //向上滑动 调节亮度  亮度相反 设置越小越亮
        if (Math.abs(e1.getY() - e2.getY()) > playermvBinding.sfv.getHeight()/255 && e1.getX()<playermvBinding.sfv.getWidth()/2 && e2.getX()<playermvBinding.sfv.getWidth()/2 && distanceY > 0) {
           light--;
            setAppScreenBrightness(light);
        }
        // 向下滑动 调节亮度 亮度相反 设置越大越暗
        if (Math.abs(e2.getY() - this.e1.getY()) > playermvBinding.sfv.getHeight()/255 && e1.getX()<playermvBinding.sfv.getWidth()/2 && e2.getX()<playermvBinding.sfv.getWidth()/2  && distanceY < 0) {
            light++;
            setAppScreenBrightness(light);
        }

        // 向左滑动
        if (Math.abs(e1.getX() - e2.getX()) > playermvBinding.sfv.getWidth()/100 && Math.abs(e1.getY()-e2.getY())<playermvBinding.sfv.getWidth()/2&& distanceX > 0) {
            //当前进度--
            progree--;
            playMvController.setPro(progree);
            playermvBinding.sbMv.setProgress(progree);
        }

        // 向右滑动
        if (Math.abs(e2.getX() - e1.getX()) > playermvBinding.sfv.getWidth()/100 && Math.abs(e1.getY()-e2.getY())<playermvBinding.sfv.getWidth()/2&& distanceX < 0) {
            //当前进度++
            progree++;
            playMvController.setPro(progree);
            playermvBinding.sbMv.setProgress(progree);
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        LogUtils.e("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LogUtils.e("onFling");
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        LogUtils.e("onSingleTapConfirmed");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        LogUtils.e("onDoubleTap");
        playMvController.playOrPause();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        LogUtils.e("onDoubleTapEvent");
        return false;
    }
}

package com.example.music.ui.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;

import com.example.music.R;
import com.example.music.ui.adapter.Vp_BannerAdapter;
import com.example.music.utils.imageutils.ScaleTransformer;
import com.example.music.databinding.CustombannerBinding;
import com.example.music.model.Bananer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CustomBanner extends RelativeLayout {
    private Context context;
    private boolean isruning = true;
    private Timer timer;
    private CustombannerBinding custombannerBinding;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            custombannerBinding.vpBanner.setCurrentItem(custombannerBinding.vpBanner.getCurrentItem() + 1, true);
        }
    };

    public CustomBanner(Context context) {
        super(context);
    }

    public CustomBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        custombannerBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custombanner,
                this,true);
        setonclick();
        timer = new Timer();
    }

    public CustomBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setonclick() {
        custombannerBinding.vpBanner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isruning = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isruning = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isruning = true;
                        break;
                    default:
                        isruning = true;
                        break;
                }
                return false;
            }
        });
    }

    public void initdata(List<Bananer> images) {
        custombannerBinding.vpBanner.setPageMargin(getResources().getDimensionPixelSize(R.dimen.dp_7));
        custombannerBinding.vpBanner.setOffscreenPageLimit(2);
        custombannerBinding.vpBanner.setPageTransformer(false, new ScaleTransformer());
        Vp_BannerAdapter vp_bannerAdapter = new Vp_BannerAdapter(context, images);
        custombannerBinding.vpBanner.setAdapter(vp_bannerAdapter);
    }

    public void start() {
        if(timer!=null){
            timer.cancel();
        }
        timer=new Timer();
        isruning = true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isruning) {
                    handler.sendEmptyMessage(10);
                }
            }
        }, 0, 5000);
    }

    public void destroy() {
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
    }
}

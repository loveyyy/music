package com.example.music.ui.frament;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.music.R;
import com.example.music.databinding.FramentRecBinding;
import com.example.music.model.Banner;
import com.example.music.ui.adapter.Vp_BannerAdapter;
import com.example.music.ui.base.BaseFragment;
import com.example.music.utils.imageutils.ScaleTransformer;
import com.example.music.viewmodel.FragmentRecVM;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Create By morningsun  on 2019-12-05
 */
public class FragmentRec extends BaseFragment<FramentRecBinding,FragmentRecVM> {
    private FragmentRecVM fragmentRecVM;
    private FramentRecBinding framentRecBinding;

    private Timer timer;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            framentRecBinding.banner.setCurrentItem(framentRecBinding.banner.getCurrentItem() + 1, true);
        }
    };

    @Override
    protected int getContentViewId() {
        return R.layout.frament_rec;
    }

    @Override
    protected void initView(FramentRecBinding bindView) {
        framentRecBinding=bindView;
    }

    @Override
    protected void SetVM(FragmentRecVM vm) {
        fragmentRecVM=vm;

        framentRecBinding.setFragmentRecVM(fragmentRecVM);
        fragmentRecVM.bannerList.observe(this, new Observer<List<Banner>>() {
            @Override
            public void onChanged(List<Banner> responseBody) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) framentRecBinding.banner.getLayoutParams();
                params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dp_15) * 2;
                params.rightMargin = params.leftMargin;
                CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                compositePageTransformer.addTransformer(new ScaleTransformer());
                compositePageTransformer.addTransformer(new MarginPageTransformer(getResources().getDimensionPixelSize(R.dimen.dp_10)));
                framentRecBinding.banner.setPageTransformer(compositePageTransformer);

                framentRecBinding.banner.setOffscreenPageLimit(2);
                Vp_BannerAdapter vp_bannerAdapter = new Vp_BannerAdapter(getMContext(), responseBody);
                framentRecBinding.banner.setAdapter(vp_bannerAdapter);
                start();

            }
        });

        fragmentRecVM.intentMutableLiveData.observe(this, new Observer<Intent>() {
            @Override
            public void onChanged(Intent intent) {
                startActivity(intent);
            }
        });
    }


    public void start() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(10);
            }
        }, 0, 5000);
    }

    @Override
    protected void setOnClick() {

    }

    @Override
    protected void initData() {
        fragmentRecVM.getRec("1236933931","11","1","10");
    }

    @Override
    protected boolean getNeedRefresh() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}

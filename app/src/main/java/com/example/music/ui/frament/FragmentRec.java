package com.example.music.ui.frament;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.blankj.utilcode.util.StringUtils;
import com.example.music.R;
import com.example.music.model.Banner;
import com.example.music.model.MusicList;
import com.example.music.ui.activity.BangMenuActivity;
import com.example.music.ui.activity.RecListInfoActivity;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Bang_MeauAdapter;
import com.example.music.ui.adapter.GridViewAdapter;
import com.example.music.ui.adapter.Music_ListAdapter;
import com.example.music.ui.adapter.Vp_BannerAdapter;
import com.example.music.ui.base.BaseFragment;
import com.example.music.utils.imageutils.ScaleTransformer;
import com.example.music.viewmodel.FragmentRecVM;
import com.example.music.databinding.FramentRecBinding;
import com.example.music.model.ArtistList;
import com.example.music.model.BangList;

import java.util.ArrayList;
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


        fragmentRecVM.musicList.observe(this, new Observer<MusicList>() {
            @Override
            public void onChanged(final MusicList listBaseRespon) {
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
                framentRecBinding.rcvMusicListMain.setLayoutManager(layoutManager);
                Music_ListAdapter music_listAdapter=new Music_ListAdapter(getContext(),listBaseRespon);
                music_listAdapter.setOnItemClick(new Music_ListAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        //推荐歌单点击事件
                        Intent intent=new Intent();
                        intent.setClass(getContext(), RecListInfoActivity.class);
                        intent.putExtra("rid",listBaseRespon.getList().get(pos).getId().toString());
                        startActivity(intent);
                    }
                });
                framentRecBinding.rcvMusicListMain.setAdapter(music_listAdapter);
            }
        });
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
        fragmentRecVM.bangList.observe(this, new Observer<List<BangList>>() {
            @Override
            public void onChanged(final List<BangList> listBaseRespon) {
                framentRecBinding.rcvRankMain.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                Bang_MeauAdapter bang_meauAdapter=new Bang_MeauAdapter(getContext(),listBaseRespon);
                framentRecBinding.rcvRankMain.setAdapter(bang_meauAdapter);
                bang_meauAdapter.setOnItemClick(new Bang_MeauAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        //排行歌曲点击事件--进入排行详情
                        Intent intent=new Intent();
                        intent.putExtra("title",listBaseRespon.get(pos).getName());
                        intent.putExtra("time",listBaseRespon.get(pos).getPub());
                        intent.putExtra("bangid",listBaseRespon.get(pos).getId());
                        intent.putExtra("img",listBaseRespon.get(pos).getPic());
                        intent.setClass(getContext(), BangMenuActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
        fragmentRecVM.artistList.observe(this, new Observer<ArtistList>() {
            @Override
            public void onChanged(final ArtistList artist_listBaseRespon) {
                framentRecBinding.rcvSingerMain.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                GridViewAdapter gridViewAdapter=new GridViewAdapter(getContext(),artist_listBaseRespon);
                framentRecBinding.rcvSingerMain.setAdapter(gridViewAdapter);
                gridViewAdapter.setOnItemClick(new GridViewAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        //歌手Item点击事件
                        Intent intent=new Intent();
                        intent.putExtra("artistid",artist_listBaseRespon.getArtistList().get(pos).getId());
                        intent.setClass(getContext(), Singer_Activity.class);
                        startActivity(intent);
                    }
                });

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

}

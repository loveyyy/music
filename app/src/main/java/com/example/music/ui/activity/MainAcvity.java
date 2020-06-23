package com.example.music.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.DrawleftaptBinding;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.ui.adapter.BaseAdapter;
import com.example.music.ui.adapter.VpMainAdapter;
import com.example.music.ui.custom.CustomDialogFragment;
import com.example.music.ui.custom.PlayerView;
import com.example.music.ui.frament.Framen_Rank;
import com.example.music.ui.frament.Frament_Music;
import com.example.music.ui.frament.Frament_Rec;
import com.example.music.ui.frament.Frament_Singer;
import com.example.music.ui.frament.Framnet_MV;
import com.example.music.databinding.MainactivityBinding;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2019-11-29
 */
public class MainAcvity extends FragmentActivity implements PlayerView.showList {
    private MainactivityBinding mainactivityBinding;
    private List<Fragment> fragments = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainactivityBinding= DataBindingUtil.setContentView(this,R.layout.mainactivity);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0, null);
        initdata();
    }

    private void initdata() {
        mainactivityBinding.main.playview.SetShowList(this);
        fragments.add(new Frament_Rec());
        fragments.add(new Framen_Rank());
        fragments.add(new Frament_Singer());
        fragments.add(new Frament_Music());
        fragments.add(new Framnet_MV());
        VpMainAdapter vpMainAdapter = new VpMainAdapter(getSupportFragmentManager(), fragments, getApplicationContext());
        mainactivityBinding.main.VpMain.setAdapter(vpMainAdapter);
        mainactivityBinding.main.VpMain.setOffscreenPageLimit(4);
        mainactivityBinding.main.tabMain.setupWithViewPager(mainactivityBinding.main.VpMain);

        mainactivityBinding.main.VpMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        StatusBarUtil.setLightMode(MainAcvity.this);
                        break;
                    default:
                        StatusBarUtil.setDarkMode(MainAcvity.this);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        for (int j = 0; j < fragments.size(); j++) {
            TabLayout.Tab tab = mainactivityBinding.main.tabMain.getTabAt(j);
            if (tab != null) {
                tab.setCustomView(vpMainAdapter.getCustomView(j));
            }
        }
        mainactivityBinding.main.VpMain.setCurrentItem(0);

        mainactivityBinding.main.tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTypeface(Typeface.DEFAULT_BOLD);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTypeface(Typeface.DEFAULT);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        StatusBarUtil.setLightMode(MainAcvity.this);

        mainactivityBinding.main.ivMaintou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> strings=new ArrayList<>();
                strings.add("本地音乐");
                strings.add("我的下载");
                strings.add("我的收藏");
                mainactivityBinding.left.rcvAccount.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
                BaseAdapter<String> bindingBaseAdapter=new BaseAdapter<>(getApplication(),strings,R.layout.drawleftapt, BR.title);
                mainactivityBinding.left.rcvAccount.setAdapter(bindingBaseAdapter);

                bindingBaseAdapter.setOnItemClick(new BaseAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {

                    }
                });
                mainactivityBinding.dlMain.openDrawer(Gravity.LEFT);
            }
        });

        mainactivityBinding.main.rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mainactivityBinding.main.playview.refresh();
    }

    @Override
    public void OnShowList(List<PlayingMusicBeens> playingMusicBeens) {
        CustomDialogFragment customDialogFragment=new CustomDialogFragment(playingMusicBeens,getApplicationContext());
        customDialogFragment.show(getSupportFragmentManager(),"MainActivity");
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}

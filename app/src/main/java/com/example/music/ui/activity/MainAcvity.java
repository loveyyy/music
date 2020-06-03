package com.example.music.ui.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.music.R;
import com.example.music.model.PlayingMusicBeens;
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
        mainactivityBinding.playview.SetShowList(this);
        fragments.add(new Frament_Rec());
        fragments.add(new Framen_Rank());
        fragments.add(new Frament_Singer());
        fragments.add(new Frament_Music());
        fragments.add(new Framnet_MV());
        VpMainAdapter vpMainAdapter = new VpMainAdapter(getSupportFragmentManager(), fragments, getApplicationContext());
        mainactivityBinding.VpMain.setAdapter(vpMainAdapter);
        mainactivityBinding.VpMain.setOffscreenPageLimit(4);
        mainactivityBinding.tabMain.setupWithViewPager(mainactivityBinding.VpMain);

        mainactivityBinding.VpMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
            TabLayout.Tab tab = mainactivityBinding.tabMain.getTabAt(j);
            if (tab != null) {
                tab.setCustomView(vpMainAdapter.getCustomView(j));
            }
        }
        mainactivityBinding.VpMain.setCurrentItem(0);

        mainactivityBinding.tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mainactivityBinding.playview.refresh();
    }

    @Override
    public void OnShowList(List<PlayingMusicBeens> playingMusicBeens) {
        CustomDialogFragment customDialogFragment=new CustomDialogFragment(playingMusicBeens,getApplicationContext());
        customDialogFragment.show(getSupportFragmentManager(),"MainActivity");
    }
}

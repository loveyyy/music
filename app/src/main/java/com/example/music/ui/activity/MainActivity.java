package com.example.music.ui.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.music.R;
import com.example.music.databinding.VpTableListBinding;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.ui.base.BaseActivity;
import com.example.music.ui.base.BaseVM;
import com.example.music.ui.custom.CustomDialogFragment;
import com.example.music.ui.custom.PlayerMusicView;
import com.example.music.ui.frament.FragmentRank;
import com.example.music.ui.frament.FragmentMusic;
import com.example.music.ui.frament.FragmentRec;
import com.example.music.ui.frament.FragmentSinger;
import com.example.music.ui.frament.FragmentMV;
import com.example.music.databinding.MainactivityBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2019-11-29
 */
public class MainActivity extends BaseActivity<MainactivityBinding, BaseVM> implements PlayerMusicView.showList {
    private MainactivityBinding mainactivityBinding;

    @Override
    public int getLayout() {
        return R.layout.mainactivity;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(MainactivityBinding bindView) {
        mainactivityBinding=bindView;
        mainactivityBinding.main.playview.SetShowList(this);
    }

    @Override
    protected void setVM(BaseVM vm) {

    }

    @Override
    protected void setListener() {
        mainactivityBinding.main.rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        mainactivityBinding.main.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

        mainactivityBinding.main.ivMaintou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, DownloadCenterActivity.class);
                startActivity(intent);
            }
        });


        mainactivityBinding.main.tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView view = tab.getCustomView().findViewById(R.id.tv_table);
                view.setTypeface(Typeface.DEFAULT_BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView view = tab.getCustomView().findViewById(R.id.tv_table);
                view.setTypeface(Typeface.DEFAULT);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentRec());
        fragments.add(new FragmentRank());
        fragments.add(new FragmentSinger());
        fragments.add(new FragmentMusic());
        fragments.add(new FragmentMV());

        mainactivityBinding.main.VpMain.setAdapter(new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return fragments.size();
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }
        });
        mainactivityBinding.main.VpMain.setOffscreenPageLimit(fragments.size());

        final String[] name = {"推荐","排行榜","歌手","歌单","MV"};
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mainactivityBinding.main.tabMain, mainactivityBinding.main.VpMain, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(name[position]);
                VpTableListBinding vpTableListBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.vp_table_list, null, false);
                vpTableListBinding.tvTable.setText(name[position]);
                vpTableListBinding.executePendingBindings();
                tab.setCustomView(vpTableListBinding.getRoot());
            }
        });
        tabLayoutMediator.attach();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainactivityBinding.main.playview.init();
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

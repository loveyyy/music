package com.example.music.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.music.R;
import com.example.music.databinding.SingerActivityBinding;
import com.example.music.model.Arisit_Info;
import com.example.music.model.BaseRespon;
import com.example.music.ui.adapter.Vp_Artist_apt;
import com.example.music.ui.frament.Frament_Artist_Mv;
import com.example.music.ui.frament.Frament_Artist_albums;
import com.example.music.ui.frament.Frament_artist_music;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;
import com.example.music.viewmodel.Singer_VM;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Singer_Activity extends FragmentActivity {
    private SingerActivityBinding singerActivityBinding;
    private List<Fragment> fragments = new ArrayList();
    private Singer_VM singer_vm;
    private int artistid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singerActivityBinding= DataBindingUtil.setContentView(this, R.layout.singer_activity);
        singer_vm= ViewModelProviders.of(this).get(Singer_VM.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(this,0, null);
        SetVm();
        initdata();
    }

    private void SetVm() {
        singer_vm.Artist_info.observe(this, new Observer<BaseRespon<Arisit_Info>>() {
            @Override
            public void onChanged(BaseRespon<Arisit_Info> arisit_infoBaseRespon) {
                RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
                Glide.with(getBaseContext()).load(arisit_infoBaseRespon.getData().getPic())
                        .apply(requestOptions).into(singerActivityBinding.ivArtist);
                singerActivityBinding.ivArtistName.setText(arisit_infoBaseRespon.getData().getName());
                singerActivityBinding.ivArtistInfo.setText("单曲："+arisit_infoBaseRespon.getData().getMusicNum()+
                        "       专辑："+arisit_infoBaseRespon.getData().getAlbumNum()+
                        "       MV："+arisit_infoBaseRespon.getData().getMvNum()+
                        "       粉丝："+arisit_infoBaseRespon.getData().getArtistFans());
                singerActivityBinding.ivArtistInfo1.setText("国籍："+arisit_infoBaseRespon.getData().getCountry()+
                        "       语言："+arisit_infoBaseRespon.getData().getLanguage()+
                        "       出生地："+arisit_infoBaseRespon.getData().getBirthplace());
            }
        });
    }

    private void initdata() {
        Intent intent=getIntent();
        artistid=intent.getIntExtra("artistid",0);
        singer_vm.Get_Artist_info(getBaseContext(),String.valueOf(artistid),"db2f93e0-189a-11ea-b707-4b7b2a81b695");

        fragments.add(new Frament_artist_music());
        fragments.add(new Frament_Artist_albums());
        fragments.add(new Frament_Artist_Mv());
        Vp_Artist_apt vp_artist_apt = new Vp_Artist_apt(getSupportFragmentManager(), fragments, getApplicationContext());
        singerActivityBinding.vpArtist.setAdapter(vp_artist_apt);
        singerActivityBinding.vpArtist.setOffscreenPageLimit(4);
        singerActivityBinding.tabArtist.setupWithViewPager(singerActivityBinding.vpArtist);

        singerActivityBinding.vpArtist.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        StatusBarUtil.setLightMode(Singer_Activity.this);
                        break;
                    default:
                        StatusBarUtil.setDarkMode(Singer_Activity.this);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        for (int j = 0; j < fragments.size(); j++) {
            TabLayout.Tab tab = singerActivityBinding.tabArtist.getTabAt(j);
            if (tab != null) {
                tab.setCustomView(vp_artist_apt.getCustomView(j));
            }
        }
        singerActivityBinding.vpArtist.setCurrentItem(0);

        singerActivityBinding.tabArtist.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        StatusBarUtil.setLightMode(Singer_Activity.this);
    }

    public int getartistid(){
        return artistid;
    }


}

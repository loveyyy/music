package com.example.music.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.music.R;
import com.example.music.databinding.SingerActivityBinding;
import com.example.music.model.ArtistInfo;
import com.example.music.model.BaseRespon;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.ui.adapter.Vp_Artist_apt;
import com.example.music.ui.base.BaseActivity;
import com.example.music.ui.custom.CustomDialogFragment;
import com.example.music.ui.custom.PlayerMusicView;
import com.example.music.ui.frament.FragmentArtistAlbums;
import com.example.music.ui.frament.FragmentArtistMusic;
import com.example.music.ui.frament.FragmentArtistMv;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;
import com.example.music.viewmodel.SingerVM;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Singer_Activity extends BaseActivity<SingerActivityBinding, SingerVM> implements  PlayerMusicView.showList {
    private SingerActivityBinding singerActivityBinding;
    private List<Fragment> fragments = new ArrayList();
    private SingerVM singer_vm;
    private int artistid;


    @Override
    public int getLayout() {
        return R.layout.singer_activity;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(SingerActivityBinding bindView) {
        singerActivityBinding=bindView;
        singerActivityBinding.playview.SetShowList(this);
    }

    @Override
    protected void setVM(SingerVM vm) {
        singer_vm=vm;
        singer_vm.artistInfo.observe(this, new Observer<BaseRespon<ArtistInfo>>() {
            @Override
            public void onChanged(BaseRespon<ArtistInfo> arisit_infoBaseRespon) {
                try {
                    RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
                    Glide.with(getContext()).load(arisit_infoBaseRespon.getData().getPic())
                            .apply(requestOptions).into(singerActivityBinding.ivArtist);
                    singerActivityBinding.ivArtistName.setText(arisit_infoBaseRespon.getData().getName());
                    singerActivityBinding.ivArtistInfo.setText("单曲："+arisit_infoBaseRespon.getData().getMusicNum()+
                            "       专辑："+arisit_infoBaseRespon.getData().getAlbumNum()+
                            "       MV："+arisit_infoBaseRespon.getData().getMvNum()+
                            "       粉丝："+arisit_infoBaseRespon.getData().getArtistFans());
                    singerActivityBinding.ivArtistInfo1.setText("国籍："+arisit_infoBaseRespon.getData().getCountry()+
                            "       语言："+arisit_infoBaseRespon.getData().getLanguage()+
                            "       出生地："+arisit_infoBaseRespon.getData().getBirthplace());


                }catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"请返回重试",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void setListener() {
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
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        artistid=intent.getIntExtra("artistid",0);
        singer_vm.getArtistInfo(String.valueOf(artistid));

        fragments.add(new FragmentArtistMusic());
        fragments.add(new FragmentArtistAlbums());
        fragments.add(new FragmentArtistMv());
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
    }



    public int getartistid(){
        return artistid;
    }



    @Override
    public void OnShowList(List<PlayingMusicBeens> playingMusicBeens) {
        CustomDialogFragment customDialogFragment=new CustomDialogFragment(playingMusicBeens,getApplicationContext());
        customDialogFragment.show(getSupportFragmentManager(),"SingerActivity");
    }
}

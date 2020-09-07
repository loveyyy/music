package com.example.music.ui.frament;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.Observer;

import com.example.music.R;
import com.example.music.databinding.FramentArtistMvBinding;
import com.example.music.model.ArtistMv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.MvPlayActivity;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.FragmentArtistMvApt;
import com.example.music.ui.base.BaseFragment;
import com.example.music.viewmodel.SingerVM;

/**
 * Create By morningsun  on 2019-12-07
 */
public class FragmentArtistMv extends BaseFragment<FramentArtistMvBinding,SingerVM> {
    private FramentArtistMvBinding framentArtistMvBinding;
    private SingerVM singerVM;
    private int artistId;

    @Override
    protected int getContentViewId() {
        return R.layout.frament_artist_mv;
    }

    @Override
    protected void initView(FramentArtistMvBinding bindView) {
        framentArtistMvBinding=bindView;
    }

    @Override
    protected void SetVM(SingerVM vm) {
        singerVM = vm;
        framentArtistMvBinding.setSingerVM(singerVM);
    }

    @Override
    protected void setOnClick() {

    }

    @Override
    protected void initData() {
        singerVM.getArtistMv(String.valueOf(artistId),"1","30");
    }

    @Override
    protected boolean getNeedRefresh() {
        return false;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        artistId = ((Singer_Activity) context).getartistid();
    }
}

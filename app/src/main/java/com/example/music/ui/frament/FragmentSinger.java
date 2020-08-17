package com.example.music.ui.frament;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.music.R;
import com.example.music.databinding.FramentSingerBinding;
import com.example.music.model.ArtistList;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Gv_Singer_apt;
import com.example.music.ui.base.BaseFragment;
import com.example.music.viewmodel.SingerVM;
import com.jaeger.library.StatusBarUtil;

/**
 * Create By morningsun  on 2019-12-05
 */
public class FragmentSinger extends BaseFragment<FramentSingerBinding,SingerVM> {
    private FramentSingerBinding framentSingerBinding;
    private SingerVM singerVM;

    @Override
    protected int getContentViewId() {
        return R.layout.frament_singer;
    }

    @Override
    protected void initView(FramentSingerBinding bindView) {
        framentSingerBinding=bindView;
    }

    @Override
    protected void SetVM(SingerVM vm) {
        singerVM=vm;

        singerVM.artistList.observe(this, new Observer<BaseRespon<ArtistList>>() {
            @Override
            public void onChanged(final BaseRespon<ArtistList> artist_listBaseRespon) {
                Gv_Singer_apt gv_singer_apt=new Gv_Singer_apt(getContext(),artist_listBaseRespon.getData().getArtistList());
                framentSingerBinding.gvSinger.setAdapter(gv_singer_apt);
                framentSingerBinding.gvSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent();
                        intent.putExtra("artistid",artist_listBaseRespon.getData().getArtistList().get(i).getId());
                        intent.setClass(getContext(), Singer_Activity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void setOnClick() {

    }

    @Override
    protected void initData() {
        singerVM.getArtistList("0","1","100");
    }

    @Override
    protected boolean getNeedRefresh() {
        return false;
    }

}

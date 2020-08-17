package com.example.music.ui.frament;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.music.R;
import com.example.music.databinding.FramentArtistMvBinding;
import com.example.music.model.ArtistMv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.MvPlayActivity;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Gv_artist_Mv_apt;
import com.example.music.ui.base.BaseFragment;
import com.example.music.viewmodel.SingerVM;
import com.jaeger.library.StatusBarUtil;

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

        singerVM.artistMv.observe(this, new Observer<BaseRespon<ArtistMv>>() {
            @Override
            public void onChanged(final BaseRespon<ArtistMv> artist_listBaseRespon) {
                Gv_artist_Mv_apt gv_artist_albums_apt=new Gv_artist_Mv_apt(getContext(),artist_listBaseRespon.getData().getMvlist());
                framentArtistMvBinding.gvArtistMv.setAdapter(gv_artist_albums_apt);
                framentArtistMvBinding.gvArtistMv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //播放mv
                        Intent intent=new Intent();
                        intent.setClass(getActivity(), MvPlayActivity.class);
                        intent.putExtra("rid",artist_listBaseRespon.getData().getMvlist().get(position).getId());
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

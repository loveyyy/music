package com.example.music.ui.frament;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.music.R;
import com.example.music.databinding.FramentArtistAlbumsBinding;
import com.example.music.model.ArtistAlbum;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Gv_artist_albums_apt;
import com.example.music.ui.base.BaseFragment;
import com.example.music.viewmodel.SingerVM;
import com.jaeger.library.StatusBarUtil;

/**
 * Create By morningsun  on 2019-12-07
 */
public class FragmentArtistAlbums extends BaseFragment<FramentArtistAlbumsBinding, SingerVM> {
    private FramentArtistAlbumsBinding framentArtistAlbumsBinding;
    private SingerVM singerVM;
    private int artistId;

    @Override
    protected int getContentViewId() {
        return R.layout.frament_artist_albums;
    }

    @Override
    protected void initView(FramentArtistAlbumsBinding bindView) {
        framentArtistAlbumsBinding = bindView;
    }

    @Override
    protected void SetVM(SingerVM vm) {
        singerVM = vm;
        singerVM.artistAlbum.observe(this, new Observer<BaseRespon<ArtistAlbum>>() {
            @Override
            public void onChanged(BaseRespon<ArtistAlbum> artist_listBaseRespon) {
                Gv_artist_albums_apt gv_artist_albums_apt = new Gv_artist_albums_apt(getContext(), artist_listBaseRespon.getData().getAlbumList());
                framentArtistAlbumsBinding.gvArtistAlbums.setAdapter(gv_artist_albums_apt);
            }
        });
    }

    @Override
    protected void setOnClick() {

    }

    @Override
    protected void initData() {
        singerVM.getArtistAlbum(String.valueOf(artistId), "1", "30");
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

package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;

import com.example.music.R;
import com.example.music.databinding.GvArtistAlbumsAptBinding;
import com.example.music.model.Artist_Album;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Gv_artist_albums_apt extends BaseAdapter {
    private Context context;
    private List<Artist_Album.AlbumListBean> artist_albums;
    public Gv_artist_albums_apt(Context context, List<Artist_Album.AlbumListBean> artist_albums){
        this.artist_albums=artist_albums;
        this.context=context;
    }
    @Override
    public int getCount() {
        return artist_albums.size();
    }

    @Override
    public Object getItem(int i) {
        return artist_albums.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GvArtistAlbumsAptBinding gvArtistAlbumsAptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.gv_artist_albums_apt,viewGroup,false);
        gvArtistAlbumsAptBinding.setVariable(BR.artistalbums, artist_albums.get(i));
        return gvArtistAlbumsAptBinding.getRoot();
    }
}

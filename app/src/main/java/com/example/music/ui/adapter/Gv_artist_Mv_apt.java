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
import com.example.music.databinding.GvArtistMvAptBinding;
import com.example.music.model.Artist_Album;
import com.example.music.model.Artist_Mv;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Gv_artist_Mv_apt extends BaseAdapter {
    private Context context;
    private List<Artist_Mv.MvlistBean> mvlistBeans;
    public Gv_artist_Mv_apt(Context context, List<Artist_Mv.MvlistBean> mvlistBeans){
        this.mvlistBeans=mvlistBeans;
        this.context=context;
    }
    @Override
    public int getCount() {
        return mvlistBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return mvlistBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GvArtistMvAptBinding artistMvAptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.gv_artist_mv_apt,viewGroup,false);
        artistMvAptBinding.setVariable(BR.artistmv, mvlistBeans.get(i));
        return artistMvAptBinding.getRoot();
    }
}

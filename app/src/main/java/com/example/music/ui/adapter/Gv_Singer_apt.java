package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;

import com.example.music.R;
import com.example.music.databinding.GrideviewItemBinding;
import com.example.music.model.ArtistList;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Gv_Singer_apt extends BaseAdapter {
    private Context context;
    private  List<ArtistList.ArtistListBean> artist_lists;
    public Gv_Singer_apt(Context context, List<ArtistList.ArtistListBean> artist_lists){
        this.context=context;
        this.artist_lists=artist_lists;
    }
    @Override
    public int getCount() {
        return artist_lists.size();
    }

    @Override
    public Object getItem(int i) {
        return artist_lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GrideviewItemBinding grideviewItemBinding= DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.grideview_item,viewGroup,false);
        grideviewItemBinding.setVariable(BR.singer, artist_lists.get(i));
        return grideviewItemBinding.getRoot();
    }
}

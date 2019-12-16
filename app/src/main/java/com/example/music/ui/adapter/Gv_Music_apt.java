package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.GvMusicAptBinding;
import com.example.music.model.Rec_List;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-10
 */
public class Gv_Music_apt extends BaseAdapter {
    private List<Rec_List.DataBean> listBeans;
    private Context context;
    public Gv_Music_apt(Context context,List<Rec_List.DataBean> listBeans){
        this.context=context;
        this.listBeans=listBeans;
    }
    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return listBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GvMusicAptBinding gvMusicAptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.gv_music_apt,viewGroup,false);
        gvMusicAptBinding.setVariable(BR.rec_list,listBeans.get(i));
        return gvMusicAptBinding.getRoot();
    }
}

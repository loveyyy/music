package com.example.music.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.PlayViewAptBinding;
import com.example.music.entry.PlayingMusicBeens;

import java.util.ArrayList;

/**
 * Create By morningsun  on 2019-12-06
 */
public class VP_Paly_Apt extends PagerAdapter {
    private ArrayList<PlayingMusicBeens> playingMusicBeens;
    private Context context;

    public VP_Paly_Apt(Context context, ArrayList<PlayingMusicBeens> playingMusicBeens ) {
        this.playingMusicBeens = playingMusicBeens;
        this.context = context;
    }


    public int getCount() {
        return playingMusicBeens.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PlayViewAptBinding playViewAptBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.play_view_apt, container, true);
        playViewAptBinding.setVariable(BR.playitem,playingMusicBeens.get(position));
        return playViewAptBinding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.LocalmusicaptBinding;
import com.example.music.model.LocalMusic;
import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.utils.LocalMusicUtils;

import java.util.List;

/**
 * Create By morningsun  on 2020-06-19
 */
public class LocalMusicApt extends RecyclerView.Adapter<BindingViewHolder> {
    private LocalmusicaptBinding localmusicaptBinding;
    private Context context;
    private List<LocalMusic> localMusics;
    public LocalMusicApt(Context context, List<LocalMusic> localMusics) {
        this.context=context;
        this.localMusics=localMusics;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        localmusicaptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.localmusicapt,parent,false);
        return new BindingViewHolder(localmusicaptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.localmusic,localMusics.get(position));
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return localMusics.size();
    }
}

package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.MusicItemAptBinding;
import com.example.music.databinding.PopPlayAptBinding;
import com.example.music.model.Bang_Music_list;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.ui.bindadapter.BindingViewHolder;

import java.util.List;

public class Pop_PlayAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<PlayingMusicBeens> playingMusicBeens;
    public Pop_PlayAdapter(Context context, List<PlayingMusicBeens> playingMusicBeens){
        this.context=context;
        this.playingMusicBeens=playingMusicBeens;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PopPlayAptBinding popPlayAptBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pop_play_apt, parent, false);
        return  new BindingViewHolder(popPlayAptBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        TextView textView= binding.getRoot().findViewById(R.id.pop_name);
        textView.setText(playingMusicBeens.get(position).getMusicname()+"--"+playingMusicBeens.get(position).getMusic_singer());
        binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return playingMusicBeens.size();
    }

}

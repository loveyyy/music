package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.PlayViewAptBinding;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.utils.PlayController;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2019-12-06
 */
public class VP_Paly_Apt extends RecyclerView.Adapter<BindingViewHolder<PlayViewAptBinding>> {
    private List<PlayingMusicBeens> playingMusicBeens;
    private Context context;
    private onItemClick onItemClick;

    public VP_Paly_Apt(Context context, List<PlayingMusicBeens> playingMusicBeens ) {
        this.playingMusicBeens = playingMusicBeens;
        this.context = context;
    }


    @NonNull
    @Override
    public BindingViewHolder<PlayViewAptBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PlayViewAptBinding playViewAptBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.play_view_apt, parent, false);
        return new BindingViewHolder(playViewAptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<PlayViewAptBinding> holder, int position) {
        holder.getBinding().setVariable(BR.playitem,playingMusicBeens.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClick!=null){
                    onItemClick.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playingMusicBeens.size();
    }

    public interface  onItemClick{
        void onItemClick(int pos);
    }

    public void  setOnItemClick(onItemClick onItemClick){
        this.onItemClick =onItemClick;
    }


    public void notif(){
        if(!playingMusicBeens.containsAll(PlayController.getInstance().getPlayList())){
            playingMusicBeens= PlayController.getInstance().getPlayList();
            notifyDataSetChanged();
        }
    }
}

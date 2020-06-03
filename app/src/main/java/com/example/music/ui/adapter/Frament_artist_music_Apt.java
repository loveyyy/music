package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.FramentArtistMusicAptBinding;
import com.example.music.model.Artist_Music;
import com.example.music.model.Artist_list;
import com.example.music.ui.bindadapter.BindingViewHolder;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Frament_artist_music_Apt extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<Artist_Music.ListBean> artistListBeans;
    private Frament_artist_music_Apt.OnItemClick onItemClick;
    public Frament_artist_music_Apt(Context context, List<Artist_Music.ListBean> artistListBeans){
        this.context=context;
        this.artistListBeans=artistListBeans;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FramentArtistMusicAptBinding framentArtistMusicAptBinding= DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.frament_artist_music_apt,parent,false);
        return new BindingViewHolder(framentArtistMusicAptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding dataBinding=holder.getBinding();
        dataBinding.setVariable(BR.artistmusic,artistListBeans.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClick!=null){
                    onItemClick.OnItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistListBeans.size();
    }

    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(Frament_artist_music_Apt.OnItemClick click){
        this.onItemClick=click;
    }
}

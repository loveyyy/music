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
import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.databinding.MusicItemAptBinding;
import com.example.music.model.Bang_Music_list;

import java.util.List;

public class Music_ItemAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<Bang_Music_list.MusicListBean> musicitem;
    private Music_ItemAdapter.OnItemClick onItemClick;
    public Music_ItemAdapter(Context context,List<Bang_Music_list.MusicListBean> musicitem){
        this.context=context;
        this.musicitem=musicitem;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MusicItemAptBinding musicItemaptBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.music_item_apt, parent, false);
        return  new BindingViewHolder(musicItemaptBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.bangMusicList,musicitem.get(position));
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
        return musicitem.size();
    }

    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(Music_ItemAdapter.OnItemClick click){
        this.onItemClick=click;
    }
}

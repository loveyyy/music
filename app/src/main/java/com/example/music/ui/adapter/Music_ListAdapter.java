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
import com.example.music.model.MusicList;

/**
 * Create By morningsun  on 2019-11-29
 */
public class Music_ListAdapter extends  RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private MusicList listBean;
    private Music_ListAdapter.OnItemClick onItemClick;
    public Music_ListAdapter(Context context, MusicList listBean){
        this.context=context;
        this.listBean=listBean;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.musci_list_adapter, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.music_list,listBean.getList().get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.OnItemClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBean.getList().size();
    }
    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(Music_ListAdapter.OnItemClick click){
        this.onItemClick=click;
    }
}
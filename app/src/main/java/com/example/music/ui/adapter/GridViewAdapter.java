package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.R;
import com.example.music.model.Artist_list;

public class GridViewAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private Artist_list artistListBean;
    private GridViewAdapter.OnItemClick onItemClick;
    public GridViewAdapter(Context context, Artist_list artistListBean){
        this.context=context;
        this.artistListBean=artistListBean;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.grideview_item, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.singer, artistListBean.getArtistList().get(position));
        binding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.OnItemClickListener(position);
                }
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return artistListBean.getArtistList().size();
    }



    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(GridViewAdapter.OnItemClick click){
        this.onItemClick=click;
    }
}

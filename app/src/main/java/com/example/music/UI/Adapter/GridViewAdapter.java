package com.example.music.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.entry.SingerBeens;
import com.example.music.R;
import com.example.music.Utils.GildeCilcleImageUtils;

public class GridViewAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private SingerBeens.DataBean singerBeens;
    private GridViewAdapter.OnItemClick onItemClick;
    public GridViewAdapter(Context context, SingerBeens.DataBean singerBeens){
        this.context=context;
        this.singerBeens=singerBeens;
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
        binding.setVariable(BR.singer, singerBeens.getArtistList().get(position));
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
        return singerBeens.getArtistList().size();
    }



    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(GridViewAdapter.OnItemClick click){
        this.onItemClick=click;
    }
}

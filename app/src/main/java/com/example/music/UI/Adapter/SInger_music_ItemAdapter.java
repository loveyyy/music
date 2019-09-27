package com.example.music.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.BR;
import com.example.music.databinding.RankitemadapterBinding;
import com.example.music.entry.Singer_MusicBeens;
import com.example.music.R;

public class SInger_music_ItemAdapter extends RecyclerView.Adapter<SInger_music_ItemAdapter.ViewHolder> {
    private Context context;
    private Singer_MusicBeens.DataBean dataBean;
    private RankitemadapterBinding rankItemadapterBinding;
    private RlRankAdapter.OnItemClick onItemClick;
    public SInger_music_ItemAdapter(Context context, Singer_MusicBeens.DataBean dataBean){
        this.context=context;
        this.dataBean=dataBean;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rankitemadapter,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //trend 判断上升还是下降  u0上升   d0下降  e0不变
        rankItemadapterBinding.setVariable(BR.item,dataBean.getList().get(position));
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
        return dataBean.getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rankItemadapterBinding= DataBindingUtil.bind(itemView);
        }
    }
    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(RlRankAdapter.OnItemClick click){
        this.onItemClick=click;
    }
}

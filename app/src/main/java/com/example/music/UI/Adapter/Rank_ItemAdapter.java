package com.example.music.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.Beens.RankMusicBeens;
import com.example.music.R;

public class Rank_ItemAdapter extends RecyclerView.Adapter<Rank_ItemAdapter.ViewHolder> {
    private  Context context;
    private RankMusicBeens.DataBean dataBean;
    private RlRankAdapter.OnItemClick onItemClick;
    public Rank_ItemAdapter(Context context, RankMusicBeens.DataBean dataBean){
        this.context=context;
        this.dataBean=dataBean;
    }
    @NonNull
    @Override
    public Rank_ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.rank_itemadapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Rank_ItemAdapter.ViewHolder holder, final int position) {
        //trend 判断上升还是下降  u0上升   d0下降  e0不变
        Glide.with(context).load(dataBean.getMusicList().get(position).getAlbumpic()).into(holder.fengmian);
        holder.name.setText(dataBean.getMusicList().get(position).getName());
        holder.singer.setText(dataBean.getMusicList().get(position).getArtist());
        holder.zhuanji.setText(dataBean.getMusicList().get(position).getAlbum());
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
        return dataBean.getMusicList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id,updown,name,singer,zhuanji,shichang;
        private ImageView fengmian;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fengmian=itemView.findViewById(R.id.fengmian);
            name=itemView.findViewById(R.id.name);
            singer=itemView.findViewById(R.id.singer);
            zhuanji=itemView.findViewById(R.id.zhuanji);
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

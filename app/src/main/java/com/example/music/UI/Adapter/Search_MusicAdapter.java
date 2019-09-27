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
import com.example.music.entry.SearchBeens;
import com.example.music.R;

public class Search_MusicAdapter extends RecyclerView.Adapter<Search_MusicAdapter.ViewHolder> {
    private Context context;
    private SearchBeens.DataBean dataBean;
    private Search_MusicAdapter.OnItemClick onItemClick;
    public Search_MusicAdapter(Context context, SearchBeens.DataBean dataBean){
        this.context=context;
        this.dataBean=dataBean;
    }
    @NonNull
    @Override
    public Search_MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rankitemadapter,parent,false);
        return new Search_MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_MusicAdapter.ViewHolder holder, final int position) {
        //trend 判断上升还是下降  u0上升   d0下降  e0不变
        Glide.with(context).load(dataBean.getList().get(position).getAlbumpic()).into(holder.fengmian);
        holder.name.setText(dataBean.getList().get(position).getName());
        holder.singer.setText(dataBean.getList().get(position).getArtist());
        holder.zhuanji.setText(dataBean.getList().get(position).getAlbum());
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
    public void setOnItemClick(Search_MusicAdapter.OnItemClick click){
        this.onItemClick=click;
    }
}


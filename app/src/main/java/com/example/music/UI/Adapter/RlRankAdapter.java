package com.example.music.UI.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.music.entry.HotMusicBeens;
import com.example.music.R;

/**
 * Created by Administrator on 2018/7/6.
 */

public class RlRankAdapter extends RecyclerView.Adapter<RlRankAdapter.ViewHolder>{
    private HotMusicBeens.DataBean dataBean;
    private Context context;
    private OnItemClick onItemClick;
    public RlRankAdapter(Context context, HotMusicBeens.DataBean netMusicBeens){
        this.context=context;
        this.dataBean=netMusicBeens;
    }
    @Override
    public RlRankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_rank,parent,false);
        ViewHolder viewHolde=new ViewHolder(view);
        return viewHolde;
    }

    @Override
    public void onBindViewHolder(final RlRankAdapter.ViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClick!=null){
                        onItemClick.OnItemClickListener(position);
                    }
                }
            });
            Glide.with(context).load(dataBean.getMusicList().get(position).getPic()).into(holder.iv_album);
            holder.tv_musicname.setText(dataBean.getMusicList().get(position).getName());
            holder.tv_artist.setText(dataBean.getMusicList().get(position).getArtist());
            holder.ib_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(likeOnClickListener!=null){
                        likeOnClickListener.LikeClick(holder.itemView,position);
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        return dataBean.getMusicList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView tv_musicname;
        private TextView tv_artist;
        private ImageButton ib_like;
        private ImageView iv_album;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_artist=itemView.findViewById(R.id.tv_artist);
            tv_musicname=itemView.findViewById(R.id.tv_muicsname);
            ib_like=itemView.findViewById(R.id.ib_like);
            iv_album=itemView.findViewById(R.id.iv_album);
        }
    }
    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(OnItemClick click){
        this.onItemClick=click;
    }

    //Like  的点击接口
    public static LikeOnClick likeOnClickListener;
    public interface  LikeOnClick{
        void LikeClick(View view,int pos);
    }
    public void setLikeOnClickListener(LikeOnClick likeOnClick){
        likeOnClickListener=likeOnClick;
    }
}

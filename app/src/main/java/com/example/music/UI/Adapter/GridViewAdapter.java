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
import com.example.music.entry.SingerBeens;
import com.example.music.R;
import com.example.music.Utils.GildeCilcleImageUtils;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {
    private Context context;
    private SingerBeens.DataBean singerBeens;
    private GridViewAdapter.OnItemClick onItemClick;
    public GridViewAdapter(Context context, SingerBeens.DataBean singerBeens){
        this.context=context;
        this.singerBeens=singerBeens;
    }

    @NonNull
    @Override
    public GridViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.grideview_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(singerBeens.getArtistList().get(position).getPic()).transform(new GildeCilcleImageUtils(context)).into(holder.iv);
        holder.tv.setText(singerBeens.getArtistList().get(position).getName());
        holder.tv1.setText(singerBeens.getArtistList().get(position).getMusicNum()+"");
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return singerBeens.getArtistList().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv,tv1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             iv = itemView.findViewById(R.id.iv);
             tv =  itemView.findViewById(R.id.tv_name);
             tv1 =  itemView.findViewById(R.id.tv_num);
        }
    }
    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(GridViewAdapter.OnItemClick click){
        this.onItemClick=click;
    }
}

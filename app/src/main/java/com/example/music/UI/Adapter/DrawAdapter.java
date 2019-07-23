package com.example.music.UI.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.music.R;


/**
 * Created by Administrator on 2018/7/7.
 */

public class DrawAdapter extends RecyclerView.Adapter<DrawAdapter.ViewHolder>{
    private Context context;
    String[] images = {"http://img3.kwcdn.kuwo.cn/star/upload/8/9/1563440446.png","http://img3.kwcdn.kuwo.cn/star/upload/9/2/1563490826.png","http://img3.kwcdn.kuwo.cn/star/upload/9/6/1563490832.png",
            "http://img3.kwcdn.kuwo.cn/star/upload/0/3/1563440448.png","http://img3.kwcdn.kuwo.cn/star/upload/8/2/1562799941.png"};
    String[] text = {"酷我飙升榜","酷我新歌榜","酷我热歌榜","抖音热歌榜","会员畅听榜"};
    String[] time = {"今日更新","今日更新","今日更新","今日更新","今日更新"};
    public   OnItemClick onItemClick;
    public DrawAdapter(Context context){
        this.context=context;
    }
    @Override
    public DrawAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.gridviewadapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(context).load(images[position]).into(holder.iv);
        holder.tvtitle.setText(text[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClick!=null){
                    onItemClick.OnItemClikListener(position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return text.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvtitle;
        private ImageView iv;
        public ViewHolder(View itemView) {
            super(itemView);
             iv =  itemView.findViewById(R.id.iv_fengmian);
             tvtitle =  itemView.findViewById(R.id.tv_title);
        }
    }
    public interface OnItemClick{
        void OnItemClikListener(int pos);
    }
    public   void getOnItemClick(OnItemClick click){
        this.onItemClick=click;
    }
}

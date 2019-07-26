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
import com.example.music.R;

public class LeftDrawAdapter extends RecyclerView.Adapter<LeftDrawAdapter.ViewHolder> {
    private Context context;
    private String[] titles={"已下载音乐"};
    private int[] images={R.drawable.ic_local};
    public DrawAdapter.OnItemClick onItemClick;
    public LeftDrawAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public LeftDrawAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.leftdrawadapter,parent,false);
        return new LeftDrawAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeftDrawAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(images[position]).into(holder.ld_iv);
        holder.ld_tv.setText(titles[position]);

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
        return titles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ld_iv;
        private TextView ld_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ld_iv=itemView.findViewById(R.id.ld_iv);
            ld_tv=itemView.findViewById(R.id.ld_tv);
        }
    }
    public interface OnItemClick{
        void OnItemClikListener(int pos);
    }
    public   void getOnItemClick(DrawAdapter.OnItemClick click){
        this.onItemClick=click;
    }
}

package com.example.music.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.R;
import com.example.music.entry.RankBeens;


/**
 * Created by Administrator on 2018/7/7.
 */

public class DrawAdapter extends RecyclerView.Adapter<BindingViewHolder>{
    private Context context;
    public   OnItemClick onItemClick;
    private RankBeens dataBean;
    public DrawAdapter(Context context, RankBeens dataBean){
        this.context=context;
        this.dataBean=dataBean;
    }
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.gridviewadapter, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.rank, dataBean.getData().get(0).getList().get(position));
        binding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.OnItemClikListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBean.getData().get(0).getList().size();
    }

    public interface OnItemClick{
        void OnItemClikListener(int pos);
    }
    public   void getOnItemClick(OnItemClick click){
        this.onItemClick=click;
    }
}

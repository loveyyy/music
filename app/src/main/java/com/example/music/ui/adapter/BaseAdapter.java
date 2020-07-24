package com.example.music.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.Interface.OnDownLoadListener;
import com.example.music.R;
import com.example.music.ui.bindadapter.BindingViewHolder;

import java.util.List;

/**
 * Create By morningsun  on 2020-06-10
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<T> list;
    private int layoutId;//单布局
    private int variableId;
    private OnItemClick onItemClick;
    private OnDownLoad onDownLoad;
    public BaseAdapter(Context context, List<T> list, int layoutId, int variableId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        this.variableId = variableId;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(variableId,list.get(position));
        binding.executePendingBindings();
        if(holder.itemView.findViewById(R.id.btn_download)!=null){
            holder.itemView.findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onDownLoad!=null){
                        onDownLoad.OnDownLoadListener(position);
                    }
                }
            });
        }


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
         return list.size();
    }



    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(BaseAdapter.OnItemClick click){
        this.onItemClick=click;
    }


    //ItemView 的点击接口
    public interface OnDownLoad{
        void OnDownLoadListener(int pos);
    }
    public void setOnDownLoad(OnDownLoad onDownLoad){
        this.onDownLoad=onDownLoad;
    }
}

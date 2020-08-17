package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.model.BangMenu;
import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.databinding.BangMeauNameItemaptBinding;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Bang_Meau_Name_ItemApt extends RecyclerView.Adapter<BindingViewHolder> {
    private List<BangMenu.ListBean> listBeans;
    private Context context;
    private OnItemClick onItemClick;

    public Bang_Meau_Name_ItemApt(Context context, List<BangMenu.ListBean> listBeans){
        this.context=context;
        this.listBeans=listBeans;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BangMeauNameItemaptBinding bangMeauNameItemaptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bang_meau_name_itemapt,parent,false);
        return new BindingViewHolder(bangMeauNameItemaptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.bangmeaunameitem,listBeans.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick.OnItemClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(Bang_Meau_Name_ItemApt.OnItemClick click){
        this.onItemClick=click;
    }
}

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
import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.databinding.BangMeauItemaptBinding;
import com.example.music.model.Bang_list;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-03
 */
public class Bang_Meau_ItemApt extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<Bang_list.MusicListBean> musicListBeans;
    private  BangMeauItemaptBinding bangMeauItemaptBinding;
    private Bang_Meau_ItemApt.OnItemClick onItemClick;

    public  Bang_Meau_ItemApt(Context context, List<Bang_list.MusicListBean> musicListBeans){
        this.context=context;
        this.musicListBeans=musicListBeans;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         bangMeauItemaptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bang_meau_itemapt,parent,false);
         return new BindingViewHolder(bangMeauItemaptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.bangmeauitem,musicListBeans.get(position));
        bangMeauItemaptBinding.tvBangMeauItemPos.setText(String.valueOf(position+1));
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
        return 5;
    }

    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(Bang_Meau_ItemApt.OnItemClick click){
        this.onItemClick=click;
    }
}

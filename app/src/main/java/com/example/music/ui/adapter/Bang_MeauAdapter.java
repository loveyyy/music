package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.databinding.BangMeauaptBinding;
import com.example.music.model.BangList;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-03
 */
public class Bang_MeauAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private List<BangList> bang_meaus;
    private Context context;
    private BangMeauaptBinding bangMeauaptBinding;
    private Bang_MeauAdapter.OnItemClick onItemClick;
    public Bang_MeauAdapter(Context context, List<BangList> bang_meaus){
        this.bang_meaus=bang_meaus;
        this.context=context;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         bangMeauaptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bang_meauapt,parent,false);
        return new BindingViewHolder(bangMeauaptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.bang_meau,bang_meaus.get(position));
        bangMeauaptBinding.rcvBangMeau.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        Bang_Meau_ItemApt bang_meau_itemApt=new Bang_Meau_ItemApt(context,bang_meaus.get(position).getMusicList());
        bangMeauaptBinding.rcvBangMeau.setAdapter(bang_meau_itemApt);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.OnItemClickListener(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bang_meaus.size();
    }

    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(Bang_MeauAdapter.OnItemClick click){
        this.onItemClick=click;
    }

}

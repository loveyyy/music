package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.RcvRecListAptBinding;
import com.example.music.model.Rec_List_Info;
import com.example.music.ui.bindadapter.BindingViewHolder;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-10
 */
public class Rcv_Rec_List_apt extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<Rec_List_Info.MusicListBean> musicListBeans;
    public Rcv_Rec_List_apt(Context context, List<Rec_List_Info.MusicListBean> musicListBeans){
        this.context=context;
        this.musicListBeans=musicListBeans;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RcvRecListAptBinding rcvRecListAptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.rcv_rec_list_apt,parent,false);
        return new BindingViewHolder(rcvRecListAptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        ViewDataBinding viewDataBinding=holder.getBinding();
        viewDataBinding.setVariable(BR.rec_list_info,musicListBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return musicListBeans.size();
    }
}

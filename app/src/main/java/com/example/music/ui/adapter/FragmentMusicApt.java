package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.GvMusicAptBinding;
import com.example.music.model.RecList;
import com.example.music.ui.bindadapter.BindingViewHolder;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-10
 */
public class FragmentMusicApt extends RecyclerView.Adapter<BindingViewHolder> {
    private List<RecList.DataBean> listBeans;
    private Context context;
    private FragmentMusicApt.OnItemClick onItemClick;
    public FragmentMusicApt(Context context, List<RecList.DataBean> listBeans){
        this.context=context;
        this.listBeans=listBeans;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GvMusicAptBinding gvMusicAptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.gv_music_apt,parent,false);
        return new BindingViewHolder(gvMusicAptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.rec_list,listBeans.get(position));
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(FragmentMusicApt.OnItemClick click){
        this.onItemClick=click;
    }
}

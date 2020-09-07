package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.R;
import com.example.music.databinding.GvArtistMvAptBinding;
import com.example.music.model.ArtistMv;
import com.example.music.ui.bindadapter.BindingViewHolder;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class FragmentArtistMvApt extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<ArtistMv.MvlistBean> mvlistBeans;
    private FragmentArtistMvApt.OnItemClick onItemClick;
    public FragmentArtistMvApt(Context context, List<ArtistMv.MvlistBean> mvlistBeans){
        this.mvlistBeans=mvlistBeans;
        this.context=context;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GvArtistMvAptBinding artistMvAptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.gv_artist_mv_apt,parent,false);
        return new BindingViewHolder(artistMvAptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.artistmv, mvlistBeans.get(position));
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
        return mvlistBeans.size();
    }
    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(FragmentArtistMvApt.OnItemClick click){
        this.onItemClick=click;
    }
}

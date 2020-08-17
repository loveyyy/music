package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.music.R;
import com.example.music.databinding.BananerBinding;
import com.example.music.model.Banner;
import com.example.music.ui.bindadapter.BindingViewHolder;

import java.util.List;

public class Vp_BannerAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private List<Banner> list;
    private Context context;
    private BananerBinding bananerBinding;

    public Vp_BannerAdapter(Context context, List<Banner> list1) {
        this.context = context;
        this.list = list1;
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bananerBinding= DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.bananer,parent,false);
        return new BindingViewHolder(bananerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        final int newPosition = position % list.size();
        RoundedCorners roundedCorners= new RoundedCorners(20);
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(list.get(newPosition).getPic()).apply(requestOptions).into(bananerBinding.ivBananer);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

}

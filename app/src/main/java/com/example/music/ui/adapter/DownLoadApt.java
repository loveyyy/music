package com.example.music.ui.adapter;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.DownloadaptBinding;
import com.example.music.model.DownLoadInfo;
import com.example.music.server.DownloadTask;
import com.example.music.server.TaskDispatcher;
import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.ui.custom.CircularProgressView;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2020-06-18
 */
public class DownLoadApt extends RecyclerView.Adapter<BindingViewHolder>  {
    private Context context;
    private List<DownLoadInfo> downLoadInfos;
    private DownloadaptBinding downLoadApt;
    private DownLoadApt.OnItemClick onItemClick;

    public  DownLoadApt(Context context,List<DownLoadInfo> downLoadInfos){
        this.context=context;
        this.downLoadInfos=downLoadInfos;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        downLoadApt= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.downloadapt,parent,false);
        return new BindingViewHolder(downLoadApt);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(BR.downloadApt,downLoadInfos.get(position));
        CircularProgressView circularProgressView=holder.itemView.findViewById(R.id.pro_down);
        switch (downLoadInfos.get(position).getState()){
            case 0:
                downLoadApt.rlDownload.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setBackgroundResource(R.drawable.stop);
                break;
            case 1:
                downLoadApt.rlDownload.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setBackgroundResource(R.drawable.stop);
                break;
            case 2:
                downLoadApt.rlDownload.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setBackgroundResource(R.drawable.stop);
                break;
            case 3:
                downLoadApt.rlDownload.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setBackgroundResource(R.drawable.stop);
                circularProgressView.setProgress(downLoadInfos.get(position).getDownloadSize() * 100 / downLoadInfos.get(position).getTotalSize());
                break;
            case 4:
                downLoadApt.rlDownload.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setBackgroundResource(R.drawable.stop);
                circularProgressView.setProgress(downLoadInfos.get(position).getDownloadSize() * 100 / downLoadInfos.get(position).getTotalSize());
                break;
            case 5:
                downLoadApt.rlDownload.setVisibility(View.GONE);
                downLoadApt.ivDown.setVisibility(View.GONE);
                break;
            case 6:
                downLoadInfos.remove(position);
                break;
        }
        holder.itemView.findViewById(R.id.rl_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClick!=null){
                    onItemClick.OnItemClickListener(position);
                }
            }
        });
        downLoadApt.rlDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downLoadInfos.get(position).getState()==DownloadTask.STOP){
                    TaskDispatcher.getInstance().enqueue(downLoadInfos.get(position));
                }else{
                    TaskDispatcher.getInstance().stop(downLoadInfos.get(position).getId());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return downLoadInfos.size();
    }



    public void notifyData(DownLoadInfo downLoadInfo){
        for (DownLoadInfo downLoadInfo1:downLoadInfos){
            if(downLoadInfo1.getId().equals(downLoadInfo.getId())){
                downLoadInfos.remove(downLoadInfo1);
                downLoadInfos.add(downLoadInfo);
                break;
            }
        }
        notifyDataSetChanged();
    }



    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(DownLoadApt.OnItemClick click){
        this.onItemClick=click;
    }
}


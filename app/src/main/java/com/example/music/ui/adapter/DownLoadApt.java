package com.example.music.ui.adapter;

import android.content.Context;
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
    private List<DownloadTask> downLoadInfos=new ArrayList<>();
    private DownloadaptBinding downLoadApt;
    private DownLoadApt.OnItemClick onItemClick;

    public  DownLoadApt(Context context,List<DownloadTask> downLoadInfos){
        this.context=context;
        this.downLoadInfos.addAll(downLoadInfos);
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
        binding.setVariable(BR.downloadApt,downLoadInfos.get(position).getDownLoadInfo());
        downLoadInfos.get(position).setTag((long) position);
        CircularProgressView circularProgressView=holder.itemView.findViewById(R.id.pro_down);
        switch (downLoadInfos.get(position).getDownLoadInfo().getState()){
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
                circularProgressView.setProgress(downLoadInfos.get(position).getDownLoadInfo().getDownloadsize()*100/downLoadInfos.get(position).getDownLoadInfo().getSize());
                break;
            case 4:
                downLoadApt.rlDownload.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setBackgroundResource(R.drawable.stop);
                circularProgressView.setProgress(downLoadInfos.get(position).getDownLoadInfo().getDownloadsize()*100/downLoadInfos.get(position).getDownLoadInfo().getSize());
                break;
            case 5:
                downLoadApt.rlDownload.setVisibility(View.GONE);
                downLoadApt.ivDown.setVisibility(View.GONE);
                break;
            case 6:
                downLoadApt.rlDownload.setVisibility(View.GONE);
                downLoadApt.ivDown.setVisibility(View.VISIBLE);
                downLoadApt.ivDown.setBackgroundResource(R.drawable.play);
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

    }

    @Override
    public int getItemCount() {
        return downLoadInfos.size();
    }



    public void notifyData(){
//        if(!downLoadInfos.isEmpty()){
//            this.downLoadInfos.clear();
//            this.downLoadInfos.addAll(TaskDispatcher.getInstance().getQueueTaskList());
//            notifyDataSetChanged();
//        }

    }





    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos);
    }
    public void setOnItemClick(DownLoadApt.OnItemClick click){
        this.onItemClick=click;
    }
}


package com.example.music.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.R;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.ApiSubscribe;
import com.example.music.model.BaseRespon;
import com.example.music.model.DownLoadInfo;
import com.example.music.model.DownlodMusciInfo;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.DownloadTask;
import com.example.music.server.TaskDispatcher;
import com.example.music.ui.adapter.Pop_PlayAdapter;
import com.example.music.utils.ACache;

import java.io.File;
import java.util.List;

public class CustomDialogFragment extends DialogFragment {

    private RecyclerView rcv_pop_list;
    private LinearLayout rl_bindwallet_bac;
    private RelativeLayout rl_play_model;
    private ImageView iv_play_model;
    private TextView tv_play_model;
    private int play_model = 0;
    private ACache aCache;


    private List<PlayingMusicBeens> playingMusicBeens;

    public CustomDialogFragment(List<PlayingMusicBeens> playingMusicBeens, Context context) {
        this.playingMusicBeens=playingMusicBeens;
        aCache=ACache.get(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这句代码的意思是让dialogFragment弹出时沾满全屏
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pop_bindwallet, null);
        //让DialogFragment的背景为透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView(view);
        initEvent();
        return view;
    }

    //初始化view
    private void initView(View view) {
        rcv_pop_list =  view.findViewById(R.id.rcv_pop_list);
        iv_play_model=view.findViewById(R.id.iv_play_model);
        tv_play_model=view.findViewById(R.id.tv_play_model);
        rl_bindwallet_bac=view.findViewById(R.id.rl_bindwallet_bac);
        rl_play_model=view.findViewById(R.id.rl_play_model);
        if(aCache.getAsObject("play_model")!=null){
            play_model=(int)aCache.getAsObject("play_model");
        }
        if(play_model==0){
            iv_play_model.setBackgroundResource(R.drawable.ic_list_for);
            tv_play_model.setText("列表循环("+playingMusicBeens.size()+")");
        }else if (play_model==1){
            iv_play_model.setBackgroundResource(R.drawable.ic_single_fo);
            tv_play_model.setText("单曲循环("+playingMusicBeens.size()+")");
        }else{
            iv_play_model.setBackgroundResource(R.drawable.ic_single_fo);
            tv_play_model.setText("随机播放("+playingMusicBeens.size()+")");
        }

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        Pop_PlayAdapter pop_playAdapter=new Pop_PlayAdapter(getContext(),playingMusicBeens);
        pop_playAdapter.SetOnDownLoadClick(new Pop_PlayAdapter.DownLoadClick() {
            @Override
            public void OnDownLoad(int pos) {
                //查询链接
                Api.getInstance().iRetrofit.downloadMuisc(playingMusicBeens.get(pos).getMusicid().split("_")[1],
                        "kuwo","id",1,"XMLHttpRequest")
                        .compose(ApiSubscribe.<BaseRespon<List<DownlodMusciInfo>>>io_main())
                        .subscribe(new ApiResponse<BaseRespon<List<DownlodMusciInfo>>>() {
                            @Override
                            public void success(BaseRespon<List<DownlodMusciInfo>> data) {
                                DownLoadInfo downLoadInfo=new DownLoadInfo();
                                downLoadInfo.setUrl(data.getData().get(0).getUrl());
                                downLoadInfo.setFilename(data.getData().get(0).getAuthor()+"-"+data.getData().get(0).getTitle()+".mp3");
                                downLoadInfo.setFilepath(Environment.getExternalStorageDirectory().getPath() + File.separator + "mv");

                                DownloadTask downloadTask=new DownloadTask();
                                downloadTask.setDownLoadInfo(downLoadInfo);
                                TaskDispatcher.getInstance().enqueue(downloadTask);
                            }

                        });

            }
        });
        rcv_pop_list.setLayoutManager(layoutManager);
        rcv_pop_list.setAdapter(pop_playAdapter);

    }

    private void initEvent(){
        rl_bindwallet_bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rl_play_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play_model==2){
                    play_model=0;
                }else{
                    play_model++;
                }
                aCache.put("play_model",play_model);
                if(play_model==0){
                    iv_play_model.setBackgroundResource(R.drawable.ic_list_for);
                    tv_play_model.setText("列表循环("+playingMusicBeens.size()+")");
                }else if (play_model==1){
                    iv_play_model.setBackgroundResource(R.drawable.ic_single_fo);
                    tv_play_model.setText("单曲循环("+playingMusicBeens.size()+")");
                }else{
                    iv_play_model.setBackgroundResource(R.drawable.ic_rand);
                    tv_play_model.setText("随机播放("+playingMusicBeens.size()+")");
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null )
        {
            //如果宽高都为MATCH_PARENT,内容外的背景色就会失效，所以只设置宽全屏
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = getResources().getDimensionPixelSize(R.dimen.dp_500);
            dialog.getWindow().setLayout(width, height/2);//全屏
            dialog.getWindow().setGravity(Gravity.BOTTOM);//内容设置在底部
            //内容的背景色.对于全屏很重要，系统的内容宽度是不全屏的，替换为自己的后宽度可以全屏
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }




}

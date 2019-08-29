package com.example.music.View;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.music.Beens.PlayingMusicBeens;
import com.example.music.Interface.MusicInterface;
import com.example.music.R;
import com.example.music.Server.DownloadServer;
import com.example.music.Server.PlayServer;
import com.example.music.UI.Activity.TextLrc;
import com.example.music.Utils.GildeCilcleImageUtils;
import com.example.music.Utils.MusicUtils;
import com.example.music.Utils.Rereofit.Api;
import com.example.music.Utils.Rereofit.ApiResponse;
import com.example.music.Utils.Rereofit.ApiSubscribe;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class PlayerView extends RelativeLayout implements   SeekBar.OnSeekBarChangeListener{
    private ServiceConnection serviceConnection;
    private MusicInterface mi;
    private boolean isBind = false;
    private ImageButton btn_play_s, btn_play_n,ibtn_play_l,ibtn_download;//开始或者暂停   下一曲
    private ImageView iv_play_musicimage;
    private TextView tv_play_time, tv_play_songname;
    private SeekBar sb_play;

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                tv_play_songname.setText(playingMusicBeens.get(pos).getMusicname());
                tv_play_time.setText(String.valueOf(playingMusicBeens.get(pos).getDuration()));
                Glide.with(context).load(playingMusicBeens.get(pos).getPic()).
                        transform(new GildeCilcleImageUtils(context)).into(iv_play_musicimage);
                playmusci(playingMusicBeens.get(pos).getRid());
                sb_play.setMax(playingMusicBeens.get(pos).getDuration());
            }
        }
    };


    private String songurl;
    private int pos;

    private List<PlayingMusicBeens> playingMusicBeens=new ArrayList<>();
    private Context context;

    public PlayerView(Context context) {
        super(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.player, this);
        initView(view);
        setonclick();
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mi = (PlayServer.MusicController) service;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        //绑定服务
        Intent intent = new Intent(context, PlayServer.class);
        isBind = context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        this.context=context;
        EventBus.getDefault().register(this);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    private void initView(View view) {
        btn_play_s=view.findViewById(R.id.ibtn_play_s);
        btn_play_n = view.findViewById(R.id.ibtn_play_n);
        ibtn_play_l=view.findViewById(R.id.ibtn_play_l);
        iv_play_musicimage = findViewById(R.id.iv_playimage);
        tv_play_time = findViewById(R.id.tv_play_time);
        tv_play_time.setText("0.00");
        tv_play_songname = findViewById(R.id.tv_paly_songname);
        sb_play = findViewById(R.id.sb_play);
        ibtn_download=findViewById(R.id.ibtn_download);
        sb_play.setOnSeekBarChangeListener(this);
    }
    private void setonclick() {
        btn_play_n.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mi.PlayWithButton()==1){
                    //已暂停
                    btn_play_s.setBackgroundResource(R.drawable.ic_play);
                }else {
                    //已播放
                    btn_play_s.setBackgroundResource(R.drawable.ic_stop);
                }
            }
        });
        ibtn_download.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //下载
                Intent intent1 = new Intent(context, DownloadServer.class);
                intent1.putExtra("adress", songurl);
                intent1.putExtra("filename",playingMusicBeens.get(pos).getMusicname()+".map");
                intent1.putExtra("name",playingMusicBeens.get(pos).getMusicname());
                intent1.putExtra("json",new Gson().toJson(playingMusicBeens.get(pos),PlayingMusicBeens.class));
                context.startService(intent1);
            }
        });

        ibtn_play_l.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = pos - 1;
                if (pos>=0) {
                    handler.sendEmptyMessage(0);
                }else{
                    Toast.makeText(context,"当前已经是第一曲",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_play_n.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = pos +1;
                if (pos>=0) {
                    handler.sendEmptyMessage(0);
                }else{
                    Toast.makeText(context,"当前已经是第一曲",Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_play_musicimage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int progress = sb_play.getProgress();
                intent.putExtra("musicid", playingMusicBeens.get(pos).getRid());
                intent.putExtra("progress", progress);
                intent.putExtra("image",playingMusicBeens.get(pos).getAlbumpic());
                intent.setClass(context, TextLrc.class);
                context.startActivity(intent);
            }
        });

    }

    public   void play(final List<PlayingMusicBeens> playingMusicBeens , final int pos){
        this.playingMusicBeens=playingMusicBeens;
        this.pos=pos;
        handler.sendEmptyMessage(0);
    }

    private void playmusci(int rid){
        Api.getInstance().iRetrofit.getmusicinfo("http://www.kuwo.cn/url",
                "mp3",rid,"url",
                "convert_url3",
                "128kmp3").
                compose(ApiSubscribe.<ResponseBody>io_main()).subscribe(new ApiResponse<ResponseBody>(context){

            @Override
            public void success(ResponseBody data1) {
                //获得歌曲播放地址
                try {
                    JSONObject jsonObject=new JSONObject(data1.string());
                    songurl=jsonObject.get("url").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mi.Play(songurl);
                btn_play_s.setBackgroundResource(R.drawable.ic_stop);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void getseek(Integer progress){
        sb_play.setProgress(progress);
        if(sb_play.getMax()<=progress){
            //播放完成
            pos=pos+1;
            handler.sendEmptyMessage(0);
        }else{
            tv_play_time.setText(MusicUtils.formatTime(sb_play.getMax() - progress));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void getscroller(Double progree){
        sb_play.setProgress(progree.intValue());
        mi.PlayWithSb(progree.intValue());
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mi.PlayWithSb(seekBar.getProgress());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isBind) {
            context.unbindService(serviceConnection);
        }
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}

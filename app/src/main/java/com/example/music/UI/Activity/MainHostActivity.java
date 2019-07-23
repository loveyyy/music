package com.example.music.UI.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.example.music.Beens.RankMusicBeens;
import com.example.music.Beens.SearchBeens;
import com.example.music.Beens.SingerBeens;
import com.example.music.Beens.Singer_MusicBeens;
import com.example.music.UI.Adapter.GridViewAdapter;
import com.example.music.UI.Adapter.Rank_ItemAdapter;
import com.example.music.UI.Adapter.RlRankAdapter;
import com.example.music.UI.Adapter.SInger_music_ItemAdapter;
import com.example.music.UI.Adapter.Search_MusicAdapter;
import com.example.music.Utils.GildeCilcleImageUtils;
import com.example.music.Utils.Rereofit.Api;
import com.example.music.Utils.Rereofit.ApiResponse;
import com.example.music.Utils.Rereofit.ApiSubscribe;

import androidx.fragment.app.FragmentActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.music.UI.Adapter.DrawAdapter;
import com.example.music.Utils.Contans.Constans;
import com.example.music.Interface.MusicInterface;
import com.example.music.R;
import com.example.music.Server.PlayServer;
import com.example.music.Utils.MusicUtils;
import com.example.music.View.lrcText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MainHostActivity extends FragmentActivity implements View.OnClickListener,
       SeekBar.OnSeekBarChangeListener, PlayServer.SendProgress,
        lrcText.ScrollChange{
    //侧滑栏
    private DrawerLayout drawerLayout;
    //Main中的控件
    private RecyclerView recyclerView,left_recycle ,rcv_singer;
    private ImageView iv_maintou,ivdrawmaintou,iv_Search;
    private EditText et_search;


    //播放器中的控件
    private ImageButton btn_play_s, btn_play_n;//开始或者暂停   下一曲
    private ImageView iv_play_musicimage;
    private TextView tv_play_time, tv_play_songname;
    private SeekBar sb_play;

    //与服务交互
    private ServiceConnection serviceConnection;
    private MusicInterface mi;
    private boolean isBind = false;

    private int MusicID;
    //歌曲控制台
    private String imageurl;
    private int pos;
    private double duration;

    private int[] bangId={17,93,16,158,145};
    private String reqId ="606ea230-a9ec-11e9-be4c-675a988c502b";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainhost);
        initview();
        initdata();
        setlistener();
    }


    private void initview() {
        PlayServer.getSendProgress(this);
        pos = 0;
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
        Intent intent = new Intent(MainHostActivity.this, PlayServer.class);
        isBind = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        drawerLayout = findViewById(R.id.dl_Main);
        left_recycle = findViewById(R.id.left_RecyclerView);
        ivdrawmaintou = findViewById(R.id.ivdrawmaintou);
        btn_play_s = findViewById(R.id.ibtn_play_s);
        btn_play_n = findViewById(R.id.ibtn_play_n);
        iv_play_musicimage = findViewById(R.id.iv_playimage);
        tv_play_time = findViewById(R.id.tv_play_time);
        tv_play_time.setText("0.00");
        tv_play_songname = findViewById(R.id.tv_paly_songname);
        sb_play = findViewById(R.id.sb_play);
        iv_maintou = findViewById(R.id.iv_Maintou);
        recyclerView=findViewById(R.id.rcv_rank);
        rcv_singer=findViewById(R.id.rcv_singer);
        et_search=findViewById(R.id.et_search);
        iv_Search=findViewById(R.id.iv_Search);
    }

    public void initdata() {
        Glide.with(this).load(R.drawable.lable).transform(new GildeCilcleImageUtils(this)).into(ivdrawmaintou);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        left_recycle.setLayoutManager(layoutManager);
        DrawAdapter drawAdapter = new DrawAdapter(this);
        left_recycle.setAdapter(drawAdapter);
        drawAdapter.getOnItemClick(new DrawAdapter.OnItemClick() {
            @Override
            public void OnItemClikListener(int pos) {
                getdata(bangId[pos],reqId);
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        Api.getInstance().iRetrofit.getsinger("http://www.kuwo.cn/api/www/artist/artistInfo",11,1,6,"635ee580-aa06-11e9-8bbc-afbabde1324f")
                .compose(ApiSubscribe.<SingerBeens>io_main()).subscribe(new ApiResponse<SingerBeens>(this){

            @Override
            public void success(final SingerBeens data) {
                RecyclerView.LayoutManager layoutManager2 =new LinearLayoutManager(MainHostActivity.this,RecyclerView.HORIZONTAL,false);
                rcv_singer.setLayoutManager(layoutManager2);
                GridViewAdapter gridViewAdapter=new GridViewAdapter(MainHostActivity.this,data.getData());
                rcv_singer.setAdapter(gridViewAdapter);

                gridViewAdapter.setOnItemClick(new GridViewAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        getsinger_music(data.getData().getArtistList().get(pos).getId(),data.getReqId());
                    }
                });
            }
        });
        getdata(bangId[0],reqId);
    }

    public void setlistener() {
        btn_play_n.setOnClickListener(this);
        btn_play_s.setOnClickListener(this);
        iv_maintou.setOnClickListener(this);
        iv_play_musicimage.setOnClickListener(this);
        sb_play.setOnSeekBarChangeListener(this);
        iv_Search.setOnClickListener(this);
    }
    private void getsinger_music(int artistid,String reqId){
        Api.getInstance().iRetrofit.getsinger_music("http://www.kuwo.cn/api/www/artist/artistMusic",artistid,1,30,reqId)
                .compose(ApiSubscribe.<Singer_MusicBeens>io_main()).subscribe(new ApiResponse<Singer_MusicBeens>(this) {
            @Override
            public void success(final Singer_MusicBeens data) {
                    //刷新数据
                RecyclerView.LayoutManager layoutManager1 =new LinearLayoutManager(MainHostActivity.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager1);
                SInger_music_ItemAdapter sInger_music_itemAdapter=new SInger_music_ItemAdapter(MainHostActivity.this,data.getData());
                recyclerView.setAdapter(sInger_music_itemAdapter);

                sInger_music_itemAdapter.setOnItemClick(new RlRankAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(final int pos) {
                        tv_play_songname.setText(data.getData().getList().get(pos).getName());
                        tv_play_time.setText(String.valueOf(data.getData().getList().get(pos).getSongTimeMinutes()));
                        MusicID=data.getData().getList().get(pos).getRid();
                        imageurl=data.getData().getList().get(pos).getAlbumpic();
                        Glide.with(MainHostActivity.this).load(data.getData().getList().get(pos).getPic()).
                                transform(new GildeCilcleImageUtils(MainHostActivity.this)).into(iv_play_musicimage);
                        getmusicurl(data.getData().getList().get(pos).getRid(),
                                data.getCurTime(),data.getReqId(),data.getData().getList().get(pos).getDuration());
                    }
                });
            }
        });
    }


    private  void getdata(int bangId, String reqId){
        Api.getInstance().iRetrofit.getmusicrank("http://www.kuwo.cn/api/www/bang/bang/musicList",bangId,1,30,reqId)
                .compose(ApiSubscribe.<RankMusicBeens>io_main()).subscribe(new ApiResponse<RankMusicBeens>(this) {
            @Override
            public void success(final RankMusicBeens data) {
                RecyclerView.LayoutManager layoutManager1 =new LinearLayoutManager(MainHostActivity.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager1);
                Rank_ItemAdapter rank_itemAdapter=new Rank_ItemAdapter(MainHostActivity.this,data.getData());
                recyclerView.setAdapter(rank_itemAdapter);

                rank_itemAdapter.setOnItemClick(new RlRankAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(final int pos) {
                        tv_play_songname.setText(data.getData().getMusicList().get(pos).getName());
                        tv_play_time.setText(String.valueOf(data.getData().getMusicList().get(pos).getSongTimeMinutes()));
                        MusicID=data.getData().getMusicList().get(pos).getRid();
                        imageurl=data.getData().getMusicList().get(pos).getAlbumpic();
                        Glide.with(MainHostActivity.this).load(data.getData().getMusicList().get(pos).getPic()).
                                transform(new GildeCilcleImageUtils(MainHostActivity.this)).into(iv_play_musicimage);
                        getmusicurl(data.getData().getMusicList().get(pos).getRid(),
                                data.getCurTime(),data.getReqId(),data.getData().getMusicList().get(pos).getDuration());
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_play_n:
//                pos = pos + 1;
//                if (netMusicBeens != null) {
//                    mi.next(netMusicBeens.get(pos).getSongmid());
//                    int imageurl = Integer.parseInt(netMusicBeens.get(pos).getSingerid());
////                    Glide.with(MainHostActivity.this).load(Constans.ImageUrl(netMusicBeens.get(pos).getSingerid(), 300, imageurl % 100)).into(iv_play_musicimage);
//                    tv_play_songname.setText(netMusicBeens.get(pos).getSongname());
//                } else {
//                    mi.next(musicBeens.get(pos).getUrl());
//                    Glide.with(MainHostActivity.this).load(R.drawable.music).into(iv_play_musicimage);
//                    tv_play_songname.setText(musicBeens.get(pos).getTitle());
//                }
                break;

            case R.id.ibtn_play_s:
                if(mi.PlayWithButton()==1){
                 //已暂停
                    btn_play_s.setBackgroundResource(R.drawable.ic_play);
                }else {
                    //已播放
                    btn_play_s.setBackgroundResource(R.drawable.ic_stop);
                }
                break;

            case R.id.iv_Maintou:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.iv_playimage:
                Intent intent = new Intent();
                int progress = sb_play.getProgress();
                intent.putExtra("musicid", MusicID);
                intent.putExtra("progress", progress);
                intent.putExtra("image",imageurl);
                intent.setClass(MainHostActivity.this, TextLrc.class);
                startActivity(intent);
                break;

            case R.id.iv_Search:
                    Api.getInstance().iRetrofit.search("http://www.kuwo.cn/api/www/search/searchMusicBykeyWord",
                            et_search.getText().toString(),1,30,"9b992050-ad30-11e9-a442-47291f49893f")
                            .compose(ApiSubscribe.<SearchBeens>io_main()).subscribe(new ApiResponse<SearchBeens>(MainHostActivity.this) {
                        @Override
                        public void success(final SearchBeens data) {
                            RecyclerView.LayoutManager layoutManager4 =new LinearLayoutManager(MainHostActivity.this,RecyclerView.VERTICAL,false);
                            recyclerView.setLayoutManager(layoutManager4);
                            Search_MusicAdapter search_musicAdapter=new Search_MusicAdapter(MainHostActivity.this,data.getData());
                            recyclerView.setAdapter(search_musicAdapter);

                            search_musicAdapter.setOnItemClick(new Search_MusicAdapter.OnItemClick() {
                                @Override
                                public void OnItemClickListener(final int pos) {
                                    tv_play_songname.setText(data.getData().getList().get(pos).getName());
                                    tv_play_time.setText(String.valueOf(data.getData().getList().get(pos).getSongTimeMinutes()));
                                    MusicID=data.getData().getList().get(pos).getRid();
                                    imageurl=data.getData().getList().get(pos).getAlbumpic();
                                    Glide.with(MainHostActivity.this).load(data.getData().getList().get(pos).getPic()).
                                            transform(new GildeCilcleImageUtils(MainHostActivity.this)).into(iv_play_musicimage);
                                    getmusicurl(data.getData().getList().get(pos).getRid(),
                                            data.getCurTime(),data.getReqId(),data.getData().getList().get(pos).getDuration());
                                }
                            });
                        }
                    });

        }
    }

    private void getmusicurl(int  rid,long curtime,String reqid,final int duration){
        Api.getInstance().iRetrofit.getmusicinfo("http://www.kuwo.cn/url",
                "mp3",rid,"url",
                "convert_url3",
                "128kmp3","web",
                curtime,
                reqid).
                compose(ApiSubscribe.<ResponseBody>io_main()).subscribe(new ApiResponse<ResponseBody>(MainHostActivity.this){

            @Override
            public void success(ResponseBody data1) {
                //获得歌曲播放地址
                try {
                    JSONObject jsonObject=new JSONObject(data1.string());
                    String url=jsonObject.get("url").toString();
                    mi.Play(url,duration);
                    btn_play_s.setBackgroundResource(R.drawable.ic_stop);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void sendP(final int progress, final int time) {
        sb_play.setMax(time);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sb_play.setProgress(progress);
                tv_play_time.setText(MusicUtils.formatTime(time - progress));
            }
        });


    }

    @Override
    public void ScrollProgree(Double progree) {
        sb_play.setProgress(progree.intValue());
    }




    //SeekBar监听
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
    protected void onDestroy() {
        super.onDestroy();
        if (isBind) {
            unbindService(serviceConnection);
        }
    }
}

package com.example.music.UI.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.Beens.PlayingMusicBeens;
import com.example.music.Beens.RankMusicBeens;
import com.example.music.Beens.SearchBeens;
import com.example.music.Beens.SingerBeens;
import com.example.music.Beens.Singer_MusicBeens;
import com.example.music.Interface.MusicInterface;
import com.example.music.R;
import com.example.music.Server.DownloadServer;
import com.example.music.Server.PlayServer;
import com.example.music.UI.Adapter.DrawAdapter;
import com.example.music.UI.Adapter.GridViewAdapter;
import com.example.music.UI.Adapter.LeftDrawAdapter;
import com.example.music.UI.Adapter.Rank_ItemAdapter;
import com.example.music.UI.Adapter.RlRankAdapter;
import com.example.music.UI.Adapter.SInger_music_ItemAdapter;
import com.example.music.UI.Adapter.Search_MusicAdapter;
import com.example.music.Utils.Contans.Constans;
import com.example.music.Utils.GildeCilcleImageUtils;
import com.example.music.Utils.MusicUtils;
import com.example.music.Utils.Rereofit.Api;
import com.example.music.Utils.Rereofit.ApiResponse;
import com.example.music.Utils.Rereofit.ApiSubscribe;
import com.example.music.View.lrcText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private RecyclerView recyclerView,left_recycle ,rcv_singer,rcv_ranktype;
    private ImageView iv_maintou,ivdrawmaintou,iv_Search;
    private EditText et_search;


    //播放器中的控件
    private ImageButton btn_play_s, btn_play_n,ibtn_play_l,ibtn_download;//开始或者暂停   下一曲
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

    private int[] bangId={17,93,16,158,145};

    //选择播放的音乐列表
    private List<PlayingMusicBeens> playingMusicBeensList =new ArrayList<>() ;
    private int playmusic_pos=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.mainhost);
        initview();
        initdata();
        setlistener();
    }


    private void initview() {
        PlayServer.getSendProgress(this);
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
        ibtn_play_l=findViewById(R.id.ibtn_play_l);
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
        rcv_ranktype=findViewById(R.id.rcv_ranktype);
        ibtn_download=findViewById(R.id.ibtn_download);
    }

    public void initdata() {
        Glide.with(this).load(R.drawable.lable).transform(new GildeCilcleImageUtils(this)).into(ivdrawmaintou);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        left_recycle.setLayoutManager(layoutManager);
        LeftDrawAdapter leftDrawAdapter = new LeftDrawAdapter(this);
        left_recycle.setAdapter(leftDrawAdapter);
        leftDrawAdapter.getOnItemClick(new DrawAdapter.OnItemClick() {
            @Override
            public void OnItemClikListener(int pos) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                //{"album":"相信你的人（热血励志版）","albumpic":"http://img1.kuwo.cn/star/albumcover/500/83/95/2541220372.jpg",
                // "duration":262,"music_singer":"陈奕迅","musicname":"相信你的人(热血励志版)",
                // "pic":"http://img1.kuwo.cn/star/albumcover/300/83/95/2541220372.jpg","rid":72867626}
                //本地音乐配置文件

            }
        });
        Api.getInstance().iRetrofit.getsinger("http://www.kuwo.cn/api/www/artist/artistInfo",11,1,6)
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
        RecyclerView.LayoutManager layoutManager5 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcv_ranktype.setLayoutManager(layoutManager5);
        DrawAdapter drawAdapter1 = new DrawAdapter(this);
        rcv_ranktype.setAdapter(drawAdapter1);
        drawAdapter1.getOnItemClick(new DrawAdapter.OnItemClick() {
            @Override
            public void OnItemClikListener(int pos) {
                getdata(bangId[pos]);
            }
        });
        getdata(bangId[0]);
    }

    public void setlistener() {
        btn_play_n.setOnClickListener(this);
        btn_play_s.setOnClickListener(this);
        ibtn_play_l.setOnClickListener(this);
        iv_maintou.setOnClickListener(this);
        iv_play_musicimage.setOnClickListener(this);
        sb_play.setOnSeekBarChangeListener(this);
        iv_Search.setOnClickListener(this);
        ibtn_download.setOnClickListener(this);
    }
    private void getsinger_music(int artistid,String reqId){
        Api.getInstance().iRetrofit.getsinger_music("http://www.kuwo.cn/api/www/artist/artistMusic",artistid,1,30)
                .compose(ApiSubscribe.<Singer_MusicBeens>io_main()).subscribe(new ApiResponse<Singer_MusicBeens>(this) {
            @Override
            public void success(final Singer_MusicBeens data) {
                    //刷新数据
                RecyclerView.LayoutManager layoutManager1 =new LinearLayoutManager(MainHostActivity.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager1);
                SInger_music_ItemAdapter sInger_music_itemAdapter=new SInger_music_ItemAdapter(MainHostActivity.this,data.getData());
                recyclerView.setNestedScrollingEnabled(false);
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
                                data.getData().getList().get(pos).getDuration(),0,"","");

                        playingMusicBeensList.clear();
                        for (Singer_MusicBeens.DataBean.ListBean listBean:data.getData().getList()) {
                            PlayingMusicBeens playingMusicBeens=new PlayingMusicBeens();
                            playingMusicBeens.setMusic_singer(listBean.getArtist());
                            playingMusicBeens.setMusicname(listBean.getName());
                            playingMusicBeens.setPic(listBean.getPic());
                            playingMusicBeens.setRid(listBean.getRid());
                            playingMusicBeens.setDuration(listBean.getDuration());
                            playingMusicBeens.setAlbum(listBean.getAlbum());
                            playingMusicBeens.setAlbumpic(listBean.getAlbumpic());
                            playingMusicBeensList.add(playingMusicBeens);
                        }
                        playmusic_pos=pos;

                    }
                });
            }
        });
    }


    private  void getdata(int bangId){
        Api.getInstance().iRetrofit.getmusicrank("http://www.kuwo.cn/api/www/bang/bang/musicList",bangId,1,30)
                .compose(ApiSubscribe.<RankMusicBeens>io_main()).subscribe(new ApiResponse<RankMusicBeens>(this) {
            @Override
            public void success(final RankMusicBeens data) {
                RecyclerView.LayoutManager layoutManager1 =new LinearLayoutManager(MainHostActivity.this,RecyclerView.VERTICAL,false);
                recyclerView.setLayoutManager(layoutManager1);
                Rank_ItemAdapter rank_itemAdapter=new Rank_ItemAdapter(MainHostActivity.this,data.getData());
                recyclerView.setAdapter(rank_itemAdapter);
                recyclerView.setNestedScrollingEnabled(false);

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
                               data.getData().getMusicList().get(pos).getDuration(),0,"","");

                        playingMusicBeensList.clear();
                        for (RankMusicBeens.DataBean.MusicListBean listBean:data.getData().getMusicList()) {
                            PlayingMusicBeens playingMusicBeens=new PlayingMusicBeens();
                            playingMusicBeens.setMusic_singer(listBean.getArtist());
                            playingMusicBeens.setMusicname(listBean.getName());
                            playingMusicBeens.setPic(listBean.getPic());
                            playingMusicBeens.setRid(listBean.getRid());
                            playingMusicBeens.setDuration(listBean.getDuration());
                            playingMusicBeens.setAlbum(listBean.getAlbum());
                            playingMusicBeens.setAlbumpic(listBean.getAlbumpic());
                            playingMusicBeensList.add(playingMusicBeens);
                        }
                        playmusic_pos=pos;
                    }
                });
            }
        });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.ibtn_download:
                getmusicurl(playingMusicBeensList.get(playmusic_pos).getRid(),
                        playingMusicBeensList.get(playmusic_pos).getDuration(),1,playingMusicBeensList.get(playmusic_pos).getMusicname(),
                        new Gson().toJson(playingMusicBeensList.get(playmusic_pos),PlayingMusicBeens.class));
                break;

            case R.id.ibtn_play_l:
                playmusic_pos = playmusic_pos - 1;
                if (playmusic_pos>=0) {
                    Glide.with(MainHostActivity.this).load(playingMusicBeensList.get(playmusic_pos).getPic()).into(iv_play_musicimage);
                    tv_play_songname.setText(playingMusicBeensList.get(playmusic_pos).getMusicname());
                    getmusicurl(playingMusicBeensList.get(playmusic_pos).getRid(),
                            playingMusicBeensList.get(playmusic_pos).getDuration(),0,"","");
                }else{
                    Toast.makeText(MainHostActivity.this,"当前已经是第一曲",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ibtn_play_n:
                playmusic_pos = playmusic_pos + 1;
                if (playmusic_pos<=playingMusicBeensList.size()) {
                    Glide.with(MainHostActivity.this).load(playingMusicBeensList.get(playmusic_pos).getPic()).into(iv_play_musicimage);
                    tv_play_songname.setText(playingMusicBeensList.get(playmusic_pos).getMusicname());
                    getmusicurl(playingMusicBeensList.get(playmusic_pos).getRid(),
                            playingMusicBeensList.get(playmusic_pos).getDuration(),0,"","");
                }else{
                    Toast.makeText(MainHostActivity.this,"当前已经是最后一曲",Toast.LENGTH_SHORT).show();
                }
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
                            et_search.getText().toString(),1,30)
                            .compose(ApiSubscribe.<SearchBeens>io_main()).subscribe(new ApiResponse<SearchBeens>(MainHostActivity.this) {
                        @Override
                        public void success(final SearchBeens data) {
                            RecyclerView.LayoutManager layoutManager4 =new LinearLayoutManager(MainHostActivity.this,RecyclerView.VERTICAL,false);
                            recyclerView.setLayoutManager(layoutManager4);
                            Search_MusicAdapter search_musicAdapter=new Search_MusicAdapter(MainHostActivity.this,data.getData());
                            recyclerView.setNestedScrollingEnabled(false);
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
                                    getmusicurl(data.getData().getList().get(pos).getRid()
                                            ,data.getData().getList().get(pos).getDuration(),0,data.getData().getList().get(pos).getName(),"");
                                    //记录正在播放的音乐列表
                                    playingMusicBeensList.clear();
                                    for (SearchBeens.DataBean.ListBean listBean:data.getData().getList()) {
                                        PlayingMusicBeens playingMusicBeens=new PlayingMusicBeens();
                                        playingMusicBeens.setMusic_singer(listBean.getArtist());
                                        playingMusicBeens.setMusicname(listBean.getName());
                                        playingMusicBeens.setPic(listBean.getPic());
                                        playingMusicBeens.setRid(listBean.getRid());
                                        playingMusicBeens.setDuration(listBean.getDuration());
                                        playingMusicBeens.setAlbum(listBean.getAlbum());
                                        playingMusicBeens.setAlbumpic(listBean.getAlbumpic());
                                        playingMusicBeensList.add(playingMusicBeens);
                                    }
                                    playmusic_pos=pos;

                                }
                            });
                        }
                    });

        }
    }
    private void getmusicurl(final int  rid, final int duration, final int type,final String name,final String json){
        Api.getInstance().iRetrofit.getmusicinfo("http://www.kuwo.cn/url",
                "mp3",rid,"url",
                "convert_url3",
                "128kmp3").
                compose(ApiSubscribe.<ResponseBody>io_main()).subscribe(new ApiResponse<ResponseBody>(MainHostActivity.this){

            @Override
            public void success(ResponseBody data1) {
                //获得歌曲播放地址
                String url="";
                try {
                    JSONObject jsonObject=new JSONObject(data1.string());
                    url=jsonObject.get("url").toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(type==0){
                    //仅播放
                    mi.Play(url,duration);
                    btn_play_s.setBackgroundResource(R.drawable.ic_stop);
                }else{
                    //下载
                    Intent intent1 = new Intent(MainHostActivity.this, DownloadServer.class);
                    intent1.putExtra("adress", url);
                    intent1.putExtra("filename",name+".map");
                    intent1.putExtra("name",name);
                    intent1.putExtra("json",json);
                    startService(intent1);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBind) {
            unbindService(serviceConnection);
        }
    }
}

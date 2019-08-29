package com.example.music.UI.Activity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.Beens.PlayingMusicBeens;
import com.example.music.Beens.RankMusicBeens;
import com.example.music.Beens.SearchBeens;
import com.example.music.Beens.SingerBeens;
import com.example.music.Beens.Singer_MusicBeens;
import com.example.music.R;
import com.example.music.UI.Adapter.DrawAdapter;
import com.example.music.UI.Adapter.GridViewAdapter;
import com.example.music.UI.Adapter.LeftDrawAdapter;
import com.example.music.UI.Adapter.Rank_ItemAdapter;
import com.example.music.UI.Adapter.RlRankAdapter;
import com.example.music.UI.Adapter.SInger_music_ItemAdapter;
import com.example.music.UI.Adapter.Search_MusicAdapter;
import com.example.music.UI.Model.ModelTest;
import com.example.music.Utils.GildeCilcleImageUtils;
import com.example.music.Utils.Rereofit.Api;
import com.example.music.Utils.Rereofit.ApiResponse;
import com.example.music.Utils.Rereofit.ApiSubscribe;
import com.example.music.View.PlayerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MainHostActivity extends FragmentActivity implements View.OnClickListener{
    //侧滑栏
    private DrawerLayout drawerLayout;
    //Main中的控件
    private RecyclerView recyclerView,left_recycle ,rcv_singer,rcv_ranktype;
    private ImageView iv_maintou,ivdrawmaintou,iv_Search;
    private EditText et_search;

    private int[] bangId={17,93,16,158,145};
    private PlayerView playerView;
    private ModelTest modelTest;

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
        drawerLayout = findViewById(R.id.dl_Main);
        left_recycle = findViewById(R.id.left_RecyclerView);
        ivdrawmaintou = findViewById(R.id.ivdrawmaintou);
        iv_maintou = findViewById(R.id.iv_Maintou);
        recyclerView=findViewById(R.id.rcv_rank);
        rcv_singer=findViewById(R.id.rcv_singer);
        et_search=findViewById(R.id.et_search);
        iv_Search=findViewById(R.id.iv_Search);
        rcv_ranktype=findViewById(R.id.rcv_ranktype);
        playerView=findViewById(R.id.playview);
    }

    public void initdata() {
//        modelTest = new ViewModelProvider(
//                this, new ViewModelProvider.AndroidViewModelFactory(getApplication())
//        ).get(ModelTest.class);
//        modelTest.getImage().observe(this, new Observer<SearchBeens>() {
//            @Override
//            public void onChanged(SearchBeens searchBeens) {
//                ///更新数据
//            }
//        });
//        modelTest.getmusic(this,et_search.getText().toString());

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
        iv_maintou.setOnClickListener(this);
        iv_Search.setOnClickListener(this);
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
                            playingMusicBeens.setMusicid(listBean.getMusicrid());
                            playingMusicBeensList.add(playingMusicBeens);
                        }
                        playmusic_pos=pos;
                        playerView.play(playingMusicBeensList,playmusic_pos);

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
                            playingMusicBeens.setMusicid(listBean.getMusicrid());
                            playingMusicBeensList.add(playingMusicBeens);
                        }
                        playmusic_pos=pos;

                        playerView.play(playingMusicBeensList,playmusic_pos);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.iv_Maintou:
                drawerLayout.openDrawer(Gravity.LEFT);
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
                                        playingMusicBeens.setMusicid(listBean.getMusicrid());
                                        playingMusicBeensList.add(playingMusicBeens);
                                    }
                                    playmusic_pos=pos;
                                    playerView.play(playingMusicBeensList,playmusic_pos);

                                }
                            });
                        }
                    });

        }
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

package com.example.music.ui.frament;

import android.content.Context;
import android.os.Environment;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.Interface.PlayMusic;
import com.example.music.R;
import com.example.music.databinding.FramentArtistMusicBinding;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.ArtistMusic;
import com.example.music.model.BaseRespon;
import com.example.music.model.DownLoadInfo;
import com.example.music.model.DownlodMusciInfo;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.TaskDispatcher;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.BaseAdapter;
import com.example.music.ui.base.BaseFragment;
import com.example.music.viewmodel.SingerVM;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class FragmentArtistMusic extends BaseFragment<FramentArtistMusicBinding,SingerVM> {
    private FramentArtistMusicBinding framentArtistMusicBinding;
    private SingerVM singerVM;
    private int artistId;
    private PlayMusic playMusic;
    @Override
    protected int getContentViewId() {
        return R.layout.frament_artist_music;
    }

    @Override
    protected void initView(FramentArtistMusicBinding bindView) {
        framentArtistMusicBinding=bindView;
    }

    @Override
    protected void SetVM(SingerVM vm) {
        singerVM=vm;
        singerVM.artistMusic.observe(this, new Observer<BaseRespon<ArtistMusic>>() {
            @Override
            public void onChanged(final BaseRespon<ArtistMusic> artist_listBaseRespon) {
                framentArtistMusicBinding.rcvArtistMusic.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
                BaseAdapter baseAdapter =new BaseAdapter<>(getContext(),artist_listBaseRespon.getData().getList(), R.layout.frament_artist_music_apt,BR.artistmusic);
//                Frament_artist_music_Apt frament_artist_music_apt=new Frament_artist_music_Apt(getContext(),artist_listBaseRespon.getData().getList());
                framentArtistMusicBinding.rcvArtistMusic.setAdapter(baseAdapter);

                baseAdapter.setOnDownLoad(new BaseAdapter.OnDownLoad() {
                    @Override
                    public void OnDownLoadListener(int pos) {
                        //查询链接
                        Api.getInstance().iRetrofit.downloadMusic(artist_listBaseRespon.getData().getList().get(pos).getMusicrid().split("_")[1],
                                "kuwo","id",1,"XMLHttpRequest")
                                .compose(RxHelper.observableIO2Main(getActivity()))
                                .subscribe(new ApiResponse<BaseRespon<List<DownlodMusciInfo>>>() {
                                    @Override
                                    public void success(BaseRespon<List<DownlodMusciInfo>> data) {
                                        DownLoadInfo downLoadInfo=new DownLoadInfo();
                                        downLoadInfo.setUrl(data.getData().get(0).getUrl());
                                        downLoadInfo.setFilename(data.getData().get(0).getAuthor()+"-"+data.getData().get(0).getTitle()+".mp3");
                                        downLoadInfo.setFilepath(Environment.getExternalStorageDirectory().getPath() + File.separator + "mv");

                                        TaskDispatcher.getInstance().enqueue(downLoadInfo);
                                    }

                                });
                    }
                });

                baseAdapter.setOnItemClick(new BaseAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        ArrayList<PlayingMusicBeens> playingMusicBeens = new ArrayList<>();
                        for (ArtistMusic.ListBean listBean : artist_listBaseRespon.getData().getList()) {
                            PlayingMusicBeens playingMusicBeens1 = new PlayingMusicBeens();
                            playingMusicBeens1.setAlbum(listBean.getAlbum());
                            playingMusicBeens1.setAlbumpic(listBean.getAlbumpic());
                            playingMusicBeens1.setDuration(listBean.getDuration());
                            playingMusicBeens1.setMusic_singer(listBean.getArtist());
                            playingMusicBeens1.setMusicid(listBean.getMusicrid());
                            playingMusicBeens1.setMusicname(listBean.getName());
                            playingMusicBeens1.setPic(listBean.getPic());
                            playingMusicBeens1.setRid(String.valueOf(listBean.getRid()));
                            playingMusicBeens.add(playingMusicBeens1);
                        }
                        playMusic.Play(playingMusicBeens,pos);
                    }
                });

            }
        });


    }

    @Override
    protected void setOnClick() {

    }

    @Override
    protected void initData() {
        singerVM.getArtistMusic(String.valueOf(artistId),"1","30");
    }

    @Override
    protected boolean getNeedRefresh() {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        artistId = ((Singer_Activity) context).getartistid();
        playMusic= (PlayMusic) getActivity();
    }
}

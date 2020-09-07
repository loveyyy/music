package com.example.music.viewmodel;

import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.BangMusicList;
import com.example.music.model.BaseRespon;
import com.example.music.model.DownLoadInfo;
import com.example.music.model.DownlodMusciInfo;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.server.TaskDispatcher;
import com.example.music.ui.adapter.Music_ItemAdapter;
import com.example.music.ui.base.BaseVM;
import com.example.music.utils.PlayController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class BangMenuVm extends BaseVM {

    public MutableLiveData<BaseRespon<BangMusicList>> bangMusicList = new MutableLiveData<>();

    public MutableLiveData<Music_ItemAdapter> adapterMutableLiveData = new MutableLiveData<>();

    public BangMenuVm(@NonNull Application application) {
        super(application);
    }

    public void getBangMusicList(String bangId, String pn, String rn) {
        Api.getInstance().iRetrofit.bangMusicList(bangId, pn, rn, getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<BangMusicList>>() {
                    @Override
                    public void success(BaseRespon<BangMusicList> data) {
                        try {
                            Music_ItemAdapter music_itemAdapter = new Music_ItemAdapter(getApplication(), data.getData().getMusicList());
                            adapterMutableLiveData.setValue(music_itemAdapter);
                            bangMusicList.setValue(data);
                            setOnClick();
                        }catch (NullPointerException e){
                            Toast.makeText(getApplication(),"请返回重试",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void setOnClick() {
        adapterMutableLiveData.getValue().setOnItemClick(new Music_ItemAdapter.OnItemClick() {
            @Override
            public void OnItemClickListener(int pos) {
                Api.getInstance().iRetrofit.downloadMusic(bangMusicList.getValue().getData().getMusicList().get(pos).getMusicrid().split("_")[1],
                        "kuwo", "id", 1, "XMLHttpRequest")
                        .compose(RxHelper.observableIO2Main(getApplication()))
                        .subscribe(new ApiResponse<BaseRespon<List<DownlodMusciInfo>>>() {
                            @Override
                            public void success(BaseRespon<List<DownlodMusciInfo>> data) {
                                DownLoadInfo downLoadInfo = new DownLoadInfo();
                                downLoadInfo.setUrl(data.getData().get(0).getUrl());
                                downLoadInfo.setFilename(data.getData().get(0).getAuthor() + "-" + data.getData().get(0).getTitle() + ".mp3");
                                downLoadInfo.setFilepath(Environment.getExternalStorageDirectory().getPath() + File.separator + "mv");
                                TaskDispatcher.getInstance().enqueue(downLoadInfo);
                            }

                        });
            }
        });
        adapterMutableLiveData.getValue().setOnDownLoad(new Music_ItemAdapter.OnDownLoad() {
            @Override
            public void OnDownLoadListener(int pos) {
                ArrayList<PlayingMusicBeens> playingMusicBeens = new ArrayList<>();
                for (BangMusicList.MusicListBean musicListBean : bangMusicList.getValue().getData().getMusicList()) {
                    PlayingMusicBeens playingMusicBeens1 = new PlayingMusicBeens();
                    playingMusicBeens1.setAlbum(musicListBean.getAlbum());
                    playingMusicBeens1.setAlbumpic(musicListBean.getAlbumpic());
                    playingMusicBeens1.setDuration(musicListBean.getDuration());
                    playingMusicBeens1.setMusic_singer(musicListBean.getArtist());
                    playingMusicBeens1.setMusicid(musicListBean.getMusicrid());
                    playingMusicBeens1.setMusicname(musicListBean.getName());
                    playingMusicBeens1.setPic(musicListBean.getPic());
                    playingMusicBeens1.setRid(musicListBean.getRid());
                    playingMusicBeens.add(playingMusicBeens1);
                }
                PlayController.getInstance().setPlayList(playingMusicBeens, pos);
            }
        });
    }

}

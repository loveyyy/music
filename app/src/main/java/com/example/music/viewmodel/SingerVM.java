package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.ArtistAlbum;
import com.example.music.model.ArtistInfo;
import com.example.music.model.ArtistList;
import com.example.music.model.ArtistMusic;
import com.example.music.model.ArtistMv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-07
 */
public class SingerVM extends BaseVM {
    public MutableLiveData<BaseRespon<ArtistList>> artistList = new MutableLiveData<>();

    public SingerVM(@NonNull Application application) {
        super(application);
    }

    public void getArtistList(String category, String pn, String rn) {
        Api.getInstance().iRetrofit.artistList(category, pn, rn, getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<ArtistList>>() {
                    @Override
                    public void success(BaseRespon<ArtistList> data) {
                        artistList.setValue(data);
                    }
                });
    }


    public MutableLiveData<BaseRespon<ArtistInfo>> artistInfo = new MutableLiveData<>();

    public void getArtistInfo(String artistId) {
        Api.getInstance().iRetrofit.artistInfo(artistId, getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<ArtistInfo>>() {
                    @Override
                    public void success(BaseRespon<ArtistInfo> data) {
                        artistInfo.setValue(data);
                    }
                });
    }

    public MutableLiveData<BaseRespon<ArtistMusic>> artistMusic = new MutableLiveData<>();

    public void getArtistMusic(String artistId, String pn, String rn) {
        Api.getInstance().iRetrofit.artistMusic(artistId, pn, rn, getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<ArtistMusic>>() {
                    @Override
                    public void success(BaseRespon<ArtistMusic> data) {
                        artistMusic.setValue(data);
                    }
                });
    }

    public MutableLiveData<BaseRespon<ArtistAlbum>> artistAlbum = new MutableLiveData<>();

    public void getArtistAlbum(String artistId, String pn, String rn) {
        Api.getInstance().iRetrofit.artistAlbum(artistId, pn, rn, getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<ArtistAlbum>>() {
                    @Override
                    public void success(BaseRespon<ArtistAlbum> data) {
                        artistAlbum.setValue(data);
                    }
                });
    }

    public MutableLiveData<BaseRespon<ArtistMv>> artistMv = new MutableLiveData<>();

    public void getArtistMv(String artistId, String pn, String rn) {
        Api.getInstance().iRetrofit.artistMv(artistId, pn, rn, getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<ArtistMv>>() {
                    @Override
                    public void success(BaseRespon<ArtistMv> data) {
                        artistMv.setValue(data);
                    }
                });
    }


}

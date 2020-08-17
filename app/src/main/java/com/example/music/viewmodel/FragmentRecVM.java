package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.ArtistList;
import com.example.music.model.BangList;
import com.example.music.model.Banner;
import com.example.music.model.BaseRespon;
import com.example.music.model.MusicList;
import com.example.music.ui.base.BaseVM;

import java.util.List;

import io.reactivex.Observable;

/**
 * Create By morningsun  on 2019-11-29
 */
public class FragmentRecVM extends BaseVM {

    public MutableLiveData<List<Banner>> bannerList=new MutableLiveData<>();
    public MutableLiveData<MusicList> musicList=new MutableLiveData<>();
    public MutableLiveData<List<BangList>> bangList=new MutableLiveData<>();
    public MutableLiveData<ArtistList> artistList=new MutableLiveData<>();

    public FragmentRecVM(@NonNull Application application) {
        super(application);
    }



    @SuppressWarnings("unchecked")
    public void getRec(String loginUdi, String category, String pn, String rn){
        Observable.concatArrayDelayError(
                Api.getInstance().iRetrofit.bannerList(getaCache().getAsString("reqid")),
                Api.getInstance().iRetrofit.musicList(loginUdi,getaCache().getAsString("reqid")),
                Api.getInstance().iRetrofit.bangList(getaCache().getAsString("reqid")),
                Api.getInstance().iRetrofit.artistList(category,pn,rn,getaCache().getAsString("reqid"))
        ).compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon>() {
                    @Override
                    public void success(BaseRespon data) {
                        if(data.getData() instanceof List){
                            if(((List) data.getData()).get(0) instanceof Banner){
                                bannerList.setValue((List<Banner>) data.getData());
                            }
                            if(((List) data.getData()).get(0) instanceof BangList){
                                bangList.setValue((List<BangList>) data.getData());
                            }
                        }
                        if(data.getData() instanceof MusicList){
                            musicList.setValue((MusicList) data.getData());
                        }
                        if(data.getData() instanceof ArtistList){
                            artistList.setValue((ArtistList) data.getData());
                        }
                    }
                });
    }

}

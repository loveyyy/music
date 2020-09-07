package com.example.music.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.ArtistMv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.MvPlayActivity;
import com.example.music.ui.adapter.FragmentArtistMvApt;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2020-06-22
 */
public class FragmentMvVM extends BaseVM {

    public MutableLiveData<FragmentArtistMvApt> adapter =new MutableLiveData<>();
    public MutableLiveData<Intent> intentMutableLiveData =new MutableLiveData<>();
    public FragmentMvVM(@NonNull Application application) {
        super(application);
    }

    public void getMvList(String pid,String pn,String rn){
        Api.getInstance().iRetrofit.mvList(pid,pn,rn,"1",getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<ArtistMv>>() {
                    @Override
                    public void success(BaseRespon<ArtistMv> data) {
                        FragmentArtistMvApt fragmentArtist_mv_apt =new FragmentArtistMvApt(getApplication(),data.getData().getMvlist());
                        adapter.setValue(fragmentArtist_mv_apt);
                        fragmentArtist_mv_apt.setOnItemClick(new FragmentArtistMvApt.OnItemClick() {
                            @Override
                            public void OnItemClickListener(int pos) {
                                //播放mv
                                Intent intent=new Intent();
                                intent.setClass(getApplication(), MvPlayActivity.class);
                                intent.putExtra("rid",data.getData().getMvlist().get(pos).getId());
                                intentMutableLiveData.setValue(intent);
                            }
                        });
                    }
                });
    }
}

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
import com.example.music.model.BaseRespon;
import com.example.music.model.RecList;
import com.example.music.ui.activity.RecListInfoActivity;
import com.example.music.ui.adapter.FragmentMusicApt;
import com.example.music.ui.base.BaseVM;

/**
 * Create By morningsun  on 2019-12-10
 */
public class FragmentMusicVM extends BaseVM {
    public MutableLiveData<FragmentMusicApt> gv_music_aptMutableLiveData =new MutableLiveData<>();

    public MutableLiveData<Intent> intentMutableLiveData =new MutableLiveData<>();
    public FragmentMusicVM(@NonNull Application application) {
        super(application);
    }

    public void getMusicListMore(String loginUid,String loginSid, String pn, String rn, String order){
        Api.getInstance().iRetrofit.musicListMore(loginUid,loginSid,pn,rn,order,getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<RecList>>() {
                    @Override
                    public void success(BaseRespon<RecList> data) {
                        FragmentMusicApt gv_music_apt=new FragmentMusicApt(getApplication(),data.getData().getData());
                        gv_music_aptMutableLiveData.setValue(gv_music_apt);
                        gv_music_apt.setOnItemClick(new FragmentMusicApt.OnItemClick() {
                            @Override
                            public void OnItemClickListener(int pos) {
                                Intent intent=new Intent();
                                intent.setClass(getApplication(), RecListInfoActivity.class);
                                intent.putExtra("rid",data.getData().getData().get(pos).getId());
                                intentMutableLiveData.setValue(intent);
                            }
                        });

                    }
                });
    }



}

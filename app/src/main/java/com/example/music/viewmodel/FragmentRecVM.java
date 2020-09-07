package com.example.music.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.ArtistList;
import com.example.music.model.BangList;
import com.example.music.model.Banner;
import com.example.music.model.BaseRespon;
import com.example.music.model.MusicList;
import com.example.music.ui.activity.BangMenuActivity;
import com.example.music.ui.activity.RecListInfoActivity;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Bang_MeauAdapter;
import com.example.music.ui.adapter.GridViewAdapter;
import com.example.music.ui.adapter.Music_ListAdapter;
import com.example.music.ui.base.BaseVM;

import java.util.List;

import io.reactivex.Observable;

/**
 * Create By morningsun  on 2019-11-29
 */
public class FragmentRecVM extends BaseVM {

    public MutableLiveData<List<Banner>> bannerList=new MutableLiveData<>();


    public MutableLiveData<Music_ListAdapter> musicAdapter=new MutableLiveData<>();
    public MutableLiveData<Bang_MeauAdapter> bangAdapter=new MutableLiveData<>();
    public MutableLiveData<GridViewAdapter> artistAdapter=new MutableLiveData<>();

    public MutableLiveData<Intent> intentMutableLiveData=new MutableLiveData<>();

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
                                List<BangList> bangList =(List<BangList>) data.getData();
                                Bang_MeauAdapter bang_meauAdapter=new Bang_MeauAdapter(getApplication(),bangList);
                                bangAdapter.setValue(bang_meauAdapter);

                                bang_meauAdapter.setOnItemClick(new Bang_MeauAdapter.OnItemClick() {
                                    @Override
                                    public void OnItemClickListener(int pos) {
                                        //排行歌曲点击事件--进入排行详情
                                        Intent intent=new Intent();
                                        intent.putExtra("title",bangList.get(pos).getName());
                                        intent.putExtra("time",bangList.get(pos).getPub());
                                        intent.putExtra("bangid",bangList.get(pos).getId());
                                        intent.putExtra("img",bangList.get(pos).getPic());
                                        intent.setClass(getApplication(), BangMenuActivity.class);
                                        intentMutableLiveData.setValue(intent);
                                    }
                                });
                            }
                        }
                        if(data.getData() instanceof MusicList){
                            MusicList musicList =(MusicList) data.getData();
                            Music_ListAdapter music_listAdapter=new Music_ListAdapter(getApplication(), musicList);
                            musicAdapter.setValue(music_listAdapter);
                            music_listAdapter.setOnItemClick(new Music_ListAdapter.OnItemClick() {
                                @Override
                                public void OnItemClickListener(int pos) {
                                    //推荐歌单点击事件
                                    Intent intent=new Intent();
                                    intent.setClass(getApplication(), RecListInfoActivity.class);
                                    intent.putExtra("rid",musicList.getList().get(pos).getId().toString());
                                    intentMutableLiveData.setValue(intent);
                                }
                            });
                        }
                        if(data.getData() instanceof ArtistList){
                            ArtistList artistList=(ArtistList) data.getData();
                            GridViewAdapter gridViewAdapter=new GridViewAdapter(getApplication(),artistList);
                            artistAdapter.setValue(gridViewAdapter);
                            gridViewAdapter.setOnItemClick(new GridViewAdapter.OnItemClick() {
                                @Override
                                public void OnItemClickListener(int pos) {
                                    //歌手Item点击事件
                                    Intent intent=new Intent();
                                    intent.putExtra("artistid",artistList.getArtistList().get(pos).getId());
                                    intent.setClass(getApplication(), Singer_Activity.class);
                                    intentMutableLiveData.setValue(intent);
                                }
                            });


                        }
                    }
                });
    }

}

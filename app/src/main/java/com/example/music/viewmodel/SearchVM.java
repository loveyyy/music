package com.example.music.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.BaseRespon;
import com.example.music.model.Search;
import com.example.music.ui.base.BaseVM;

import java.util.List;

/**
 * Create By morningsun  on 2020-06-24
 */
public class SearchVM extends BaseVM {
    public MutableLiveData<BaseRespon<Search>> search=new MutableLiveData<>();
    public MutableLiveData<BaseRespon<List<String>>> keyList=new MutableLiveData<>();

    public SearchVM(@NonNull Application application) {
        super(application);
    }
    public void searchMusic(String key){
        Api.getInstance().iRetrofit.searchMusic(key,
                "1","30",getaCache().getAsString("reqid"),
                "http://www.kuwo.cn/search/list?key=%E8%B5%A4%E4%BC%B6")
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<Search>>() {
                    @Override
                    public void success(BaseRespon<Search> data) {
                        search.setValue(data);
                    }

                });
    }

    public void searchKey(String key){
        Api.getInstance().iRetrofit.searchKey(key,
               getaCache().getAsString("reqid"),"http://www.kuwo.cn/search/list?key=%E8%B5%A4%E4%BC%B6")
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<List<String>>>() {
                    @Override
                    public void success(BaseRespon<List<String>> data) {
                        keyList.setValue(data);
                    }

                });
    }

}

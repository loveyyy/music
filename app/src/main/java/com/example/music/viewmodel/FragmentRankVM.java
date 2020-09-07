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
import com.example.music.model.BangMenu;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.BangMenuActivity;
import com.example.music.ui.adapter.Bang_Meau_NameApt;
import com.example.music.ui.base.BaseVM;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class FragmentRankVM extends BaseVM {

    public MutableLiveData<Bang_Meau_NameApt> adapter=new MutableLiveData<>();
    public MutableLiveData<Intent> intentMutableLiveData=new MutableLiveData<>();

    public FragmentRankVM(@NonNull Application application) {
        super(application);
    }

    public void getBangMenu(){
        Api.getInstance().iRetrofit.bangMenu(getaCache().getAsString("reqid"))
                .compose(RxHelper.observableIO2Main(getApplication()))
                .subscribe(new ApiResponse<BaseRespon<List<BangMenu>>>() {
                    @Override
                    public void success(BaseRespon<List<BangMenu>> data) {
                        Bang_Meau_NameApt bang_meau_nameApt=new Bang_Meau_NameApt(getApplication(),data.getData());
                        adapter.setValue(bang_meau_nameApt);
                        bang_meau_nameApt.setOnItemClick(new Bang_Meau_NameApt.OnItemClick() {
                            @Override
                            public void OnItemClickListener(int pos, int pos1) {
                                Intent intent=new Intent();
                                intent.putExtra("title",data.getData().get(pos).getList().get(pos1).getName());
                                intent.putExtra("time",data.getData().get(pos).getList().get(pos1).getPub());
                                intent.putExtra("bangid",data.getData().get(pos).getList().get(pos1).getSourceid());
                                intent.putExtra("img",data.getData().get(pos).getList().get(pos1).getPic());
                                intent.setClass(getApplication(), BangMenuActivity.class);
                                intentMutableLiveData.setValue(intent);
                            }
                        });
                    }
                });
    }
}

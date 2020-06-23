package com.example.music.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.RecListInfoActivityBinding;
import com.example.music.model.BaseRespon;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.model.Rec_List_Info;
import com.example.music.ui.adapter.BaseAdapter;
import com.example.music.ui.base.BaseActivity;
import com.example.music.ui.custom.CustomDialogFragment;
import com.example.music.ui.custom.PlayerView;
import com.example.music.viewmodel.Rec_VM;

import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2019-12-10
 */
public class Rec_List_Info_Activity extends BaseActivity<RecListInfoActivityBinding,Rec_VM> implements   PlayerView.showList{
    private RecListInfoActivityBinding recListInfoActivityBinding;
    private Rec_VM rec_vm;

    @Override
    public int getLayout() {
        return R.layout.rec_list_info_activity;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(RecListInfoActivityBinding bindView) {
        recListInfoActivityBinding=bindView;
        recListInfoActivityBinding.playview.SetShowList(this);
    }

    @Override
    protected void setVM(Rec_VM vm) {
        rec_vm=vm;
        rec_vm.rec_list_info.observe(this, new Observer<BaseRespon<Rec_List_Info>>() {
            @Override
            public void onChanged(final BaseRespon<Rec_List_Info> rec_list_infoBaseRespon) {
                try {
                    Glide.with(getBaseContext()).load(rec_list_infoBaseRespon.getData().getImg()).into(recListInfoActivityBinding.ivRec);
                    recListInfoActivityBinding.ivRecName.setText(rec_list_infoBaseRespon.getData().getName());
                    recListInfoActivityBinding.ivRecInfo.setText(rec_list_infoBaseRespon.getData().getDesc());
                    recListInfoActivityBinding.ivRecInfo1.setText(rec_list_infoBaseRespon.getData().getInfo());

                    recListInfoActivityBinding.rcvRec.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
                    BaseAdapter<Rec_List_Info.MusicListBean> beanBaseAdapter=new BaseAdapter<>(getBaseContext(),rec_list_infoBaseRespon.getData().getMusicList(),R.layout.rcv_rec_list_apt, BR.rec_list_info);
                    recListInfoActivityBinding.rcvRec.setAdapter(beanBaseAdapter);
                    beanBaseAdapter.setOnItemClick(new BaseAdapter.OnItemClick() {
                        @Override
                        public void OnItemClickListener(int pos) {
                            ArrayList<PlayingMusicBeens> playingMusicBeens = new ArrayList<>();
                            for (Rec_List_Info.MusicListBean listBean : rec_list_infoBaseRespon.getData().getMusicList()) {
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
                            recListInfoActivityBinding.playview.play(playingMusicBeens,pos);
                        }
                    });


                }catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"请返回重试",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void setListener() {

    }

    protected void initData() {
        Intent intent=getIntent();
        rec_vm.Get_rec_list_info(intent.getStringExtra("rid"),"1","30");
    }


    @Override
    public void OnShowList(List<PlayingMusicBeens> playingMusicBeens) {
        CustomDialogFragment customDialogFragment=new CustomDialogFragment(playingMusicBeens,getApplicationContext());
        customDialogFragment.show(getSupportFragmentManager(),"RecListInfo");
    }
}

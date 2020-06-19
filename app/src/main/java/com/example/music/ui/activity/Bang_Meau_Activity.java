package com.example.music.ui.activity;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.BangMeauActivityBinding;
import com.example.music.model.Bang_Music_list;
import com.example.music.model.BaseRespon;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.ui.adapter.BaseAdapter;
import com.example.music.ui.base.BaseActivity;
import com.example.music.viewmodel.Bang_Meau_Vm;

import java.util.ArrayList;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Bang_Meau_Activity extends BaseActivity<BangMeauActivityBinding,Bang_Meau_Vm> {
    private BangMeauActivityBinding bangMeauActivityBinding;
    private Bang_Meau_Vm bang_meau_vm;


    @Override
    public int getLayout() {
        return R.layout.bang_meau_activity;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(BangMeauActivityBinding bindView) {
        bangMeauActivityBinding=bindView;

    }

    @Override
    protected void setVM(Bang_Meau_Vm vm) {
        bang_meau_vm=vm;
        bang_meau_vm.Bang_Music_list.observe(this, new Observer<BaseRespon<Bang_Music_list>>() {
            @Override
            public void onChanged(final BaseRespon<Bang_Music_list> listBaseRespon) {
                try {
                    bangMeauActivityBinding.rcvBangMeauList.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false));
                    BaseAdapter<Bang_Music_list.MusicListBean> beanBaseAdapter=new BaseAdapter<>(getBaseContext(),listBaseRespon.getData().getMusicList(),R.layout.music_item_apt, BR.bangMusicList);
//                    final Music_ItemAdapter music_itemAdapter = new Music_ItemAdapter(getBaseContext(), listBaseRespon.getData().getMusicList());
                    bangMeauActivityBinding.setBangMeauAdapter(beanBaseAdapter);
                    beanBaseAdapter.setOnItemClick(new BaseAdapter.OnItemClick() {
                        @Override
                        public void OnItemClickListener(int pos) {
                            ArrayList<PlayingMusicBeens> playingMusicBeens = new ArrayList<>();
                            for (Bang_Music_list.MusicListBean musicListBean : listBaseRespon.getData().getMusicList()) {
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
                            bangMeauActivityBinding.playview.play(playingMusicBeens, pos);
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

    @Override
    protected void initData() {
        Intent intent = getIntent();
        bangMeauActivityBinding.tvRankTitle.setText(intent.getStringExtra("title"));
        bangMeauActivityBinding.tvRankTime.setText(intent.getStringExtra("time"));
        Glide.with(this).load(intent.getStringExtra("img")).into(bangMeauActivityBinding.ivTop);
        bang_meau_vm.Get_Bang_Music_list(intent.getStringExtra("bangid"), "1", "30", "ec08f70-159e-11ea-adf3-798fb627bfc6");
    }
}

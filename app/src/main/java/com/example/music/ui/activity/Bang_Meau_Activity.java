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
import com.example.music.R;
import com.example.music.ui.adapter.Music_ItemAdapter;
import com.example.music.viewmodel.Bang_Meau_Vm;
import com.example.music.databinding.BangMeauActivityBinding;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.model.Bang_Music_list;
import com.example.music.model.BaseRespon;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Bang_Meau_Activity extends AppCompatActivity {
    private BangMeauActivityBinding bangMeauActivityBinding;
    private Bang_Meau_Vm bang_meau_vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bangMeauActivityBinding = DataBindingUtil.setContentView(this, R.layout.bang_meau_activity);
        bang_meau_vm = ViewModelProviders.of(this).get(Bang_Meau_Vm.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        setVM();
        initdata();
    }

    private void setVM() {
        bang_meau_vm.Bang_Music_list.observe(this, new Observer<BaseRespon<Bang_Music_list>>() {
            @Override
            public void onChanged(final BaseRespon<Bang_Music_list> listBaseRespon) {
                try {
                    bangMeauActivityBinding.rcvBangMeauList.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false));
                    final Music_ItemAdapter music_itemAdapter = new Music_ItemAdapter(getBaseContext(), listBaseRespon.getData().getMusicList());
                    bangMeauActivityBinding.rcvBangMeauList.setAdapter(music_itemAdapter);

                    music_itemAdapter.setOnItemClick(new Music_ItemAdapter.OnItemClick() {
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

    private void initdata() {
        Intent intent = getIntent();
        bangMeauActivityBinding.tvRankTitle.setText(intent.getStringExtra("title"));
        bangMeauActivityBinding.tvRankTime.setText(intent.getStringExtra("time"));
        Glide.with(this).load(intent.getStringExtra("img")).into(bangMeauActivityBinding.ivTop);
        bang_meau_vm.Get_Bang_Music_list(this, intent.getStringExtra("bangid"), "1", "30", "ec08f70-159e-11ea-adf3-798fb627bfc6");
    }

}

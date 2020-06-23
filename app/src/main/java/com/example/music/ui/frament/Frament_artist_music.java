package com.example.music.ui.frament;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.Interface.PlayMusic;
import com.example.music.R;
import com.example.music.databinding.FramentArtistMusicBinding;
import com.example.music.model.Artist_Music;
import com.example.music.model.Artist_list;
import com.example.music.model.Bang_Music_list;
import com.example.music.model.BaseRespon;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Frament_artist_music_Apt;
import com.example.music.viewmodel.Frament_Rank_VM;
import com.example.music.viewmodel.Singer_VM;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Frament_artist_music extends Fragment {
    private FramentArtistMusicBinding framentArtistMusicBinding;
    private Singer_VM singer_vm;
    private int artistid;
    private PlayMusic playMusic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        framentArtistMusicBinding= DataBindingUtil.inflate(inflater, R.layout.frament_artist_music,container,false);
        singer_vm = ViewModelProviders.of(this).get(Singer_VM.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0, null);
        SetVM();
        initdata();
        return framentArtistMusicBinding.getRoot();
    }

    private void SetVM() {
        singer_vm.Artist_Music.observe(this, new Observer<BaseRespon<Artist_Music>>() {
            @Override
            public void onChanged(final BaseRespon<Artist_Music> artist_listBaseRespon) {
                framentArtistMusicBinding.rcvArtistMusic.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
                Frament_artist_music_Apt frament_artist_music_apt=new Frament_artist_music_Apt(getContext(),artist_listBaseRespon.getData().getList());
                framentArtistMusicBinding.rcvArtistMusic.setAdapter(frament_artist_music_apt);
                frament_artist_music_apt.setOnItemClick(new Frament_artist_music_Apt.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        ArrayList<PlayingMusicBeens> playingMusicBeens = new ArrayList<>();
                        for (Artist_Music.ListBean listBean : artist_listBaseRespon.getData().getList()) {
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
                        playMusic.Play(playingMusicBeens,pos);
                    }
                });

            }
        });
    }

    private void initdata() {
        singer_vm.Get_Artist_Music(String.valueOf(artistid),"1","30");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        artistid = ((Singer_Activity) context).getartistid();
        playMusic= (PlayMusic) getActivity();
    }
}

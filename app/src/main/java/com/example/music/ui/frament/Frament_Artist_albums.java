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

import com.example.music.R;
import com.example.music.databinding.FramentArtistAlbumsBinding;
import com.example.music.databinding.FramentArtistMusicBinding;
import com.example.music.model.Artist_Album;
import com.example.music.model.Artist_Music;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Frament_artist_music_Apt;
import com.example.music.ui.adapter.Gv_artist_albums_apt;
import com.example.music.viewmodel.Singer_VM;
import com.jaeger.library.StatusBarUtil;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Frament_Artist_albums extends Fragment {
    private FramentArtistAlbumsBinding framentArtistAlbumsBinding;
    private Singer_VM singer_vm;
    private int artistid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        framentArtistAlbumsBinding= DataBindingUtil.inflate(inflater, R.layout.frament_artist_albums,container,false);
        singer_vm = ViewModelProviders.of(this).get(Singer_VM.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0, null);
        SetVM();
        initdata();
        return framentArtistAlbumsBinding.getRoot();
    }

    private void SetVM() {
        singer_vm.Artist_Album.observe(this, new Observer<BaseRespon<Artist_Album>>() {
            @Override
            public void onChanged(BaseRespon<Artist_Album> artist_listBaseRespon) {
                Gv_artist_albums_apt gv_artist_albums_apt=new Gv_artist_albums_apt(getContext(),artist_listBaseRespon.getData().getAlbumList());
                framentArtistAlbumsBinding.gvArtistAlbums.setAdapter(gv_artist_albums_apt);
            }
        });
    }

    private void initdata() {
        singer_vm.Get_Artist_Album(String.valueOf(artistid),"1","30");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        artistid = ((Singer_Activity) context).getartistid();
    }
}

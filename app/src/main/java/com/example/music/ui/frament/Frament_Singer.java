package com.example.music.ui.frament;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.music.R;
import com.example.music.databinding.FramentSingerBinding;
import com.example.music.model.Artist_list;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Gv_Singer_apt;
import com.example.music.viewmodel.Frament_Rank_VM;
import com.example.music.viewmodel.Singer_VM;
import com.jaeger.library.StatusBarUtil;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Frament_Singer extends Fragment {
    private FramentSingerBinding framentSingerBinding;
    private Singer_VM singer_vm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        framentSingerBinding= DataBindingUtil.inflate(inflater, R.layout.frament_singer,container,false);
        singer_vm = ViewModelProviders.of(this).get(Singer_VM.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0, null);
        SetVM();
        initdata();
        return framentSingerBinding.getRoot();
    }

    private void SetVM() {
        singer_vm.Artist_list.observe(this, new Observer<BaseRespon<Artist_list>>() {
            @Override
            public void onChanged(final BaseRespon<Artist_list> artist_listBaseRespon) {
                Gv_Singer_apt gv_singer_apt=new Gv_Singer_apt(getContext(),artist_listBaseRespon.getData().getArtistList());
                framentSingerBinding.gvSinger.setAdapter(gv_singer_apt);
                framentSingerBinding.gvSinger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent();
                        intent.putExtra("artistid",artist_listBaseRespon.getData().getArtistList().get(i).getId());
                        intent.setClass(getContext(), Singer_Activity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void initdata() {
        singer_vm.Get_Artist_list(getContext(),"0","1","100","d8bbee10-1895-11ea-b707-4b7b2a81b695");
    }
}

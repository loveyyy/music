package com.example.music.ui.frament;

import android.content.Intent;
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
import com.example.music.ui.activity.Bang_Meau_Activity;
import com.example.music.ui.activity.Rec_List_Info_Activity;
import com.example.music.ui.activity.Singer_Activity;
import com.example.music.ui.adapter.Bang_MeauAdapter;
import com.example.music.ui.adapter.GridViewAdapter;
import com.example.music.ui.adapter.Music_ListAdapter;
import com.example.music.viewmodel.Frament_Rec_VM;
import com.example.music.databinding.FramentRecBinding;
import com.example.music.model.Artist_list;
import com.example.music.model.Bananer;
import com.example.music.model.Bang_list;
import com.example.music.model.BaseRespon;
import com.example.music.model.Music_list;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Frament_Rec extends Fragment {
    private Frament_Rec_VM framentRecVM;
    private FramentRecBinding framentRecBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        framentRecBinding= DataBindingUtil.inflate(inflater,R.layout.frament_rec,container,false);
        framentRecVM = ViewModelProviders.of(this).get(Frament_Rec_VM.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0, null);
        setVM();
        initdata();
        return framentRecBinding.getRoot();
    }

    private void initdata() {
        framentRecVM.Get_Bananer_list("ec08f70-159e-11ea-adf3-798fb627bfc6");
    }

    private void setVM(){
        framentRecVM.Music_list.observe(this, new Observer<BaseRespon<Music_list>>() {
            @Override
            public void onChanged(final BaseRespon<Music_list> listBaseRespon) {
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
                framentRecBinding.rcvMusicListMain.setLayoutManager(layoutManager);
                Music_ListAdapter music_listAdapter=new Music_ListAdapter(getContext(),listBaseRespon.getData());
                music_listAdapter.setOnItemClick(new Music_ListAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        //推荐歌单点击事件
                        Intent intent=new Intent();
                        intent.setClass(getContext(), Rec_List_Info_Activity.class);
                        intent.putExtra("rid",listBaseRespon.getData().getList().get(pos).getId().toString());
                        startActivity(intent);
                    }
                });
                framentRecBinding.rcvMusicListMain.setAdapter(music_listAdapter);
            }
        });
        framentRecVM.Bananer_list.observe(this, new Observer<BaseRespon<List<Bananer>>>() {
            @Override
            public void onChanged(BaseRespon<List<Bananer>> bananerBaseRespon) {
                framentRecBinding.customMain.initdata(bananerBaseRespon.getData());
                framentRecBinding.customMain.start();
                framentRecVM.Get_Bang_list("ec08f70-159e-11ea-adf3-798fb627bfc6");
            }
        });
        framentRecVM.Bang_meau.observe(this, new Observer<BaseRespon<List<Bang_list>>>() {
            @Override
            public void onChanged(final BaseRespon<List<Bang_list>> listBaseRespon) {
                framentRecBinding.rcvRankMain.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                Bang_MeauAdapter bang_meauAdapter=new Bang_MeauAdapter(getContext(),listBaseRespon.getData());
                framentRecBinding.rcvRankMain.setAdapter(bang_meauAdapter);
                bang_meauAdapter.setOnItemClick(new Bang_MeauAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        //排行歌曲点击事件--进入排行详情
                        Intent intent=new Intent();
                        intent.putExtra("title",listBaseRespon.getData().get(pos).getName());
                        intent.putExtra("time",listBaseRespon.getData().get(pos).getPub());
                        intent.putExtra("bangid",listBaseRespon.getData().get(pos).getId());
                        intent.putExtra("img",listBaseRespon.getData().get(pos).getPic());
                        intent.setClass(getContext(), Bang_Meau_Activity.class);
                        startActivity(intent);
                    }
                });
                framentRecVM.Get_Artist_list(getContext(),"11","1","6","ec08f70-159e-11ea-adf3-798fb627bfc6");
            }
        });
        framentRecVM.Artist_list.observe(this, new Observer<BaseRespon<Artist_list>>() {
            @Override
            public void onChanged(final BaseRespon<Artist_list> artist_listBaseRespon) {
                framentRecBinding.rcvSingerMain.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                GridViewAdapter gridViewAdapter=new GridViewAdapter(getContext(),artist_listBaseRespon.getData());
                framentRecBinding.rcvSingerMain.setAdapter(gridViewAdapter);
                gridViewAdapter.setOnItemClick(new GridViewAdapter.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos) {
                        //歌手Item点击事件
                        Intent intent=new Intent();
                        intent.putExtra("artistid",artist_listBaseRespon.getData().getArtistList().get(pos).getId());
                        intent.setClass(getContext(), Singer_Activity.class);
                        startActivity(intent);
                    }
                });
                framentRecVM.Get_Music_list("ec08f70-159e-11ea-adf3-798fb627bfc6","0");
            }
        });
    }

}

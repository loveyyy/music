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
import com.example.music.databinding.FramentMusicBinding;
import com.example.music.model.BaseRespon;
import com.example.music.model.Rec_List;
import com.example.music.ui.activity.Rec_List_Info_Activity;
import com.example.music.ui.adapter.Gv_Music_apt;
import com.example.music.viewmodel.Frament_Music_VM;
import com.jaeger.library.StatusBarUtil;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Frament_Music extends Fragment {
    private Frament_Music_VM frament_music_vm;
    private FramentMusicBinding framentMusicBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        framentMusicBinding= DataBindingUtil.inflate(inflater, R.layout.frament_music,container,false);
        frament_music_vm = ViewModelProviders.of(this).get(Frament_Music_VM.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0, null);
        setVM();
        initdata();
        return framentMusicBinding.getRoot();
    }

    private void initdata() {
        frament_music_vm.Get_Music_list_more(getContext(),"1857627817","3255031405","1","30","new","ec08f70-159e-11ea-adf3-798fb627bfc6");
    }

    private void setVM(){
        frament_music_vm.Music_list_more.observe(this, new Observer<BaseRespon<Rec_List>>() {
            @Override
            public void onChanged(final BaseRespon<Rec_List> listBaseRespon) {
                Gv_Music_apt gv_music_apt=new Gv_Music_apt(getContext(),listBaseRespon.getData().getData());
                framentMusicBinding.gvMusic.setAdapter(gv_music_apt);

                framentMusicBinding.gvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent();
                        intent.setClass(getContext(), Rec_List_Info_Activity.class);
                        intent.putExtra("rid",listBaseRespon.getData().getData().get(i).getId());
                        startActivity(intent);
                    }
                });
            }
        });
    }


}

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
import com.example.music.ui.adapter.Bang_Meau_NameApt;
import com.example.music.viewmodel.Frament_Rank_VM;
import com.example.music.databinding.FramentRankBinding;
import com.example.music.model.Bang_meau;
import com.example.music.model.BaseRespon;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Framen_Rank extends Fragment {
    private FramentRankBinding framentRankBinding;
    private Frament_Rank_VM bang_meau_vm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        framentRankBinding= DataBindingUtil.inflate(inflater, R.layout.frament_rank,container,false);
        bang_meau_vm = ViewModelProviders.of(this).get(Frament_Rank_VM.class);
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(),0, null);
        SetVM();
        initdata();
        return framentRankBinding.getRoot();
    }
    private void SetVM() {
        bang_meau_vm.Bang_Menu.observe(this, new Observer<BaseRespon<List<Bang_meau>>>() {
            @Override
            public void onChanged(final BaseRespon<List<Bang_meau>> listBaseRespon) {
                framentRankBinding.rcvBangMeauName.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
                Bang_Meau_NameApt bang_meau_nameApt=new Bang_Meau_NameApt(getContext(),listBaseRespon.getData());
                framentRankBinding.rcvBangMeauName.setAdapter(bang_meau_nameApt);
                bang_meau_nameApt.setOnItemClick(new Bang_Meau_NameApt.OnItemClick() {
                    @Override
                    public void OnItemClickListener(int pos, int pos1) {
                        Intent intent=new Intent();
                        intent.putExtra("title",listBaseRespon.getData().get(pos).getList().get(pos1).getName());
                        intent.putExtra("time",listBaseRespon.getData().get(pos).getList().get(pos1).getPub());
                        intent.putExtra("bangid",listBaseRespon.getData().get(pos).getList().get(pos1).getSourceid());
                        intent.putExtra("img",listBaseRespon.getData().get(pos).getList().get(pos1).getPic());
                        intent.setClass(getContext(), Bang_Meau_Activity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }


    private void initdata() {
        bang_meau_vm.Get_Bang_Menu(getContext(),"ec08f70-159e-11ea-adf3-798fb627bfc6");
    }
}

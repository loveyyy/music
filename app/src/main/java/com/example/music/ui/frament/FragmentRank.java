package com.example.music.ui.frament;

import android.content.Intent;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.R;
import com.example.music.model.BangMenu;
import com.example.music.ui.activity.BangMenuActivity;
import com.example.music.ui.adapter.Bang_Meau_NameApt;
import com.example.music.ui.base.BaseFragment;
import com.example.music.viewmodel.FragmentRankVM;
import com.example.music.databinding.FramentRankBinding;
import com.example.music.model.BaseRespon;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class FragmentRank extends BaseFragment<FramentRankBinding,FragmentRankVM> {
    private FramentRankBinding framentRankBinding;
    private FragmentRankVM fragmentRankVM;

    @Override
    protected int getContentViewId() {
        return R.layout.frament_rank;
    }

    @Override
    protected void initView(FramentRankBinding bindView) {
        framentRankBinding=bindView;
    }

    @Override
    protected void SetVM(FragmentRankVM vm) {
        fragmentRankVM=vm;

        fragmentRankVM.Bang_Menu.observe(this, new Observer<BaseRespon<List<BangMenu>>>() {
            @Override
            public void onChanged(final BaseRespon<List<BangMenu>> listBaseRespon) {
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
                        intent.setClass(getContext(), BangMenuActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void setOnClick() {

    }

    @Override
    protected void initData() {
        fragmentRankVM.getBangMenu();
    }

    @Override
    protected boolean getNeedRefresh() {
        return false;
    }
}

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
        framentRankBinding.setFragmentRankVM(fragmentRankVM);

        fragmentRankVM.intentMutableLiveData.observe(this, new Observer<Intent>() {
            @Override
            public void onChanged(Intent intent) {
                startActivity(intent);
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

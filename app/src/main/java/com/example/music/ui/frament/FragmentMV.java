package com.example.music.ui.frament;

import android.content.Intent;

import androidx.lifecycle.Observer;

import com.example.music.R;
import com.example.music.databinding.FramentMvBinding;
import com.example.music.ui.base.BaseFragment;
import com.example.music.viewmodel.FragmentMvVM;

/**
 * Create By morningsun  on 2019-12-05
 */
public class FragmentMV extends BaseFragment<FramentMvBinding, FragmentMvVM> {
    private FramentMvBinding framentMvBinding;
    private FragmentMvVM fragmentMvVM;


    @Override
    public int getContentViewId() {
        return R.layout.frament_mv;
    }

    @Override
    protected void initView(FramentMvBinding bindView) {
        framentMvBinding=bindView;
    }

    @Override
    protected void SetVM(FragmentMvVM vm) {
        fragmentMvVM=vm;
        framentMvBinding.setFragmentMv(fragmentMvVM);

        fragmentMvVM.intentMutableLiveData.observe(this, new Observer<Intent>() {
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
        fragmentMvVM.getMvList("236682871","1","30");
    }



    @Override
    protected boolean getNeedRefresh() {
        return false;
    }


}

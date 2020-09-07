package com.example.music.ui.frament;

import android.content.Intent;

import androidx.lifecycle.Observer;

import com.example.music.R;
import com.example.music.databinding.FramentMusicBinding;
import com.example.music.ui.base.BaseFragment;
import com.example.music.viewmodel.FragmentMusicVM;

/**
 * Create By morningsun  on 2019-12-05
 */
public class FragmentMusic extends BaseFragment<FramentMusicBinding,FragmentMusicVM> {
    private FragmentMusicVM fragmentMusicVM;
    private FramentMusicBinding framentMusicBinding;

    @Override
    protected int getContentViewId() {
        return R.layout.frament_music;
    }

    @Override
    protected void initView(FramentMusicBinding bindView) {
        framentMusicBinding=bindView;
    }

    @Override
    protected void SetVM(FragmentMusicVM vm) {
        fragmentMusicVM=vm;
        framentMusicBinding.setFragmentMusicVM(fragmentMusicVM);
        fragmentMusicVM.intentMutableLiveData.observe(this, new Observer<Intent>() {
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
        fragmentMusicVM.getMusicListMore("1857627817","3255031405","1","30","new");
    }

    @Override
    protected boolean getNeedRefresh() {
        return false;
    }



}

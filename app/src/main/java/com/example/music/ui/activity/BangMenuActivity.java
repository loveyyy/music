package com.example.music.ui.activity;

import android.content.Intent;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.music.R;
import com.example.music.databinding.BangMeauActivityBinding;
import com.example.music.ui.base.BaseActivity;
import com.example.music.viewmodel.BangMenuVm;

/**
 * Create By morningsun  on 2019-12-05
 */
public class BangMenuActivity extends BaseActivity<BangMeauActivityBinding, BangMenuVm> {
    private BangMeauActivityBinding bangMeauActivityBinding;
    private BangMenuVm bang_meau_vm;


    @Override
    public int getLayout() {
        return R.layout.bang_meau_activity;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(BangMeauActivityBinding bindView) {
        bangMeauActivityBinding=bindView;
        ViewModel viewModel = new ViewModelProvider(this).get(BangMenuVm.class);
    }

    @Override
    protected void setVM(BangMenuVm vm) {
        bang_meau_vm=vm;
        bangMeauActivityBinding.setBangMeauVM(bang_meau_vm);
    }


    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        bangMeauActivityBinding.tvRankTitle.setText(intent.getStringExtra("title"));
        bangMeauActivityBinding.tvRankTime.setText(intent.getStringExtra("time"));
        Glide.with(this).load(intent.getStringExtra("img")).into(bangMeauActivityBinding.ivTop);
        bang_meau_vm.getBangMusicList(intent.getStringExtra("bangid"), "1", "30");
    }
}

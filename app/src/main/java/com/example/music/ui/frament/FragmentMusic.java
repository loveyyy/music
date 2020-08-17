package com.example.music.ui.frament;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.Observer;

import com.example.music.R;
import com.example.music.databinding.FramentMusicBinding;
import com.example.music.model.BaseRespon;
import com.example.music.model.RecList;
import com.example.music.ui.activity.RecListInfoActivity;
import com.example.music.ui.adapter.Gv_Music_apt;
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

        fragmentMusicVM.musicListMore.observe(this, new Observer<BaseRespon<RecList>>() {
            @Override
            public void onChanged(final BaseRespon<RecList> listBaseRespon) {
                Gv_Music_apt gv_music_apt=new Gv_Music_apt(getContext(),listBaseRespon.getData().getData());
                framentMusicBinding.gvMusic.setAdapter(gv_music_apt);

                framentMusicBinding.gvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent();
                        intent.setClass(getContext(), RecListInfoActivity.class);
                        intent.putExtra("rid",listBaseRespon.getData().getData().get(i).getId());
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
        fragmentMusicVM.getMusicListMore("1857627817","3255031405","1","30","new");
    }

    @Override
    protected boolean getNeedRefresh() {
        return false;
    }



}

package com.example.music.ui.frament;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.Observer;

import com.example.music.R;
import com.example.music.databinding.FramentMvBinding;
import com.example.music.model.ArtistMv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.MvPlayActivity;
import com.example.music.ui.adapter.Gv_artist_Mv_apt;
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

        fragmentMvVM.mvList.observe(this, new Observer<BaseRespon<ArtistMv>>() {
            @Override
            public void onChanged(final BaseRespon<ArtistMv> mv_listBaseRespon) {
                Gv_artist_Mv_apt gv_artist_mv_apt=new Gv_artist_Mv_apt(getMContext(),mv_listBaseRespon.getData().getMvlist());
                framentMvBinding.gvMv.setAdapter(gv_artist_mv_apt);
                framentMvBinding.gvMv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //播放mv
                        Intent intent=new Intent();
                        intent.setClass(getActivity(), MvPlayActivity.class);
                        intent.putExtra("rid",mv_listBaseRespon.getData().getMvlist().get(position).getId());
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
        fragmentMvVM.getMvList("236682871","1","30");
    }



    @Override
    protected boolean getNeedRefresh() {
        return false;
    }


}

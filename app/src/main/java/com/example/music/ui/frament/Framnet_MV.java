package com.example.music.ui.frament;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.music.R;
import com.example.music.databinding.FramentMvBinding;
import com.example.music.model.Artist_Mv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.activity.MvPlayActivity;
import com.example.music.ui.adapter.Gv_artist_Mv_apt;
import com.example.music.ui.base.BaseFrament;
import com.example.music.viewmodel.FramentMv_VM;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Framnet_MV extends BaseFrament<FramentMvBinding> {
    private FramentMvBinding framentMvBinding;
    private FramentMv_VM framentMv_vm;


    @Override
    public int getContentViewId() {
        return R.layout.frament_mv;
    }

    @Override
    protected void initview(FramentMvBinding bindView) {
        framentMvBinding=bindView;
        framentMv_vm = ViewModelProviders.of(this).get(FramentMv_VM.class);
    }

    @Override
    protected void SetVM() {
        framentMv_vm.mv_list.observe(this, new Observer<BaseRespon<Artist_Mv>>() {
            @Override
            public void onChanged(final BaseRespon<Artist_Mv> mv_listBaseRespon) {
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
    protected void setonclick() {

    }

    @Override
    protected void initData() {
        framentMv_vm.Get_Mv_List("236682871","1","30");
    }


}

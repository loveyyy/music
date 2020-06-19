package com.example.music.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.R;
import com.example.music.databinding.RecListInfoActivityBinding;
import com.example.music.model.BaseRespon;
import com.example.music.model.Rec_List_Info;
import com.example.music.ui.adapter.Rcv_Rec_List_apt;
import com.example.music.viewmodel.Rec_VM;

/**
 * Create By morningsun  on 2019-12-10
 */
public class Rec_List_Info_Activity extends AppCompatActivity {
    private RecListInfoActivityBinding recListInfoActivityBinding;
    private Rec_VM rec_vm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recListInfoActivityBinding= DataBindingUtil.setContentView(this, R.layout.rec_list_info_activity);
        rec_vm= ViewModelProviders.of(this).get(Rec_VM.class);
        SetVM();
        initData();
    }
    private void SetVM() {
        rec_vm.rec_list_info.observe(this, new Observer<BaseRespon<Rec_List_Info>>() {
            @Override
            public void onChanged(BaseRespon<Rec_List_Info> rec_list_infoBaseRespon) {
                try {
                    Glide.with(getBaseContext()).load(rec_list_infoBaseRespon.getData().getImg()).into(recListInfoActivityBinding.ivRec);
                    recListInfoActivityBinding.ivRecName.setText(rec_list_infoBaseRespon.getData().getName());
                    recListInfoActivityBinding.ivRecInfo.setText(rec_list_infoBaseRespon.getData().getDesc());
                    recListInfoActivityBinding.ivRecInfo1.setText(rec_list_infoBaseRespon.getData().getInfo());

                    recListInfoActivityBinding.rcvRec.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL,false));
                    Rcv_Rec_List_apt rcv_rec_list_apt=new Rcv_Rec_List_apt(getBaseContext(),rec_list_infoBaseRespon.getData().getMusicList());
                    recListInfoActivityBinding.rcvRec.setAdapter(rcv_rec_list_apt);
                }catch (NullPointerException e){
                    Toast.makeText(getApplicationContext(),"请返回重试",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void initData() {
        Intent intent=getIntent();
        rec_vm.Get_rec_list_info(intent.getStringExtra("rid"),"1","30","bbf263b0-1b36-11ea-9235-b7165f37904a");
    }


}

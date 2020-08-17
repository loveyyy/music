package com.example.music.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.R;
import com.example.music.model.BangMenu;
import com.example.music.ui.bindadapter.BindingViewHolder;
import com.example.music.databinding.BangMeauNameaptBinding;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Bang_Meau_NameApt extends RecyclerView.Adapter<BindingViewHolder> {
    private Context context;
    private List<BangMenu> bang_menus;
    private BangMeauNameaptBinding bangMeauNameaptBinding;
    private OnItemClick onItemClick;
    public Bang_Meau_NameApt(Context context, List<BangMenu> bang_menus){
        this.context=context;
        this.bang_menus = bang_menus;
    }
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        bangMeauNameaptBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.bang_meau_nameapt,parent,false);
        return new BindingViewHolder(bangMeauNameaptBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, final int position) {
        bangMeauNameaptBinding.tvBangMeauName.setText(bang_menus.get(position).getName());
        bangMeauNameaptBinding.rcvBangMeauNameItem.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        Bang_Meau_Name_ItemApt bang_meau_name_itemApt=new Bang_Meau_Name_ItemApt(context, bang_menus.get(position).getList());
        bangMeauNameaptBinding.rcvBangMeauNameItem.setAdapter(bang_meau_name_itemApt);
        bang_meau_name_itemApt.setOnItemClick(new Bang_Meau_Name_ItemApt.OnItemClick() {
            @Override
            public void OnItemClickListener(int pos) {
                onItemClick.OnItemClickListener(position,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bang_menus.size();
    }

    //ItemView 的点击接口
    public interface OnItemClick{
        void OnItemClickListener(int pos,int pos1);
    }
    public void setOnItemClick(Bang_Meau_NameApt.OnItemClick click){
        this.onItemClick=click;
    }
}

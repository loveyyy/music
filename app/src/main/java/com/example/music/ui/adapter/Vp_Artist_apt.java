package com.example.music.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.music.R;

import java.util.List;

/**
 * Create By morningsun  on 2019-12-07
 */
public class Vp_Artist_apt extends FragmentPagerAdapter {
    private List<Fragment> mFrament;
    private Context context;

    public Vp_Artist_apt(FragmentManager fm, List<Fragment> mFrament, Context context) {
        super(fm);
        this.mFrament = mFrament;
        this.context = context;
    }

    public Fragment getItem(int position) {
        return mFrament.get(position);
    }

    public int getCount() {
        return mFrament.size();
    }

    @SuppressLint("SetTextI18n")
    public View getCustomView(int position) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.vp_table_list, null);
        TextView tv = view.findViewById(R.id.tv_table);
        switch(position) {
            case 0:
                tv.setText("单曲");
                break;
            case 1:
                tv.setText("专辑");
                break;
            case 2:
                tv.setText("MV");
                break;
        }
        return view;
    }
}


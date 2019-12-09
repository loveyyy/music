package com.example.music.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.music.R;
import com.example.music.model.Bananer;

import java.util.List;

public class Vp_BannerAdapter extends PagerAdapter {
    private List<Bananer> list;
    private Context context;

    public Vp_BannerAdapter(Context context, List<Bananer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int newPosition = position % list.size();
        ImageView iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        RoundedCorners roundedCorners= new RoundedCorners(context.getResources().getDimensionPixelOffset(R.dimen.dp_10));
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(list.get(newPosition).getPic()).apply(requestOptions).into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

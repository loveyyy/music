package com.example.music.ui.bindadapter;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.music.utils.imageutils.GildeCilcleImageUtils;

public class ImageViewAttrAdapter {
    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter({"imageCilcleUrl"})
    public static void loadImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions().transform(new GildeCilcleImageUtils());
        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }
    @BindingAdapter({"imageUrl"})
    public static void loadImage1(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}

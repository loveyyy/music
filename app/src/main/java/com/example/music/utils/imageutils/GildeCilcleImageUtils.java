package com.example.music.utils.imageutils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GildeCilcleImageUtils extends BitmapTransformation {
    private static final String ID = "com.xiaohe.www.lib.tools.glide.GlideCircleTransform";
    private static final byte[] ID_BYTES;

    public GildeCilcleImageUtils() {
        super();
    }

    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
        int x = (toTransform.getWidth() - size) / 2;
        int y = (toTransform.getHeight() - size) / 2;
        Bitmap square = Bitmap.createBitmap(toTransform, x, y, size, size);
        Bitmap circle = pool.get(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circle);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(square, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = (float)size / 2.0F;
        canvas.drawCircle(r, r, r, paint);
        return circle;
    }

    public boolean equals(Object obj) {
        return  false;
    }

    public int hashCode() {
        return  "com.xiaohe.www.lib.tools.glide.GlideCircleTransform".hashCode();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    static {
        ID_BYTES = "com.xiaohe.www.lib.tools.glide.GlideCircleTransform".getBytes(CHARSET);
    }
}


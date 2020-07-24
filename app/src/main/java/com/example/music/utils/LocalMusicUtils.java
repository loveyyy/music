package com.example.music.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.blankj.utilcode.util.LogUtils;
import com.example.music.model.LocalMusic;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2020-06-19
 */
public class LocalMusicUtils {
    //定义一个集合，存放从本地读取到的内容
    public static List<LocalMusic> list;

    public static LocalMusic localMusic;
    private static String name;
    private static String singer;
    private static String path;
    private static int duration;
    private static long size;
    private static long id;

    public static List<LocalMusic> getmusic(Context context) {

        list = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                localMusic = new LocalMusic();
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                if(url.equals(Environment.getExternalStorageDirectory().getPath() + File.separator + "mv")){
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                    singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                    duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                    size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                    localMusic.setPath(path);
                    localMusic.setDuration(duration);
                    localMusic.setSize(size);
                    localMusic.setId(id);
                    if (size > 1000 * 800) {
                        if (name.contains("-")) {
                            String[] str = name.split("-");
                            singer = str[0];
                            localMusic.setSinger(singer);
                            name = str[1];
                            localMusic.setName(name);
                        } else {
                            localMusic.setName(name);
                        }
                        list.add(localMusic);
                    }
                }

            }
        }
        cursor.close();
        return list;
    }

}

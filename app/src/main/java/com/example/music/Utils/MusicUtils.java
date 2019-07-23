package com.example.music.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.music.R;

import java.io.File;

/**
 * Created by Administrator on 2018/5/24.
 */

public class MusicUtils {

    public static String formatTime(int time) {
        if (time  % 60 < 10) {
            return time / 60 + ":0" + time % 60;
        } else {
            return time / 60 + ":" + time % 60;
        }
    }
    public static String geturi(String musicuri,String musicname){
        File file=new File(musicuri);
        File file1=new File(file.getParent());
        Log.e("uri",file.getParent());
        File[]files=file1.listFiles();
        if(files!=null){
            for (int i=0;i<files.length;i++){
                if(files[i].getName().replace(".lrc","").equals(musicname.replace(".mp3",""))){
                    String  musiclric=file.getParent()+"/"+files[i].getName();
                    Log.e("uri",file.getParent()+"/"+files[i].getName());
                    return musiclric;
                }
            }

        }
        return null;
    }
    /**
     * 根据专辑ID获取专辑封面图
     * @param album_id 专辑ID
     * @return
     */
    public static Bitmap getAlbumArt(Context context,int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        Bitmap bm = null;
        if (album_art != null) {
            bm = BitmapFactory.decodeFile(album_art);
        } else {
            bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
        }
        return bm;
    }
}

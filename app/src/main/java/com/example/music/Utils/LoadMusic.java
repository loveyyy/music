package com.example.music.Utils;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;


import com.example.music.entry.MusicBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class LoadMusic {


    private static final String TAG = "com.example.nature.MusicLoader";

    private static List<MusicBeen> musicList = new ArrayList<MusicBeen>();

    private static LoadMusic musicLoader;

    private static ContentResolver contentResolver;
    //Uri，指向external的database
    private Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    //projection：选择的列; where：过滤条件; sortOrder：排序。
    private String[] projection = {
            MediaStore.Audio.Media._ID,
            Media.DISPLAY_NAME,
            Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE,
            Media.ALBUM_ID
    };
    private String sortOrder = Media.DATA;

    public static LoadMusic instance(ContentResolver pContentResolver) {
        if (musicLoader == null) {
            contentResolver = pContentResolver;
            musicLoader = new LoadMusic();
        }
        return musicLoader;
    }

    @SuppressLint("LongLogTag")
    private LoadMusic() {                                                                                                             //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
        Cursor cursor = contentResolver.query(contentUri, projection, null, null, sortOrder);
        if (cursor == null) {
        } else if (!cursor.moveToFirst()) {
        } else {
            int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
            int albumCol = cursor.getColumnIndex(Media.ALBUM);
            int idCol = cursor.getColumnIndex(Media._ID);
            int durationCol = cursor.getColumnIndex(Media.DURATION);
            int sizeCol = cursor.getColumnIndex(Media.SIZE);
            int artistCol = cursor.getColumnIndex(Media.ARTIST);
            int urlCol = cursor.getColumnIndex(Media.DATA);
            int albumll=cursor.getColumnIndex(Media.ALBUM_ID);
            do {
                int album_id = cursor.getInt(albumll);
                String title = cursor.getString(displayNameCol);
                String album = cursor.getString(albumCol);
                long id = cursor.getLong(idCol);
                int duration = cursor.getInt(durationCol);
                long size = cursor.getLong(sizeCol);
                String artist = cursor.getString(artistCol);
                String url = cursor.getString(urlCol);

                MusicBeen musicInfo = new MusicBeen();
                musicInfo.setAlbum(album);
                musicInfo.setId(id);
                musicInfo.setTitle(title);
                musicInfo.setDuration(duration);
                musicInfo.setSize(size);
                musicInfo.setArtist(artist);
                musicInfo.setUrl(url);
                musicInfo.setAlbumid(album_id);
                musicList.add(musicInfo);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public List<MusicBeen> getMusicList() {
        return musicList;
    }

}
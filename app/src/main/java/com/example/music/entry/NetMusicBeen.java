package com.example.music.entry;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/6.
 */

public class NetMusicBeen implements Serializable {
    //专辑ID
    private String albumid;
    //专辑名称
    private String albumname;
    //专辑描叙
    private String albumdesc;
    //歌手Id
    private String singerid;
    //歌手姓名
    private String singername;
    //歌曲Id
    private String songid;
    //歌曲名称
    private String songname;
    //歌曲播放songmid
    private  String songmid;


    public String getSongmid() {
        return songmid;
    }

    public void setSongmid(String songmid) {
        this.songmid = songmid;
    }


    public String getAlbumdesc() {
        return albumdesc;
    }

    public void setAlbumdesc(String albumdesc) {
        this.albumdesc = albumdesc;
    }

    public String getAlbumid() {
        return albumid;

    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getSingerid() {
        return singerid;
    }

    public void setSingerid(String singerid) {
        this.singerid = singerid;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }
}

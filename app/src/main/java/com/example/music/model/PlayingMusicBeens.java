package com.example.music.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PlayingMusicBeens  {
    @Id(autoincrement = true)
    private Long id;
    private String musicid;
    private  String pic;
    private  String musicname;
    private String music_singer;
    private  int  duration;
    private String album;
    private String albumpic;
    private String rid;
    @Generated(hash = 1883344590)
    public PlayingMusicBeens(Long id, String musicid, String pic, String musicname,
            String music_singer, int duration, String album, String albumpic,
            String rid) {
        this.id = id;
        this.musicid = musicid;
        this.pic = pic;
        this.musicname = musicname;
        this.music_singer = music_singer;
        this.duration = duration;
        this.album = album;
        this.albumpic = albumpic;
        this.rid = rid;
    }
    @Generated(hash = 805414836)
    public PlayingMusicBeens() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMusicid() {
        return this.musicid;
    }
    public void setMusicid(String musicid) {
        this.musicid = musicid;
    }
    public String getPic() {
        return this.pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getMusicname() {
        return this.musicname;
    }
    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }
    public String getMusic_singer() {
        return this.music_singer;
    }
    public void setMusic_singer(String music_singer) {
        this.music_singer = music_singer;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getAlbum() {
        return this.album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getAlbumpic() {
        return this.albumpic;
    }
    public void setAlbumpic(String albumpic) {
        this.albumpic = albumpic;
    }
    public String getRid() {
        return this.rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
}

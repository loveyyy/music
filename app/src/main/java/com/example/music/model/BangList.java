package com.example.music.model;

import java.util.List;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
public class BangList {
        private String leader;
        private String num;
        private String name;
        private String pic;
        private String id;
        private String pub;
        private List<MusicListBean> musicList;

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public List<MusicListBean> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<MusicListBean> musicList) {
        this.musicList = musicList;
    }

    public static class MusicListBean {
            private String musicrid;
            private int hasmv;
            private String artist;
            private String releaseDate;
            private String album;
            private int albumid;
            private String pay;
            private int artistid;
            private String albumpic;
            private String pic;
            private int isstar;
            private int rid;
            private boolean isListenFee;
            private int duration;
            private String pic120;
            private String name;
            private int online;
            private int track;
            private PayInfoBean payInfo;

            public String getMusicrid() {
                return musicrid;
            }

            public void setMusicrid(String musicrid) {
                this.musicrid = musicrid;
            }

            public int getHasmv() {
                return hasmv;
            }

            public void setHasmv(int hasmv) {
                this.hasmv = hasmv;
            }

            public String getArtist() {
                return artist;
            }

            public void setArtist(String artist) {
                this.artist = artist;
            }

            public String getReleaseDate() {
                return releaseDate;
            }

            public void setReleaseDate(String releaseDate) {
                this.releaseDate = releaseDate;
            }

            public String getAlbum() {
                return album;
            }

            public void setAlbum(String album) {
                this.album = album;
            }

            public int getAlbumid() {
                return albumid;
            }

            public void setAlbumid(int albumid) {
                this.albumid = albumid;
            }

            public String getPay() {
                return pay;
            }

            public void setPay(String pay) {
                this.pay = pay;
            }

            public int getArtistid() {
                return artistid;
            }

            public void setArtistid(int artistid) {
                this.artistid = artistid;
            }

            public String getAlbumpic() {
                return albumpic;
            }

            public void setAlbumpic(String albumpic) {
                this.albumpic = albumpic;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getIsstar() {
                return isstar;
            }

            public void setIsstar(int isstar) {
                this.isstar = isstar;
            }

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
            }

            public boolean isListenFee() {
                return isListenFee;
            }

            public void setListenFee(boolean listenFee) {
                isListenFee = listenFee;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getPic120() {
                return pic120;
            }

            public void setPic120(String pic120) {
                this.pic120 = pic120;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public int getTrack() {
                return track;
            }

            public void setTrack(int track) {
                this.track = track;
            }

            public PayInfoBean getPayInfo() {
                return payInfo;
            }

            public void setPayInfo(PayInfoBean payInfo) {
                this.payInfo = payInfo;
            }

            public static class PayInfoBean {
                private int cannotOnlinePlay;
                private int cannotDownload;

                public int getCannotOnlinePlay() {
                    return cannotOnlinePlay;
                }

                public void setCannotOnlinePlay(int cannotOnlinePlay) {
                    this.cannotOnlinePlay = cannotOnlinePlay;
                }

                public int getCannotDownload() {
                    return cannotDownload;
                }

                public void setCannotDownload(int cannotDownload) {
                    this.cannotDownload = cannotDownload;
                }
            }
    }
}

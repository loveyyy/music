package com.example.music.model;

import java.util.List;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
public class BangMusicList {
        private String num;
        private String pub;
        private List<MusicListBean> musicList;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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
            private String artist;
            private String trend;
            private String pic;
            private int isstar;
            private String rid;
            private int duration;
            private String rank_change;
            private int track;
            private boolean hasLossless;
            private int hasmv;
            private String releaseDate;
            private String album;
            private int albumid;
            private String pay;
            private int artistid;
            private String albumpic;
            private String songTimeMinutes;
            private boolean isListenFee;
            private String pic120;
            private String name;
            private int online;
            private PayInfoBean payInfo;
            private String nationid;

        public String getMusicrid() {
            return musicrid;
        }

        public void setMusicrid(String musicrid) {
            this.musicrid = musicrid;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getTrend() {
            return trend;
        }

        public void setTrend(String trend) {
            this.trend = trend;
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

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getRank_change() {
            return rank_change;
        }

        public void setRank_change(String rank_change) {
            this.rank_change = rank_change;
        }

        public int getTrack() {
            return track;
        }

        public void setTrack(int track) {
            this.track = track;
        }

        public boolean isHasLossless() {
            return hasLossless;
        }

        public void setHasLossless(boolean hasLossless) {
            this.hasLossless = hasLossless;
        }

        public int getHasmv() {
            return hasmv;
        }

        public void setHasmv(int hasmv) {
            this.hasmv = hasmv;
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

        public String getSongTimeMinutes() {
            return songTimeMinutes;
        }

        public void setSongTimeMinutes(String songTimeMinutes) {
            this.songTimeMinutes = songTimeMinutes;
        }

        public boolean isListenFee() {
            return isListenFee;
        }

        public void setListenFee(boolean listenFee) {
            isListenFee = listenFee;
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

        public PayInfoBean getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(PayInfoBean payInfo) {
            this.payInfo = payInfo;
        }

        public String getNationid() {
            return nationid;
        }

        public void setNationid(String nationid) {
            this.nationid = nationid;
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

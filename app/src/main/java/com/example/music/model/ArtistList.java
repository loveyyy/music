package com.example.music.model;

import java.util.List;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
@Data
public class ArtistList {
    private String total;
    private List<ArtistListBean> artistList;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ArtistListBean> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<ArtistListBean> artistList) {
        this.artistList = artistList;
    }

    public static class ArtistListBean {
        private int artistFans;
        private int albumNum;
        private int mvNum;
        private String pic;
        private String musicNum;
        private String pic120;
        private int isStar;
        private String aartist;
        private String name;
        private String pic70;
        private int id;
        private String pic300;

        public int getArtistFans() {
            return artistFans;
        }

        public void setArtistFans(int artistFans) {
            this.artistFans = artistFans;
        }

        public int getAlbumNum() {
            return albumNum;
        }

        public void setAlbumNum(int albumNum) {
            this.albumNum = albumNum;
        }

        public int getMvNum() {
            return mvNum;
        }

        public void setMvNum(int mvNum) {
            this.mvNum = mvNum;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getMusicNum() {
            return musicNum;
        }

        public void setMusicNum(String musicNum) {
            this.musicNum = musicNum;
        }

        public String getPic120() {
            return pic120;
        }

        public void setPic120(String pic120) {
            this.pic120 = pic120;
        }

        public int getIsStar() {
            return isStar;
        }

        public void setIsStar(int isStar) {
            this.isStar = isStar;
        }

        public String getAartist() {
            return aartist;
        }

        public void setAartist(String aartist) {
            this.aartist = aartist;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic70() {
            return pic70;
        }

        public void setPic70(String pic70) {
            this.pic70 = pic70;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPic300() {
            return pic300;
        }

        public void setPic300(String pic300) {
            this.pic300 = pic300;
        }
    }
}

package com.example.music.model;

import java.util.List;

/**
 * Create By morningsun  on 2020-06-24
 */
public class Search {
        private String total;
        private List<ListBean> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * musicrid : MUSIC_143517243
             * artist : 张云雷&酷小我大制作
             * mvpayinfo : {"play":0,"vid":0,"down":0}
             * pic : https://img2.kuwo.cn/star/albumcover/300/73/12/3267250037.jpg
             * isstar : 1
             * rid : 143517243
             * duration : 64
             * score100 : 0
             * content_type : 0
             * track : 143
             * hasLossless : false
             * hasmv : 0
             * releaseDate : 2019-03-14
             * album : 张云雷的音乐盒
             * albumid : 9325009
             * pay : 0
             * artistid : 119158
             * albumpic : https://img2.kuwo.cn/star/albumcover/500/73/12/3267250037.jpg
             * songTimeMinutes : 01:04
             * isListenFee : false
             * pic120 : https://img2.kuwo.cn/star/albumcover/120/73/12/3267250037.jpg
             * name : 探清水河（二人转版）
             * online : 1
             * payInfo : {"cannotDownload":0,"cannotOnlinePlay":0}
             * nationid : 0
             */

            private String musicrid;
            private String artist;
            private MvpayinfoBean mvpayinfo;
            private String pic;
            private int isstar;
            private int rid;
            private int duration;
            private String score100;
            private String content_type;
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

            public MvpayinfoBean getMvpayinfo() {
                return mvpayinfo;
            }

            public void setMvpayinfo(MvpayinfoBean mvpayinfo) {
                this.mvpayinfo = mvpayinfo;
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

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getScore100() {
                return score100;
            }

            public void setScore100(String score100) {
                this.score100 = score100;
            }

            public String getContent_type() {
                return content_type;
            }

            public void setContent_type(String content_type) {
                this.content_type = content_type;
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

            public boolean isIsListenFee() {
                return isListenFee;
            }

            public void setIsListenFee(boolean isListenFee) {
                this.isListenFee = isListenFee;
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

            public static class MvpayinfoBean {
                /**
                 * play : 0
                 * vid : 0
                 * down : 0
                 */

                private int play;
                private int vid;
                private int down;

                public int getPlay() {
                    return play;
                }

                public void setPlay(int play) {
                    this.play = play;
                }

                public int getVid() {
                    return vid;
                }

                public void setVid(int vid) {
                    this.vid = vid;
                }

                public int getDown() {
                    return down;
                }

                public void setDown(int down) {
                    this.down = down;
                }
            }

            public static class PayInfoBean {
                /**
                 * cannotDownload : 0
                 * cannotOnlinePlay : 0
                 */

                private int cannotDownload;
                private int cannotOnlinePlay;

                public int getCannotDownload() {
                    return cannotDownload;
                }

                public void setCannotDownload(int cannotDownload) {
                    this.cannotDownload = cannotDownload;
                }

                public int getCannotOnlinePlay() {
                    return cannotOnlinePlay;
                }

                public void setCannotOnlinePlay(int cannotOnlinePlay) {
                    this.cannotOnlinePlay = cannotOnlinePlay;
                }
            }
        }
}

package com.example.music.entry;

import java.util.List;

public class SearchBeens {
    private int code;
    private long curTime;
    private DataBean data;
    private String msg;
    private String profileId;
    private String reqId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getCurTime() {
        return curTime;
    }

    public void setCurTime(long curTime) {
        this.curTime = curTime;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public static class DataBean {
        /**
         * total : 2
         * list : [{"musicrid":"MUSIC_72739394","hasmv":0,"artist":"烟把儿乐队魏振&王淑妍","releaseDate":"2019-07-20","album":"再探清水河","albumid":10401930,"pay":"16515324","artistid":3826550,"albumpic":"http://img3.kuwo.cn/star/albumcover/500/3/69/3911010009.jpg","songTimeMinutes":"04:01","pic":"http://img3.kuwo.cn/star/albumcover/300/3/69/3911010009.jpg","isstar":0,"rid":72739394,"isListenFee":false,"duration":241,"pic120":"http://img3.kuwo.cn/star/albumcover/120/3/69/3911010009.jpg","name":"再探清水河","online":1,"track":1,"payInfo":{"cannotOnlinePlay":0,"cannotDownload":0},"hasLossless":true},{"musicrid":"MUSIC_72739358","hasmv":0,"artist":"烟把儿乐队魏振&王淑妍","releaseDate":"2019-07-20","album":"再探清水河","albumid":10401930,"pay":"16515324","artistid":3826550,"albumpic":"http://img3.kuwo.cn/star/albumcover/500/3/69/3911010009.jpg","songTimeMinutes":"04:01","pic":"http://img3.kuwo.cn/star/albumcover/300/3/69/3911010009.jpg","isstar":0,"rid":72739358,"isListenFee":false,"duration":241,"pic120":"http://img3.kuwo.cn/star/albumcover/120/3/69/3911010009.jpg","name":"再探清水河(伴奏)","online":1,"track":2,"payInfo":{"cannotOnlinePlay":0,"cannotDownload":0},"hasLossless":true}]
         */

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
             * musicrid : MUSIC_72739394
             * hasmv : 0
             * artist : 烟把儿乐队魏振&王淑妍
             * releaseDate : 2019-07-20
             * album : 再探清水河
             * albumid : 10401930
             * pay : 16515324
             * artistid : 3826550
             * albumpic : http://img3.kuwo.cn/star/albumcover/500/3/69/3911010009.jpg
             * songTimeMinutes : 04:01
             * pic : http://img3.kuwo.cn/star/albumcover/300/3/69/3911010009.jpg
             * isstar : 0
             * rid : 72739394
             * isListenFee : false
             * duration : 241
             * pic120 : http://img3.kuwo.cn/star/albumcover/120/3/69/3911010009.jpg
             * name : 再探清水河
             * online : 1
             * track : 1
             * payInfo : {"cannotOnlinePlay":0,"cannotDownload":0}
             * hasLossless : true
             */

            private String musicrid;
            private int hasmv;
            private String artist;
            private String releaseDate;
            private String album;
            private int albumid;
            private String pay;
            private int artistid;
            private String albumpic;
            private String songTimeMinutes;
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
            private boolean hasLossless;

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

            public String getSongTimeMinutes() {
                return songTimeMinutes;
            }

            public void setSongTimeMinutes(String songTimeMinutes) {
                this.songTimeMinutes = songTimeMinutes;
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

            public boolean isIsListenFee() {
                return isListenFee;
            }

            public void setIsListenFee(boolean isListenFee) {
                this.isListenFee = isListenFee;
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

            public boolean isHasLossless() {
                return hasLossless;
            }

            public void setHasLossless(boolean hasLossless) {
                this.hasLossless = hasLossless;
            }

            public static class PayInfoBean {
                /**
                 * cannotOnlinePlay : 0
                 * cannotDownload : 0
                 */

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
}

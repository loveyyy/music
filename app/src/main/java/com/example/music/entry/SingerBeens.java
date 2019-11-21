package com.example.music.entry;

import java.util.List;

public class SingerBeens {


    /**
     * code : 200
     * curTime : 1563528002644
     * data : {"total":"14331","artistList":[{"artistFans":418218,"albumNum":54,"mvNum":276,"pic":"http://img4.kuwo.cn/star/starheads/300/39/41/2312174491.jpg","musicNum":750,"pic120":"http://img4.kuwo.cn/star/starheads/120/39/41/2312174491.jpg","isStar":0,"aartist":"邓紫棋","name":"G.E.M.邓紫棋","pic70":"http://img4.kuwo.cn/star/starheads/70/39/41/2312174491.jpg","id":5371,"pic300":"http://img4.kuwo.cn/star/starheads/300/39/41/2312174491.jpg"},{"artistFans":73599,"albumNum":28,"mvNum":2,"pic":"http://img4.kuwo.cn/star/starheads/300/57/42/3773488596.jpg","musicNum":57,"pic120":"http://img4.kuwo.cn/star/starheads/120/57/42/3773488596.jpg","isStar":0,"aartist":"SherlyChan","name":"陈雪凝","pic70":"http://img4.kuwo.cn/star/starheads/70/57/42/3773488596.jpg","id":1486611,"pic300":"http://img4.kuwo.cn/star/starheads/300/57/42/3773488596.jpg"},{"artistFans":1136556,"albumNum":44,"mvNum":164,"pic":"http://img4.kuwo.cn/star/starheads/300/20/17/4242228528.jpg","musicNum":498,"pic120":"http://img4.kuwo.cn/star/starheads/120/20/17/4242228528.jpg","isStar":0,"aartist":"Joker Xue","name":"薛之谦","pic70":"http://img4.kuwo.cn/star/starheads/70/20/17/4242228528.jpg","id":947,"pic300":"http://img4.kuwo.cn/star/starheads/300/20/17/4242228528.jpg"},{"artistFans":29882,"albumNum":54,"mvNum":2,"pic":"http://img4.kuwo.cn/star/starheads/300/3/89/3557381605.jpg","musicNum":149,"pic120":"http://img4.kuwo.cn/star/starheads/120/3/89/3557381605.jpg","isStar":0,"aartist":"","name":"王琪","pic70":"http://img4.kuwo.cn/star/starheads/70/3/89/3557381605.jpg","id":64359,"pic300":"http://img4.kuwo.cn/star/starheads/300/3/89/3557381605.jpg"},{"artistFans":511942,"albumNum":82,"mvNum":319,"pic":"http://img3.sycdn.kuwo.cn/star/starheads/300/75/22/1016326664.jpg","musicNum":1025,"pic120":"http://img3.sycdn.kuwo.cn/star/starheads/120/75/22/1016326664.jpg","isStar":0,"aartist":"JJ","name":"林俊杰","pic70":"http://img3.sycdn.kuwo.cn/star/starheads/70/75/22/1016326664.jpg","id":1062,"pic300":"http://img3.sycdn.kuwo.cn/star/starheads/300/75/22/1016326664.jpg"},{"artistFans":821642,"albumNum":44,"mvNum":798,"pic":"http://img1.kuwo.cn/star/starheads/300/61/99/2461721588.jpg","musicNum":1163,"pic120":"http://img1.kuwo.cn/star/starheads/120/61/99/2461721588.jpg","isStar":0,"aartist":"Jay Chou","name":"周杰伦","pic70":"http://img1.kuwo.cn/star/starheads/70/61/99/2461721588.jpg","id":336,"pic300":"http://img1.kuwo.cn/star/starheads/300/61/99/2461721588.jpg"}]}
     * msg : success
     * profileId : site
     * reqId : 977a389f-b236-4fc4-a70d-c1db1a541097
     */

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
         * total : 14331
         * artistList : [{"artistFans":418218,"albumNum":54,"mvNum":276,"pic":"http://img4.kuwo.cn/star/starheads/300/39/41/2312174491.jpg","musicNum":750,"pic120":"http://img4.kuwo.cn/star/starheads/120/39/41/2312174491.jpg","isStar":0,"aartist":"邓紫棋","name":"G.E.M.邓紫棋","pic70":"http://img4.kuwo.cn/star/starheads/70/39/41/2312174491.jpg","id":5371,"pic300":"http://img4.kuwo.cn/star/starheads/300/39/41/2312174491.jpg"},{"artistFans":73599,"albumNum":28,"mvNum":2,"pic":"http://img4.kuwo.cn/star/starheads/300/57/42/3773488596.jpg","musicNum":57,"pic120":"http://img4.kuwo.cn/star/starheads/120/57/42/3773488596.jpg","isStar":0,"aartist":"SherlyChan","name":"陈雪凝","pic70":"http://img4.kuwo.cn/star/starheads/70/57/42/3773488596.jpg","id":1486611,"pic300":"http://img4.kuwo.cn/star/starheads/300/57/42/3773488596.jpg"},{"artistFans":1136556,"albumNum":44,"mvNum":164,"pic":"http://img4.kuwo.cn/star/starheads/300/20/17/4242228528.jpg","musicNum":498,"pic120":"http://img4.kuwo.cn/star/starheads/120/20/17/4242228528.jpg","isStar":0,"aartist":"Joker Xue","name":"薛之谦","pic70":"http://img4.kuwo.cn/star/starheads/70/20/17/4242228528.jpg","id":947,"pic300":"http://img4.kuwo.cn/star/starheads/300/20/17/4242228528.jpg"},{"artistFans":29882,"albumNum":54,"mvNum":2,"pic":"http://img4.kuwo.cn/star/starheads/300/3/89/3557381605.jpg","musicNum":149,"pic120":"http://img4.kuwo.cn/star/starheads/120/3/89/3557381605.jpg","isStar":0,"aartist":"","name":"王琪","pic70":"http://img4.kuwo.cn/star/starheads/70/3/89/3557381605.jpg","id":64359,"pic300":"http://img4.kuwo.cn/star/starheads/300/3/89/3557381605.jpg"},{"artistFans":511942,"albumNum":82,"mvNum":319,"pic":"http://img3.sycdn.kuwo.cn/star/starheads/300/75/22/1016326664.jpg","musicNum":1025,"pic120":"http://img3.sycdn.kuwo.cn/star/starheads/120/75/22/1016326664.jpg","isStar":0,"aartist":"JJ","name":"林俊杰","pic70":"http://img3.sycdn.kuwo.cn/star/starheads/70/75/22/1016326664.jpg","id":1062,"pic300":"http://img3.sycdn.kuwo.cn/star/starheads/300/75/22/1016326664.jpg"},{"artistFans":821642,"albumNum":44,"mvNum":798,"pic":"http://img1.kuwo.cn/star/starheads/300/61/99/2461721588.jpg","musicNum":1163,"pic120":"http://img1.kuwo.cn/star/starheads/120/61/99/2461721588.jpg","isStar":0,"aartist":"Jay Chou","name":"周杰伦","pic70":"http://img1.kuwo.cn/star/starheads/70/61/99/2461721588.jpg","id":336,"pic300":"http://img1.kuwo.cn/star/starheads/300/61/99/2461721588.jpg"}]
         */

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
            /**
             * artistFans : 418218
             * albumNum : 54
             * mvNum : 276
             * pic : http://img4.kuwo.cn/star/starheads/300/39/41/2312174491.jpg
             * musicNum : 750
             * pic120 : http://img4.kuwo.cn/star/starheads/120/39/41/2312174491.jpg
             * isStar : 0
             * aartist : 邓紫棋
             * name : G.E.M.邓紫棋
             * pic70 : http://img4.kuwo.cn/star/starheads/70/39/41/2312174491.jpg
             * id : 5371
             * pic300 : http://img4.kuwo.cn/star/starheads/300/39/41/2312174491.jpg
             */

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
}

package com.example.music.model;

import java.math.BigInteger;
import java.util.List;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
public class MusicList {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * img : http://img1.kwcdn.kuwo.cn/star/userpl2015/10/13/1575302893570_132026710_150.jpg
             * uname :
             * img700 : http://img1.kwcdn.kuwo.cn/star/userpl2015/10/13/1575302893570_132026710_700.jpg
             * img300 : http://img1.kwcdn.kuwo.cn/star/userpl2015/10/13/1575302893570_132026710b.jpg
             * userName :
             * img500 : http://img1.kwcdn.kuwo.cn/star/userpl2015/10/13/1575302893570_132026710_500.jpg
             * total : 174
             * name : 每日最新单曲推荐
             * listencnt : 196170575
             * id : 1082685104
             * tag :
             * musicList : []
             * desc :
             * info : 电影《半个喜剧》曝光主题曲《如果我不是我》音频，由歌手、音乐创作人李宇春倾情献唱，此次亦是李宇春与开心麻花的首度合作。
             */

            private String img;
            private String uname;
            private String img700;
            private String img300;
            private String userName;
            private String img500;
            private int total;
            private String name;
            private BigInteger listencnt;
            private BigInteger id;
            private String tag;
            private String desc;
            private String info;
            private List<?> musicList;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getImg700() {
                return img700;
            }

            public void setImg700(String img700) {
                this.img700 = img700;
            }

            public String getImg300() {
                return img300;
            }

            public void setImg300(String img300) {
                this.img300 = img300;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getImg500() {
                return img500;
            }

            public void setImg500(String img500) {
                this.img500 = img500;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public BigInteger getListencnt() {
                return listencnt;
            }

            public void setListencnt(BigInteger listencnt) {
                this.listencnt = listencnt;
            }

            public BigInteger getId() {
                return id;
            }

            public void setId(BigInteger id) {
                this.id = id;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public List<?> getMusicList() {
                return musicList;
            }

            public void setMusicList(List<?> musicList) {
                this.musicList = musicList;
            }
        }
}

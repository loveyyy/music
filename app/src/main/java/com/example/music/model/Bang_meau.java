package com.example.music.model;

import java.util.List;

/**
 * Create By morningsun  on 2019-11-28
 */
public class Bang_meau {
        /**
         * name : 官方榜
         * list : [{"sourceid":"93","intro":"酷我用户每天播放线上歌曲的飙升指数TOP排行榜，为你展示流行趋势、蹿红歌曲，每天更新","name":"酷我飙升榜","id":"489929","source":"2","pic":"http://img3.kwcdn.kuwo.cn/star/upload/9/6/1575500764.png","pub":"今日更新"},{"sourceid":"17","intro":"酷我用户每天播放新歌（一个月内发行）TOP排行榜，为你展示当下潮流新歌，每天更新","name":"酷我新歌榜","id":"489928","source":"2","pic":"http://img3.kwcdn.kuwo.cn/star/upload/8/5/1575414353.png","pub":"今日更新"},{"sourceid":"16","intro":"酷我用户每天播放线上歌曲TOP排行榜，为你展示当下最人气最热门歌曲，每天更新","name":"酷我热歌榜","id":"489927","source":"2","pic":"http://img3.kwcdn.kuwo.cn/star/upload/4/4/1575500760.png","pub":"今日更新"},{"sourceid":"158","intro":"抖音官方热歌TOP排行榜，为你展示最火最洗脑的抖音神曲，每周二更新","name":"抖音热歌榜","id":"490022","source":"2","pic":"http://img3.kwcdn.kuwo.cn/star/upload/6/9/1575342085.png","pub":"12月03日更新"},{"sourceid":"145","intro":"酷我音乐包歌曲TOP排行榜，为你展示最卖座的高品质无损音乐，每天更新","name":"会员畅听榜","id":"507077","source":"2","pic":"http://img3.kwcdn.kuwo.cn/star/upload/7/1/1575342094.png","pub":"今日更新"}]
         */

        private String name;
        private List<ListBean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * sourceid : 93
             * intro : 酷我用户每天播放线上歌曲的飙升指数TOP排行榜，为你展示流行趋势、蹿红歌曲，每天更新
             * name : 酷我飙升榜
             * id : 489929
             * source : 2
             * pic : http://img3.kwcdn.kuwo.cn/star/upload/9/6/1575500764.png
             * pub : 今日更新
             */

            private String sourceid;
            private String intro;
            private String name;
            private String id;
            private String source;
            private String pic;
            private String pub;

            public String getSourceid() {
                return sourceid;
            }

            public void setSourceid(String sourceid) {
                this.sourceid = sourceid;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPub() {
                return pub;
            }

            public void setPub(String pub) {
                this.pub = pub;
            }
        }
}

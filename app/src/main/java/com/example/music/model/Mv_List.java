package com.example.music.model;

import java.util.List;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
@Data
public class Mv_List {
        private int total;
        private List<MvlistBean> mvlist;

        @Data
        public static class MvlistBean {
            private int duration;
            private String artist;
            private int mvPlayCnt;
            private String name;
            private int online;
            private String songTimeMinutes;
            private int artistid;
            private int id;
            private String pic;
    }
}

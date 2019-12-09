package com.example.music.model;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
@Data
public class Radio_info {
        private String musicrid;
        private String artist;
        private String pic;
        private int isstar;
        private int rid;
        private String upPcStr;
        private int duration;
        private int mvPlayCnt;
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
        private String mvUpPcStr;
        private String pic120;
        private String albuminfo;
        private String name;
        private int online;
        private PayInfoBean payInfo;

        @Data
        public static class PayInfoBean {
            private int cannotOnlinePlay;
            private int cannotDownload;
    }
}

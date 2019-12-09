package com.example.music.model;

import java.util.List;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
@Data
public class Radio_list {
        private List<AlbumListBean> albumList;
        @Data
        public static class AlbumListBean {
            private String artist;
            private String album;
            private String listencnt;
            private String pic;
            private String rid;
    }
}

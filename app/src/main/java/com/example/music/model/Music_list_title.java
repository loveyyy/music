package com.example.music.model;

import java.util.List;

import lombok.Data;

/**
 * Create By morningsun  on 2019-11-28
 */
@Data
public class Music_list_title {
        private String img;
        private String mdigest;
        private String name;
        private String id;
        private String type;
        private String img1;
        private List<DataBean> data;

        @Data
        public static class DataBean {
            private String extend;
            private String img;
            private String digest;
            private String name;
            private String isnew;
            private String id;
    }
}

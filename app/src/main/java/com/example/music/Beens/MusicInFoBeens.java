package com.example.music.Beens;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MusicInFoBeens {

    /**
     * state : 0
     * message :
     * request_id : 0b0b8a9015634172370481612e0a6c
     * data : {"song_id":1812047014,"song_name":"你笑起来真好看","album_id":2104974877,"pace":0,"album_name":"你笑起来真好看","album_logo":"http://pic.xiami.net/images/album/img52/4583/5d21d3c8110fb_229185052_1562497992.jpg","artist_id":2103336159,"artist_name":"邓文怡","singers":"邓文怡","artist_logo":"http://pic.xiami.net/images/artistlogo/12/15294677616512.jpg","length":172,"listen_file":"Gi00ZkkrVi0GFmNkMgpxKRx9HS0dd09NEz0gKBAWGyUFXydnNTofMAc4dC4zLQZjBgs6bAQrLF19KgZnBV4zZAdfRS0zO04vB050YzQteSoHfB0wBgU8Xn4qIGYEKEllBl9Naj5hSjAIOHgpSAt5LAloK2gcd0tLEhgLZwc4J2EHTzMuM1E0aAcodCgzdWgpBQs2ZxJ0NVF/PX0tBVE3KAYXK2o/OzgvFCgGHjMdEx8IDippERslTwpQGSABUjAnHT0sIUA7GC0dLHUq","play_volume":89,"music_type":0,"track":1,"cd_serial":1,"lyric_type":3,"lyric":"http://img.xiami.net/lyric/14/1812047014_1562750615_5234.trc","purview_roles":[{"quality":"s","filesize":30357420,"is_exist":true,"operation_list":[{"purpose":1,"upgrade_role":3},{"purpose":2,"upgrade_role":3}]},{"quality":"h","filesize":6885876,"is_exist":true,"operation_list":[{"purpose":1,"upgrade_role":0},{"purpose":2,"upgrade_role":0}]},{"quality":"l","filesize":2754350,"is_exist":true,"operation_list":[{"purpose":1,"upgrade_role":0},{"purpose":2,"upgrade_role":0}]},{"quality":"f","filesize":0,"is_exist":true,"operation_list":[{"purpose":1,"upgrade_role":1},{"purpose":2,"upgrade_role":1}]}],"quality":"l","expire":28800,"rate":128,"favorite":0,"h5_url":""}
     * data-version : ab6ab8c1b83f96440ef88f01ebc10206
     */

    private int state;
    private String message;
    private String request_id;
    private DataBean data;
    @SerializedName("data-version")
    private String dataversion;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getDataversion() {
        return dataversion;
    }

    public void setDataversion(String dataversion) {
        this.dataversion = dataversion;
    }

    public static class DataBean {
        /**
         * song_id : 1812047014
         * song_name : 你笑起来真好看
         * album_id : 2104974877
         * pace : 0
         * album_name : 你笑起来真好看
         * album_logo : http://pic.xiami.net/images/album/img52/4583/5d21d3c8110fb_229185052_1562497992.jpg
         * artist_id : 2103336159
         * artist_name : 邓文怡
         * singers : 邓文怡
         * artist_logo : http://pic.xiami.net/images/artistlogo/12/15294677616512.jpg
         * length : 172
         * listen_file : Gi00ZkkrVi0GFmNkMgpxKRx9HS0dd09NEz0gKBAWGyUFXydnNTofMAc4dC4zLQZjBgs6bAQrLF19KgZnBV4zZAdfRS0zO04vB050YzQteSoHfB0wBgU8Xn4qIGYEKEllBl9Naj5hSjAIOHgpSAt5LAloK2gcd0tLEhgLZwc4J2EHTzMuM1E0aAcodCgzdWgpBQs2ZxJ0NVF/PX0tBVE3KAYXK2o/OzgvFCgGHjMdEx8IDippERslTwpQGSABUjAnHT0sIUA7GC0dLHUq
         * play_volume : 89
         * music_type : 0
         * track : 1
         * cd_serial : 1
         * lyric_type : 3
         * lyric : http://img.xiami.net/lyric/14/1812047014_1562750615_5234.trc
         * purview_roles : [{"quality":"s","filesize":30357420,"is_exist":true,"operation_list":[{"purpose":1,"upgrade_role":3},{"purpose":2,"upgrade_role":3}]},{"quality":"h","filesize":6885876,"is_exist":true,"operation_list":[{"purpose":1,"upgrade_role":0},{"purpose":2,"upgrade_role":0}]},{"quality":"l","filesize":2754350,"is_exist":true,"operation_list":[{"purpose":1,"upgrade_role":0},{"purpose":2,"upgrade_role":0}]},{"quality":"f","filesize":0,"is_exist":true,"operation_list":[{"purpose":1,"upgrade_role":1},{"purpose":2,"upgrade_role":1}]}]
         * quality : l
         * expire : 28800
         * rate : 128
         * favorite : 0
         * h5_url :
         */

        private int song_id;
        private String song_name;
        private int album_id;
        private int pace;
        private String album_name;
        private String album_logo;
        private int artist_id;
        private String artist_name;
        private String singers;
        private String artist_logo;
        private int length;
        private String listen_file;
        private int play_volume;
        private int music_type;
        private int track;
        private int cd_serial;
        private int lyric_type;
        private String lyric;
        private String quality;
        private int expire;
        private int rate;
        private int favorite;
        private String h5_url;
        private List<PurviewRolesBean> purview_roles;

        public int getSong_id() {
            return song_id;
        }

        public void setSong_id(int song_id) {
            this.song_id = song_id;
        }

        public String getSong_name() {
            return song_name;
        }

        public void setSong_name(String song_name) {
            this.song_name = song_name;
        }

        public int getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(int album_id) {
            this.album_id = album_id;
        }

        public int getPace() {
            return pace;
        }

        public void setPace(int pace) {
            this.pace = pace;
        }

        public String getAlbum_name() {
            return album_name;
        }

        public void setAlbum_name(String album_name) {
            this.album_name = album_name;
        }

        public String getAlbum_logo() {
            return album_logo;
        }

        public void setAlbum_logo(String album_logo) {
            this.album_logo = album_logo;
        }

        public int getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(int artist_id) {
            this.artist_id = artist_id;
        }

        public String getArtist_name() {
            return artist_name;
        }

        public void setArtist_name(String artist_name) {
            this.artist_name = artist_name;
        }

        public String getSingers() {
            return singers;
        }

        public void setSingers(String singers) {
            this.singers = singers;
        }

        public String getArtist_logo() {
            return artist_logo;
        }

        public void setArtist_logo(String artist_logo) {
            this.artist_logo = artist_logo;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public String getListen_file() {
            return listen_file;
        }

        public void setListen_file(String listen_file) {
            this.listen_file = listen_file;
        }

        public int getPlay_volume() {
            return play_volume;
        }

        public void setPlay_volume(int play_volume) {
            this.play_volume = play_volume;
        }

        public int getMusic_type() {
            return music_type;
        }

        public void setMusic_type(int music_type) {
            this.music_type = music_type;
        }

        public int getTrack() {
            return track;
        }

        public void setTrack(int track) {
            this.track = track;
        }

        public int getCd_serial() {
            return cd_serial;
        }

        public void setCd_serial(int cd_serial) {
            this.cd_serial = cd_serial;
        }

        public int getLyric_type() {
            return lyric_type;
        }

        public void setLyric_type(int lyric_type) {
            this.lyric_type = lyric_type;
        }

        public String getLyric() {
            return lyric;
        }

        public void setLyric(String lyric) {
            this.lyric = lyric;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public String getH5_url() {
            return h5_url;
        }

        public void setH5_url(String h5_url) {
            this.h5_url = h5_url;
        }

        public List<PurviewRolesBean> getPurview_roles() {
            return purview_roles;
        }

        public void setPurview_roles(List<PurviewRolesBean> purview_roles) {
            this.purview_roles = purview_roles;
        }

        public static class PurviewRolesBean {
            /**
             * quality : s
             * filesize : 30357420
             * is_exist : true
             * operation_list : [{"purpose":1,"upgrade_role":3},{"purpose":2,"upgrade_role":3}]
             */

            private String quality;
            private int filesize;
            private boolean is_exist;
            private List<OperationListBean> operation_list;

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }

            public int getFilesize() {
                return filesize;
            }

            public void setFilesize(int filesize) {
                this.filesize = filesize;
            }

            public boolean isIs_exist() {
                return is_exist;
            }

            public void setIs_exist(boolean is_exist) {
                this.is_exist = is_exist;
            }

            public List<OperationListBean> getOperation_list() {
                return operation_list;
            }

            public void setOperation_list(List<OperationListBean> operation_list) {
                this.operation_list = operation_list;
            }

            public static class OperationListBean {
                /**
                 * purpose : 1
                 * upgrade_role : 3
                 */

                private int purpose;
                private int upgrade_role;

                public int getPurpose() {
                    return purpose;
                }

                public void setPurpose(int purpose) {
                    this.purpose = purpose;
                }

                public int getUpgrade_role() {
                    return upgrade_role;
                }

                public void setUpgrade_role(int upgrade_role) {
                    this.upgrade_role = upgrade_role;
                }
            }
        }
    }
}

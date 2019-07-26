package com.example.music.Beens;


public class PlayingMusicBeens {
        private  String pic;
        private  String musicname;
        private String music_singer;
        private  int  duration;
        private String album;
        private String albumpic;

        public String getAlbumpic() {
            return albumpic;
        }

        public void setAlbumpic(String albumpic) {
            this.albumpic = albumpic;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

         public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        private  int rid;
        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getMusicname() {
            return musicname;
        }

        public void setMusicname(String musicname) {
            this.musicname = musicname;
        }

        public String getMusic_singer() {
            return music_singer;
        }

        public void setMusic_singer(String music_singer) {
            this.music_singer = music_singer;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
}

package com.example.music.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Create By morningsun  on 2020-06-17
 */
@Entity
public class DownLoadInfo {
    private Long id;
    private String filename;
    private String filepath;
    private int size;
    private String url;
    private int state;
    private int downloadsize;
    @Generated(hash = 2033880839)
    public DownLoadInfo(Long id, String filename, String filepath, int size,
            String url, int state, int downloadsize) {
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
        this.size = size;
        this.url = url;
        this.state = state;
        this.downloadsize = downloadsize;
    }
    @Generated(hash = 1743687477)
    public DownLoadInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFilepath() {
        return this.filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public int getSize() {
        return this.size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getDownloadsize() {
        return this.downloadsize;
    }
    public void setDownloadsize(int downloadsize) {
        this.downloadsize = downloadsize;
    }
}

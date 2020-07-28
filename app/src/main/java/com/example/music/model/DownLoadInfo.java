package com.example.music.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Create By morningsun  on 2020-06-17
 */
@Entity
public class DownLoadInfo {
    @Id(autoincrement = true)
    private Long id;
    private String filename;
    private String filepath;
    private int totalSize;
    private String url;
    private int downloadSize;
    private int state;
    @ToMany(referencedJoinProperty="taskId")
    private List<DownLoadProgree> downLoadProgree;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 395963920)
    private transient DownLoadInfoDao myDao;
    @Generated(hash = 612494790)
    public DownLoadInfo(Long id, String filename, String filepath, int totalSize, String url,
            int downloadSize, int state) {
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
        this.totalSize = totalSize;
        this.url = url;
        this.downloadSize = downloadSize;
        this.state = state;
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
    public int getTotalSize() {
        return this.totalSize;
    }
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getDownloadSize() {
        return this.downloadSize;
    }
    public void setDownloadSize(int downloadSize) {
        this.downloadSize = downloadSize;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 915369665)
    public List<DownLoadProgree> getDownLoadProgree() {
        if (downLoadProgree == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DownLoadProgreeDao targetDao = daoSession.getDownLoadProgreeDao();
            List<DownLoadProgree> downLoadProgreeNew = targetDao
                    ._queryDownLoadInfo_DownLoadProgree(id);
            synchronized (this) {
                if (downLoadProgree == null) {
                    downLoadProgree = downLoadProgreeNew;
                }
            }
        }
        return downLoadProgree;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2122777778)
    public synchronized void resetDownLoadProgree() {
        downLoadProgree = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 19427046)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDownLoadInfoDao() : null;
    }
}

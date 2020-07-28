package com.example.music.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

/**
 * Create By morningsun  on 2020-07-27
 */
@Entity
public class DownLoadProgree {
    @Id(autoincrement = true)
    private Long id;
    private Long taskId;
    private int start;
    private int end;
    private int state;
    private int downloadSize;
    @ToOne(joinProperty = "taskId")
    private DownLoadInfo downLoadInfo;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1320896653)
    private transient DownLoadProgreeDao myDao;
    @Generated(hash = 1880492710)
    public DownLoadProgree(Long id, Long taskId, int start, int end, int state,
            int downloadSize) {
        this.id = id;
        this.taskId = taskId;
        this.start = start;
        this.end = end;
        this.state = state;
        this.downloadSize = downloadSize;
    }
    @Generated(hash = 1890976054)
    public DownLoadProgree() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTaskId() {
        return this.taskId;
    }
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    public int getStart() {
        return this.start;
    }
    public void setStart(int start) {
        this.start = start;
    }
    public int getEnd() {
        return this.end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    @Generated(hash = 2138676705)
    private transient Long downLoadInfo__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1545381819)
    public DownLoadInfo getDownLoadInfo() {
        Long __key = this.taskId;
        if (downLoadInfo__resolvedKey == null
                || !downLoadInfo__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DownLoadInfoDao targetDao = daoSession.getDownLoadInfoDao();
            DownLoadInfo downLoadInfoNew = targetDao.load(__key);
            synchronized (this) {
                downLoadInfo = downLoadInfoNew;
                downLoadInfo__resolvedKey = __key;
            }
        }
        return downLoadInfo;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1766744962)
    public void setDownLoadInfo(DownLoadInfo downLoadInfo) {
        synchronized (this) {
            this.downLoadInfo = downLoadInfo;
            taskId = downLoadInfo == null ? null : downLoadInfo.getId();
            downLoadInfo__resolvedKey = taskId;
        }
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
    public int getDownloadSize() {
        return this.downloadSize;
    }
    public void setDownloadSize(int downloadSize) {
        this.downloadSize = downloadSize;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 280020057)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDownLoadProgreeDao() : null;
    }

}

package com.example.music.utils.greendao;

import android.content.Context;

import com.example.music.BuildConfig;
import com.example.music.model.DaoMaster;
import com.example.music.model.DaoSession;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Create By morningsun  on 2020-01-09
 */
public class DaoManager {
    private static final String DB_NAME = "music";

    private Context context;

    private volatile static DaoManager manager = new DaoManager();
    private static DaoMaster sDaoMaster;
    private static DaoMaster.DevOpenHelper sHelper;
    private static DaoSession sDaoSession;

    /**
     * 单例模式获得操作数据库对象
     *
     * @return
     */
    public static DaoManager getInstance()
    {
        return manager;
    }

    private DaoManager()
    {
        setDebug();
    }

    public void init(Context context)
    {
        this.context = context;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     *
     * @return
     */
    public DaoMaster getDaoMaster()
    {
        if (sDaoMaster == null)
        {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
            sDaoMaster = new DaoMaster(helper.getWritableDb());
        }
        return sDaoMaster;
    }


    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     *
     * @return
     */
    public DaoSession getDaoSession()
    {
        if (sDaoSession == null)
        {
            if (sDaoMaster == null)
            {
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }


    /**
     * 打开输出日志，默认关闭
     */
    public void setDebug()
    {
        if (BuildConfig.DEBUG)
        {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection()
    {
        closeHelper();
        closeDaoSession();
    }

    public void closeHelper()
    {
        if (sHelper != null)
        {
            sHelper.close();
            sHelper = null;
        }
    }

    public void closeDaoSession()
    {
        if (sDaoSession != null)
        {
            sDaoSession.clear();
            sDaoSession = null;
        }
    }
}

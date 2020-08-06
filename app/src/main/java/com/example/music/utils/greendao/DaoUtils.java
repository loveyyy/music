package com.example.music.utils.greendao;

import android.content.Context;
import android.util.Log;


import com.example.music.model.DaoMaster;
import com.example.music.model.DownLoadInfo;
import com.example.music.model.DownLoadInfoDao;
import com.example.music.model.DownLoadProgree;
import com.example.music.model.PlayingMusicBeens;
import com.example.music.model.PlayingMusicBeensDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Create By morningsun  on 2020-01-09
 */
public class DaoUtils {
    private static final String TAG = DaoUtils.class.getSimpleName();
    private DaoManager mManager;

    public DaoUtils(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    public boolean insertMuisc(PlayingMusicBeens playingMusicBeens){
        boolean flag = false;
        flag = mManager.getDaoSession().getPlayingMusicBeensDao().insert(playingMusicBeens) == -1 ? false : true;
        Log.i(TAG, "insert Meizi :" + flag + "-->" + playingMusicBeens.toString());
        return flag;
    }



    public long insertDownload(DownLoadInfo downLoadInfo){
        return mManager.getDaoSession().getDownLoadInfoDao().insert(downLoadInfo);
    }

    public long insertDownloadTask(DownLoadProgree progree){
        return mManager.getDaoSession().getDownLoadProgreeDao().insert(progree);
    }



    /**
     * 插入多条数据，在子线程操作
     * @return
     */
    public boolean insertMultMuisc(final List<PlayingMusicBeens> playingMusicBeensList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (PlayingMusicBeens playingMusicBeens : playingMusicBeensList) {
                        mManager.getDaoSession().insertOrReplace(playingMusicBeens);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @return
     */
    public boolean updateMuisc(PlayingMusicBeens playingMusicBeens){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(playingMusicBeens);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @return
     */
    public synchronized boolean updateDownload(DownLoadInfo downLoadInfo){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(downLoadInfo);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改一条数据
     * @return
     */
    public synchronized boolean updateDownloadTask(DownLoadProgree downLoadProgree){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(downLoadProgree);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除单条记录
     * @return
     */
    public boolean deleteMessage(PlayingMusicBeens playingMusicBeens){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(playingMusicBeens);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }




    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(PlayingMusicBeens.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<PlayingMusicBeens> queryAllMessage(){
        return mManager.getDaoSession().loadAll(PlayingMusicBeens.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public PlayingMusicBeens queryMessageById(long key){
        return mManager.getDaoSession().load(PlayingMusicBeens.class, key);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public synchronized DownLoadInfo queryDownlodInfo(long key){
        return mManager.getDaoSession().load(DownLoadInfo.class, key);
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public synchronized List<DownLoadInfo> queryDownloadInfoAll(){
        return mManager.getDaoSession().loadAll(DownLoadInfo.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public synchronized DownLoadProgree queryDownlodTask(long key){
        return mManager.getDaoSession().load(DownLoadProgree.class, key);
    }



    /**
     * 使用native sql进行查询操作
     */
    public List<PlayingMusicBeens> queryMessageByNativeSql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(PlayingMusicBeens.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public List<PlayingMusicBeens> queryMessageByQueryBuilder(long id){
        QueryBuilder<PlayingMusicBeens> queryBuilder = mManager.getDaoSession().queryBuilder(PlayingMusicBeens.class);
        return queryBuilder.where(PlayingMusicBeensDao.Properties.Id.eq(id)).list();
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public List<DownLoadInfo> queryDownloadInfoBuilder(int  state){
        QueryBuilder<DownLoadInfo> queryBuilder = mManager.getDaoSession().queryBuilder(DownLoadInfo.class);
        return queryBuilder.where(DownLoadInfoDao.Properties.State.eq(state)).list();
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public DownLoadInfo queryDownloadInfoBuilder(String url){
        QueryBuilder<DownLoadInfo> queryBuilder = mManager.getDaoSession().queryBuilder(DownLoadInfo.class);
        return queryBuilder.where(DownLoadInfoDao.Properties.Url.eq(url)).unique();
    }




//    /**
//     * 使用queryBuilder进行查询
//     * @return
//     */
//    public List<Message> queryMessageByRead(){
//        QueryBuilder<Message> queryBuilder = mManager.getDaoSession().queryBuilder(Message.class);
//        return queryBuilder.where(MessageDao.Properties.Read.eq(0)).list();
//    }
}

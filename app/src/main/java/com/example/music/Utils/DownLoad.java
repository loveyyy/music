package com.example.music.Utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DownLoad {
    //下载器
    private DownloadManager downloadManager;
    //上下文
    private Context mContext;
    //下载的ID
    private long downloadId;
    private File file ;

    public DownLoad(Context context) {
        this.mContext = context;
    }

    //下载apk
    public void downloadAPK(String url,String filename,String name,String json) {
        file = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOWNLOADS,filename);
        if (file.exists()) {
            file.delete();
        }
        writeTxtToFile(json,name+".text");
        Log.e("tag-------------text",json);
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(true);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("歌曲下载");
        request.setDescription(name);
        request.setVisibleInDownloadsUi(true);

        //设置下载的路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
        //获取DownloadManager
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);

        //注册广播接收者，监听下载状态
        mContext.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //广播监听下载的各个状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                //下载延迟
                case DownloadManager.STATUS_PENDING:
                    Toast.makeText(mContext, "下载延迟", Toast.LENGTH_SHORT).show();
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    Toast.makeText(mContext, "正在下载", Toast.LENGTH_SHORT).show();
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    Toast.makeText(mContext,"下载完成:"+file.getPath(),Toast.LENGTH_SHORT).show();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    mContext.unregisterReceiver(receiver);
                    break;
            }
        }
        c.close();
    }
    /**
     * 生成文件
     *
     */
    private static File makeFilePath(String fileName) {
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DOWNLOADS,fileName);
            if (file.exists()) {
                file.delete();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private static boolean writeTxtToFile(String text,String fileName) {
        Boolean isSavaFile=false;
        String strContent = text;
        try {
            File file = makeFilePath(fileName);
            if (!file.exists()) {
                file.delete();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
            isSavaFile = true;
        } catch (Exception e) {
            isSavaFile = false;
            Log.e("TestFile", "Error on write File:" + e);
        }
        return isSavaFile;
    }


}

package com.example.music.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.music.R;
import com.example.music.model.PlayingMusicBeens;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Create By morningsun  on 2020-06-23
 */
public class NotificationUtils {
    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";
    private Notification.Builder builder;
    private NotificationCompat.Builder builder1;

    //单例对象
    private static NotificationUtils instance;


    public NotificationUtils() {

    }

    public static NotificationUtils getInstance() {
        if (instance == null) {
            synchronized (NotificationUtils.class) {
                if (instance == null) {
                    instance = new NotificationUtils();
                }
            }
        }
        return instance;
    }


    @SuppressLint("NewApi")
    public void createNotificationChannel(Context context) {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager(context).createNotificationChannel(channel);
    }

    private NotificationManager getManager(Context context) {
        if (manager == null) {
            manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @SuppressLint("NewApi")
    public Notification.Builder getChannelNotification(Context context, RemoteViews mRemoteViews) {
        Notification.Builder builder = new Notification.Builder(context, id);
        Resources res = context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.ic_icon);
        builder.setLargeIcon(bmp);
        builder.setSmallIcon(R.drawable.ic_icon);
        builder.setCustomContentView(mRemoteViews);
        builder.setCustomBigContentView(mRemoteViews);
        builder.setAutoCancel(false);
        return builder;
    }

    public NotificationCompat.Builder getNotification_25(Context context, RemoteViews mRemoteViews) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_icon);
        Resources res = context.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.ic_icon);
        builder.setLargeIcon(bmp);
        builder.setCustomContentView(mRemoteViews);
        builder.setCustomBigContentView(mRemoteViews);
        builder.setAutoCancel(false);
        return builder;
    }

    public void sendNotification(final PlayingMusicBeens playingMusicBeens, final int num, final Context context) {
        Glide.with(context)
                .asBitmap()
                .load(playingMusicBeens.getPic())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        final RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_play);
                        mRemoteViews.setTextViewText(R.id.tv_name, playingMusicBeens.getMusicname());
                        mRemoteViews.setTextViewText(R.id.tv_name, playingMusicBeens.getMusicname() + "-" + playingMusicBeens.getMusic_singer());
                        mRemoteViews.setImageViewResource(R.id.ib_last, R.drawable.ic_lrc_last);
                        if (PlayController.getInstance().get_state() == 0) {
                            mRemoteViews.setImageViewResource(R.id.ib_play, R.drawable.ic_lrc_stop);
                        } else {
                            mRemoteViews.setImageViewResource(R.id.ib_play, R.drawable.ic_lrc_play);
                        }

                        mRemoteViews.setImageViewResource(R.id.ib_next, R.drawable.ic_lrc_next);
                        mRemoteViews.setImageViewBitmap(R.id.iv_img, resource);

                        Intent intentLast = new Intent("last");
                        PendingIntent pIntentLast = PendingIntent.getBroadcast(context, 0,
                                intentLast, 0);
                        mRemoteViews.setOnClickPendingIntent(R.id.ib_last, pIntentLast);

                        Intent intentPause = new Intent("play");
                        PendingIntent pIntentPause = PendingIntent.getBroadcast(context, 0,
                                intentPause, 0);
                        mRemoteViews.setOnClickPendingIntent(R.id.ib_play, pIntentPause);

                        Intent intentNext = new Intent("next");
                        PendingIntent pIntentNext = PendingIntent.getBroadcast(context, 0,
                                intentNext, 0);
                        mRemoteViews.setOnClickPendingIntent(R.id.ib_next, pIntentNext);

                        Intent intentMain = new Intent("main");//将要跳转的界面
                        PendingIntent pendingIntentMain = PendingIntent.getBroadcast(context, 0, intentMain,0);
                        mRemoteViews.setOnClickPendingIntent(R.id.rl_notic, pendingIntentMain);

                        Intent intentClose = new Intent("close");//将要跳转的界面
                        PendingIntent pendingIntentClose = PendingIntent.getBroadcast(context, 0, intentClose,0);
                        mRemoteViews.setOnClickPendingIntent(R.id.ib_close, pendingIntentClose);

                        if (Build.VERSION.SDK_INT >= 26) {
                            createNotificationChannel(context);
                            builder = getChannelNotification(context, mRemoteViews);
                            builder.build().flags = Notification.FLAG_NO_CLEAR;
                            getManager(context).notify(111, builder.build());
                        } else {
                            builder1 = getNotification_25(context, mRemoteViews);
                            builder1.build().flags = Notification.FLAG_NO_CLEAR;
                            getManager(context).notify(111, builder1.build());
                        }
                    }
                });
    }


    //判断通知权限是否开启
    @SuppressLint("NewApi")
    public boolean isNotificationEnabled(Context context) {
        AppOpsManager mAppOps =
                (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());

            Method checkOpNoThrowMethod =
                    appOpsClass.getMethod("checkOpNoThrow",
                            Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
            int value = (Integer) opPostNotificationValue.get(Integer.class);

            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) ==
                    AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public  boolean isAppForeground(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList =
                activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList == null) {
            Log.d("isAppForeground", "runningAppProcessInfoList is null!");
            return false;
        }

        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
            if (processInfo.processName.equals(context.getPackageName())
                    && (processInfo.importance ==
                    ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)) {
                return true;
            }
        }
        return false;
    }

}

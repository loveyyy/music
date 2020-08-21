package com.example.music.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.music.ui.activity.MainActivity;
import com.example.music.utils.NotificationUtils;
import com.example.music.utils.PlayController;

/**
 * Create By morningsun  on 2020-06-23
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case "last":
                    PlayController.getInstance().playLast();
                    break;
                case "play":
                    PlayController.getInstance().playOrPause();
                    break;
                case "next":
                    PlayController.getInstance().playNext();
                    break;
                case "main":
                    if (!NotificationUtils.getInstance().isAppForeground(context)) {
                       Intent intent1=new Intent(context, MainActivity.class);
                       context.startActivity(intent1);
                    }
                    break;
                case "close":
                    PlayController.getInstance().playOrPause();
                    break;
            }
    }
}

package com.example.music.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.music.utils.PlayController;

/**
 * Create By morningsun  on 2020-06-23
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()){
                case "last":
                    PlayController.getInstance(context).play_last();
                    break;
                case "play":
                    PlayController.getInstance(context).play_Paush();
                    break;
                case "next":
                    PlayController.getInstance(context).play_Next();
                    break;
            }
    }
}

package com.example.music.http;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.music.ui.MyApplication;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Create By morningsun  on 2019-11-21
 */
public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences=MyApplication.getContext().getSharedPreferences("config",
                MyApplication.getContext().MODE_PRIVATE);
        HashSet<String> preferences = (HashSet) sharedPreferences.getStringSet("cookie", null);
        if (preferences != null) {
            builder.header("csrf",sharedPreferences.getString("token",""));
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.v("OkHttp", "Adding Header: " +  cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}

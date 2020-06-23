package com.example.music.http;

import android.content.SharedPreferences;

import com.example.music.ui.MyApplication;
import com.example.music.utils.ACache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Create By morningsun  on 2019-11-21
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        SharedPreferences.Editor config = MyApplication.getContext().getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE)
                .edit();
        ACache aCache=ACache.get(MyApplication.getContext());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            config.putStringSet("cookie", cookies);
            List<String> asd=new ArrayList<>(cookies);
            config.putString("token",  asd.get(0).split(";")[0].split("=")[1]);
            config.commit();
        }

        if(!originalResponse.headers("reqid").isEmpty()){
            aCache.put("reqid",originalResponse.header("reqid"));
        }

        return originalResponse;
    }
}

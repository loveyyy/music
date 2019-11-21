package com.example.music.Utils.Rereofit;

import android.util.Log;

import com.example.music.UI.MyApplication;

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
        HashSet<String> preferences = (HashSet) MyApplication.getContext().getSharedPreferences("config",
                MyApplication.getContext().MODE_PRIVATE).getStringSet("cookie", null);
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                builder.header("csrf",cookie.split(";")[0].split("=")[1]);
//                builder.addHeader("Cookie1","liveCheck=1; path=/; domain=.www.kuwo.cn;");
//                builder.addHeader("Cookie2","watched=433963137; path=/; domain=.www.kuwo.cn;");
//                builder.addHeader("Cookie3","userid=1658163577; path=/; domain=.www.kuwo.cn;");
//                builder.addHeader("Cookie4","websid=3252139571; path=/; domain=.www.kuwo.cn;");
//                builder.addHeader("Cookie5","Hm_lvt_cdb524f42f0ce19b169a8071123a4797=1574307666; path=/; domain=.www.kuwo.cn;");
//                builder.addHeader("Cookie6","Hm_lpvt_cdb524f42f0ce19b169a8071123a4797=1574319068; path=/; domain=.www.kuwo.cn;");
                Log.v("OkHttp", "Adding Header: " +  cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
        return chain.proceed(builder.build());
    }
}

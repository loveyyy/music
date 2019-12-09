package com.example.music.http;

import android.util.Log;

import com.example.music.utils.Constans;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public IRetrofit iRetrofit;
    private static Api mInstance;
    private Retrofit mRetrofit;


    private Api() {
        initRetrofit();
    }

    public static Api getInstance() {
        if (mInstance == null) {
            synchronized (Api.class) {
                if (mInstance == null)
                    mInstance = new Api();
            }
        }
        return mInstance;
    }

    private void initRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger()
        {
            @Override
            public void log(String message)
            {
                Log.d("Http", message+"");
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).
                writeTimeout(20, TimeUnit.SECONDS);
        builder.interceptors().add(new ReceivedCookiesInterceptor());
        builder.interceptors().add(new AddCookiesInterceptor());
        OkHttpClient okHttpClient = builder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constans.IP)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        iRetrofit = mRetrofit.create(IRetrofit.class);
    }
}
package com.sdt.fossilhometest.data.remote;


import com.sdt.fossilhometest.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public final class NetworkHelper {

    static final int TIME_OUT = 10;

    private NetworkHelper() {
    }

    public static Retrofit createRetrofit(String url) {
        OkHttpClient client = createOkHttpClient(
            createHeaderInterceptor(),
            createLoggingInterceptor()
        );
        return builder(client)
            .baseUrl(url)
            .build();
    }

    public static Retrofit.Builder builder(OkHttpClient client) {
        return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client);
    }

    public static OkHttpClient createOkHttpClient(Interceptor header,
                                                  Interceptor logging) {
        return new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(header)
            .addInterceptor(logging)
            .build();
    }

    public static Interceptor createLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(s -> {
            if (BuildConfig.DEBUG)
                Timber.d("OkHttp: %s", s);
        });
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    public static Interceptor createHeaderInterceptor() {
        return chain -> {
            Request request = chain.request();
            HttpUrl newUrl = request.url().newBuilder()
                //add Query Parameters
                .build();
            Request newRequest = request.newBuilder()
                .url(newUrl)
                .header("Content-Type", "application/json")
                .build();
            return chain.proceed(newRequest);
        };
    }
}

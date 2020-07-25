package com.sdt.fossilhometest.di.module;

import android.content.Context;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.remote.NetworkHelper;
import com.sdt.fossilhometest.data.remote.api.UserApi;
import com.sdt.fossilhometest.utils.Constants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @Named(Constants.BASE_URL_NAME)
    String provideBaseUrl(Context context) {
        return context.getString(R.string.base_url);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@Named(Constants.BASE_URL_NAME) String baseUrl) {
        return NetworkHelper.createRetrofit(baseUrl);
    }

    @Provides
    @Singleton
    UserApi provideUserApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }

}

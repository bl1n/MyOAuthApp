package com.lft.myoauthapp.di;

import com.google.gson.Gson;
import com.lft.myoauthapp.BuildConfig;
import com.lft.myoauthapp.api.GithubApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {


    @Provides
    @Singleton
    OkHttpClient provideClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        return builder.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    @Provides
    @Singleton
    GithubApi provideApiService(Retrofit retrofit) {
        return retrofit.create(GithubApi.class);
    }

}

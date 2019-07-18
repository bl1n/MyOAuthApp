package com.lft.myoauthapp;

import android.app.Application;

import com.lft.myoauthapp.di.AppComponent;
import com.lft.myoauthapp.di.AppModule;
import com.lft.myoauthapp.di.DaggerAppComponent;
import com.lft.myoauthapp.di.NetworkModule;

public class AppDelegate extends Application {
    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }
}

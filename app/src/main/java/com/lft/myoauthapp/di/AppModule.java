package com.lft.myoauthapp.di;


import android.content.Context;

import com.lft.myoauthapp.AppDelegate;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final AppDelegate mApp;

    public AppModule(AppDelegate app) {
        mApp = app;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mApp;
    }

    @Provides
    @Singleton
    AppDelegate provideApp() {
        return mApp;
    }


}


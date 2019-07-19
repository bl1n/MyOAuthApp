package com.lft.myoauthapp.di;



import com.lft.myoauthapp.RepoActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(RepoActivity inject);

}

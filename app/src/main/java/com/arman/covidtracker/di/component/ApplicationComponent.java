package com.arman.covidtracker.di.component;

import android.content.res.Resources;

import com.arman.covidtracker.di.module.ApplicationModule;
import com.arman.covidtracker.di.module.NetModule;
import com.arman.covidtracker.service.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, NetModule.class})
public interface ApplicationComponent {

    Retrofit getService();

    ApiService getApiService();

    Resources getResources();

}
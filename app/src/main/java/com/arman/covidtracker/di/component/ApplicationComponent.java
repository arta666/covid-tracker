package com.arman.covidtracker.di.component;

import android.content.res.Resources;

import com.arman.covidtracker.db.AppDb;
import com.arman.covidtracker.di.module.ApplicationModule;
import com.arman.covidtracker.di.module.NetModule;
import com.arman.covidtracker.di.module.RoomModule;
import com.arman.covidtracker.repository.CountryRepository;
import com.arman.covidtracker.repository.NewsRepository;
import com.arman.covidtracker.service.ApiService;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, NetModule.class, RoomModule.class})
public interface ApplicationComponent {

    Retrofit getService();

    ApiService getApiService();

    Resources getResources();

    AppDb getDatabase();

    CountryRepository getCountryRepository();

    NewsRepository getNewsRepository();

}
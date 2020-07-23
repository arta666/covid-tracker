package com.arman.covidtracker.di.module;

import android.app.Application;

import androidx.room.Room;

import com.arman.covidtracker.db.AppDb;
import com.arman.covidtracker.db.CountryDao;
import com.arman.covidtracker.db.NewsDao;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.repository.CountryDataSource;
import com.arman.covidtracker.repository.CountryRepository;
import com.arman.covidtracker.repository.NewsDataSource;
import com.arman.covidtracker.repository.NewsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private AppDb appDb;

    public RoomModule(Application application){
        appDb = Room.databaseBuilder(application,
                AppDb .class, "covide-db").allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    AppDb provideRoomDatabase(){
        return appDb;
    }

    @Singleton
    @Provides
    CountryDao providesCountryDao(AppDb appDb){
        return appDb.countryDao();
    }

    @Singleton
    @Provides
    CountryRepository provideCountryRepository(CountryDao countryDao){
        return new CountryDataSource(countryDao);
    }

    @Singleton
    @Provides
    NewsDao providesNewsDao(AppDb appDb){
        return appDb.newsDao();
    }

    @Singleton
    @Provides
    NewsRepository provideNewsRepository(NewsDao newsDao){
        return new NewsDataSource(newsDao);
    }



}

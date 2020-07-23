package com.arman.covidtracker.di.module;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import com.arman.covidtracker.app.MyApplication;
import com.arman.covidtracker.db.AppDb;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = NetModule.class)
public class ApplicationModule {

    private MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return application.getResources();
    }

    @Provides
    @Singleton
    Locale provideCurrentLocale(Resources resources) {

        Locale locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = resources.getConfiguration().getLocales().get(0);
        } else {
            locale = resources.getConfiguration().locale;
        }

        return locale;
    }



}

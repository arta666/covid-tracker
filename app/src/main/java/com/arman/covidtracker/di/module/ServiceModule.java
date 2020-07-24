package com.arman.covidtracker.di.module;


import com.arman.covidtracker.widget.WidgetService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {

    @ContributesAndroidInjector
    abstract WidgetService contributeWidgetService();
}

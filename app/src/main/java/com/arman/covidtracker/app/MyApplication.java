package com.arman.covidtracker.app;

import android.app.Application;

import com.arman.covidtracker.di.component.ApplicationComponent;
import com.arman.covidtracker.di.component.DaggerApplicationComponent;
import com.arman.covidtracker.di.module.ApplicationModule;
import com.arman.covidtracker.di.module.NetModule;
import com.arman.covidtracker.di.module.RoomModule;


public class MyApplication extends Application {


    private ApplicationComponent component;

    private static MyApplication mInstance =null;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //Dagger
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule(this,Endpoints.BASE_URL))
                .roomModule(new RoomModule(this))
                .build();
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}

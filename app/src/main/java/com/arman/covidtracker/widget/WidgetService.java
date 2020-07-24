package com.arman.covidtracker.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.arman.covidtracker.app.MyApplication;
import com.arman.covidtracker.db.NewsDao;
import com.arman.covidtracker.model.News;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class WidgetService extends RemoteViewsService {

    private static final String TAG = WidgetService.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: widget service called");
        return new WidgetRemoteViewsFactory(getApplicationContext(), intent);
    }
}
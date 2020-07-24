package com.arman.covidtracker.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.arman.covidtracker.R;
import com.arman.covidtracker.app.Constants;
import com.arman.covidtracker.app.MyApplication;
import com.arman.covidtracker.db.AppDb;
import com.arman.covidtracker.db.NewsDao;
import com.arman.covidtracker.model.News;
import com.arman.covidtracker.utils.DateConverter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = WidgetRemoteViewsFactory.class.getSimpleName();
    private List<News> newsList = new ArrayList<>();
    private Context context;
    private int appWidgetId;


    NewsDao newsDao;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        newsDao = MyApplication.getInstance().getComponent().getNewsDao();
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        List<News> news = newsDao.getNews();
        if (news != null) {
            this.newsList.clear();
            this.newsList.addAll(news);
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        List<News> news = newsDao.getNews();
        if (news != null) {
            this.newsList.clear();
            this.newsList.addAll(news);
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.item_widget);

        News news = newsList.get(position);
        row.setTextViewText(R.id.title, news.getTitle());
        String createdDate = DateConverter.getDefaultDate(context,news.getAddedOn());
        row.setTextViewText(R.id.subtitle, createdDate);

        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putBoolean(Constants.WIDGET_NEWS, true);
        extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtras(extras);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
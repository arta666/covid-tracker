package com.arman.covidtracker.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.arman.covidtracker.R;
import com.arman.covidtracker.app.Constants;
import com.arman.covidtracker.ui.activity.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class CovidTrackerWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.widget_title);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.covid_tracker_widget);
        views.setTextViewText(R.id.tvTitle, widgetText);

        Intent widgetService = new Intent(context, WidgetService.class);
        widgetService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        widgetService.setData(Uri.parse(widgetService.toUri(Intent.URI_INTENT_SCHEME)));


        views.setRemoteAdapter(R.id.news_list, widgetService);

        Intent clickIntent = new Intent(context, MainActivity.class);
        clickIntent.putExtra(Constants.WIDGET_NEWS, true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.tvTitle, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, CovidTrackerWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.news_list);
        }
        super.onReceive(context, intent);
    }
}


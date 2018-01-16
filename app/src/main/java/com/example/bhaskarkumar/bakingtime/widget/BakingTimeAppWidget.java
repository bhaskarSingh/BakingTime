package com.example.bhaskarkumar.bakingtime.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.bhaskarkumar.bakingtime.IngredientsList;
import com.example.bhaskarkumar.bakingtime.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingTimeAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews views = getBakingListRemoteView(context, appWidgetId);
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

    private static RemoteViews getBakingListRemoteView(Context context,
                                                       int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_time_app_widget);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        views.setRemoteAdapter(R.id.widgetListView, intent);

        Intent startIntent = new Intent(context, IngredientsList.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widgetListView, pendingIntent);

        views.setEmptyView(R.id.widgetListView, R.id.empty_view);
        return views;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


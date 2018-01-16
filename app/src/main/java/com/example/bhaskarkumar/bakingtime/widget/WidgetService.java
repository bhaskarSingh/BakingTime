package com.example.bhaskarkumar.bakingtime.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListProvider(getApplicationContext() , intent);
    }
}

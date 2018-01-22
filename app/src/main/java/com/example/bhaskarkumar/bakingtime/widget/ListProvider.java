package com.example.bhaskarkumar.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bhaskarkumar.bakingtime.Communicator;
import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.object.Bake;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.bhaskarkumar.bakingtime.MainActivity.Base_url;

class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private int id;
    Context mContext;
    ArrayList<Ingredients> ingredients = new ArrayList<>();
    private boolean res = true;
    private int widgetID;
    private ArrayList<Bake> mArrayList = new ArrayList<>();
    public ListProvider(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        widgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Communicator communicator = retrofit.create(Communicator.class);

        Call<List<Bake>> call = communicator.bakeItems();
        call.enqueue(new Callback<List<Bake>>() {
            @Override
            public void onResponse(Call<List<Bake>> call, Response<List<Bake>> response) {
                //Check is web response was successful or not
                if (response.isSuccessful()){
                    // Loop through json and store value into arrayList
                    if (mArrayList.isEmpty()) {
                        for (int i = 0 ; i < response.body().size() ; i++) {
                            Bake bake = response.body().get(i);
                            mArrayList.add(new Bake(bake.getId(), bake.getName(),
                                    bake.getSteps(), bake.getIngredients(), bake.getImage()));
                        }
                        id =WidgetConfigActivity.loadTitlePref(mContext, widgetID);
                        ingredients = (ArrayList<Ingredients>) mArrayList.get(id).getIngredients();
                        if (res) {
                            AppWidgetManager manager = AppWidgetManager.getInstance(mContext);
                            int[] widgetsInts = manager.getAppWidgetIds(new ComponentName(mContext,
                                    BakingTimeAppWidget.class));
                            manager.notifyAppWidgetViewDataChanged(widgetsInts, R.id.widgetListView);
                            BakingTimeAppWidget.updateAppWidget(mContext, manager, widgetID);
                            res = false;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Bake>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        ingredients.clear();
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_name);

        double quantity = ingredients.get(position).getQuantity();
        String measure = ingredients.get(position).getMeasure();
        String ingredient = ingredients.get(position).getIngredient();
        remoteViews.setTextViewText(R.id.IngreMeasureTVWidget, measure);
        remoteViews.setTextViewText(R.id.IngreQuantityTVWidget, String.valueOf(quantity));
        remoteViews.setTextViewText(R.id.IgreIngredientsTVWidget, ingredient);


        return remoteViews;
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

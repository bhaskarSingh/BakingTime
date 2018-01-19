package com.example.bhaskarkumar.bakingtime.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bhaskarkumar.bakingtime.Communicator;
import com.example.bhaskarkumar.bakingtime.MainActivity;
import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.RecipeSteps;
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
    Context mContext;
    ArrayList<Bake> mArrayList;
    ArrayList<Ingredients> ingredients;
    private String bake;
    private boolean res = true;
    private int widgetID;
    public ListProvider(Context applicationContext, Intent intent) {
        mArrayList = new ArrayList<>();
        ingredients = new ArrayList<>();
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
                        for (Bake bake : response.body()) {
                            mArrayList.add(new Bake(bake.getId(), bake.getName(),
                                    bake.getSteps(), bake.getIngredients(), bake.getImage()));
                        }

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
        mArrayList.clear();
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_recipe_name);

        remoteViews.setTextViewText(R.id.recipeNameTVwidget, mArrayList.get(position).getName() + "");

        Intent fillInIntent = new Intent();

        bake = mArrayList.get(position).getName();
        ingredients = (ArrayList<Ingredients>) mArrayList.get(position).getIngredients();

        fillInIntent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
        fillInIntent.putExtra(RecipeSteps.INGREDIENTS_LIST_KEY, ingredients);

        remoteViews.setOnClickFillInIntent(R.id.recipeNameTVwidget, fillInIntent);

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

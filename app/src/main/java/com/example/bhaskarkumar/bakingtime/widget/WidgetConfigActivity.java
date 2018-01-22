package com.example.bhaskarkumar.bakingtime.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bhaskarkumar.bakingtime.Communicator;
import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.object.Bake;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.bhaskarkumar.bakingtime.MainActivity.Base_url;

public class WidgetConfigActivity extends Activity {
    private static final String PREFS_NAME = "com.example.bhaskarkumar.bakingtime";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static int count = 1;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ArrayList<Bake> mArrayList = new ArrayList<>();
    private ArrayList<String> recipeNames = new ArrayList<>();

    public WidgetConfigActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int text, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
//        Random random = new Random();
//        count = random.nextInt(1000);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int titleValue = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, 0);
        if (titleValue != -1) {
            return titleValue;
        } else {
            return -1;
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveRecipeNamePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME + "N", 0).edit();
        prefs.putString(PREF_PREFIX_KEY + "R" + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadRecipeNamePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME + "N", 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + "R" + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        getRecipeList();
        setContentView(R.layout.widget_config_view);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

    }
    public void getRecipeList(){
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
                            recipeNames.add(bake.getName());
                        }
                    }
                    ListView listView = findViewById(R.id.widgetListViewConfig);
                    listView.setAdapter(new ArrayAdapter<>(WidgetConfigActivity.this,
                            android.R.layout.simple_list_item_1,recipeNames));

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                            Context context = WidgetConfigActivity.this;

                            int isd = mArrayList.get(position).getId() - 1;
                            saveTitlePref(context, isd, mAppWidgetId);
                            saveRecipeNamePref(context, mAppWidgetId, mArrayList.get(isd).getName());

                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                            BakingTimeAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                            // Make sure we pass back the original appWidgetId
                            Intent resultValue = new Intent();
                            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                            setResult(RESULT_OK, resultValue);

                            finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Bake>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}

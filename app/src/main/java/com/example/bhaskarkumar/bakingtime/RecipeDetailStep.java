package com.example.bhaskarkumar.bakingtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bhaskarkumar.bakingtime.object.Steps;

public class RecipeDetailStep extends AppCompatActivity {

    private static final String LOG_TAG = RecipeDetailStep.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_step);

        Steps step = getIntent().getExtras().getParcelable(RecipeSteps.STEPS_DETAIL_KEY);
        String bake = getIntent().getStringExtra(MainActivity.RECIPE_NAME_KEY);
        setTitle(bake);
        Log.i(LOG_TAG, step.getShortDescription() + " ");
        Log.i(LOG_TAG, step.getDescription() + " ");
        Log.i(LOG_TAG, step.getThumbnailURL() + " ");
        Log.i(LOG_TAG, step.getVideoURL() + " ");
    }
}

package com.example.bhaskarkumar.bakingtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.bhaskarkumar.bakingtime.fragment.RecipeDetailStepFragment;
import com.example.bhaskarkumar.bakingtime.object.Steps;

/**
 * This activity opens when RecipeSteps(activity-fragment) steps list item is clicked.
 */
public class RecipeDetailStep extends AppCompatActivity {

    private static final String LOG_TAG = RecipeDetailStep.class.getSimpleName();
    private static final String RECIPE_TITLE = "recipe-title";
    private String bake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_steps_listactivity_activity);


        Steps step = getIntent().getExtras().getParcelable(RecipeSteps.STEPS_DETAIL_KEY);
        bake = getIntent().getStringExtra(MainActivity.RECIPE_NAME_KEY);
        setTitle(bake);
        Log.i(LOG_TAG, step.getShortDescription() + " ");
        Log.i(LOG_TAG, step.getDescription() + " ");
        Log.i(LOG_TAG, step.getThumbnailURL() + " ");
        Log.i(LOG_TAG, step.getVideoURL() + " ");


        if (savedInstanceState == null) {
            //Create RecipeDetailFragment
            RecipeDetailStepFragment fragment = new RecipeDetailStepFragment();
            fragment.setSteps(step);
            //Attach fragment to the activity dynamically
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_detail_fragment_view, fragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(RECIPE_TITLE, bake);
        super.onSaveInstanceState(outState);
    }
}

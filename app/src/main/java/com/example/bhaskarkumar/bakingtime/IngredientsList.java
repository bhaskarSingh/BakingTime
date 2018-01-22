package com.example.bhaskarkumar.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.bhaskarkumar.bakingtime.fragment.FragmentIngredients;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;

import java.util.ArrayList;

public class IngredientsList extends AppCompatActivity {

    private static final String LOG_TAG = IngredientsList.class.getSimpleName();
    private static final String INGREDIENTS_FRAGMENT_KEY = "ingredients-fragment-key";
    private ArrayList<Ingredients> ingredients;
    private FragmentIngredients fragmentIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_steps_listactivity_activity);
        Intent intent = getIntent();
        if (intent != null){
             ingredients = intent.getParcelableArrayListExtra(RecipeSteps.INGREDIENTS_LIST_KEY);
            String bake = intent.getStringExtra(MainActivity.RECIPE_NAME_KEY);
             setTitle(bake);
            Log.i(LOG_TAG, ingredients.size() + "");
        }

        if (savedInstanceState != null){
            fragmentIngredients =
                    (FragmentIngredients) getSupportFragmentManager()
                            .getFragment(savedInstanceState, INGREDIENTS_FRAGMENT_KEY);
        }else {
            fragmentIngredients = new FragmentIngredients();
        }
        fragmentIngredients.setIngredients(ingredients);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.steps_detail_fragment_view, fragmentIngredients)
                .commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, INGREDIENTS_FRAGMENT_KEY, fragmentIngredients);
        super.onSaveInstanceState(outState);
    }
}

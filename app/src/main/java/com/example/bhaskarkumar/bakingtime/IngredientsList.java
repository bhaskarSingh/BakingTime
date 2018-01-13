package com.example.bhaskarkumar.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.bhaskarkumar.bakingtime.adapter.IngredientsListAdapter;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;

import java.util.ArrayList;

public class IngredientsList extends AppCompatActivity {

    private static final String LOG_TAG = IngredientsList.class.getSimpleName();
    private ArrayList<Ingredients> ingredients;
    private RecyclerView recipeRV;
    private IngredientsListAdapter mIngredientsListAdapter;
    private String bake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_ingredient_list);
        Intent intent = getIntent();
        if (intent != null){
             ingredients = intent.getParcelableArrayListExtra(RecipeSteps.INGREDIENTS_LIST_KEY);
             bake = intent.getStringExtra(MainActivity.RECIPE_NAME_KEY);
             setTitle(bake);
            Log.i(LOG_TAG, ingredients.size() + "");
        }

        recipeRV = findViewById(R.id.ingredientsListRecyclerView);
        mIngredientsListAdapter = new IngredientsListAdapter(ingredients);
        recipeRV.setLayoutManager(new LinearLayoutManager(this));
        recipeRV.setAdapter(mIngredientsListAdapter);
    }
}

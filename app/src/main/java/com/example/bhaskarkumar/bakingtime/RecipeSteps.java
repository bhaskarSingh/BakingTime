package com.example.bhaskarkumar.bakingtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bhaskarkumar.bakingtime.object.Ingredients;
import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;
import java.util.List;

public class RecipeSteps extends AppCompatActivity {
    private static final String LOG_TAG = RecipeSteps.class.getSimpleName();
    private RecyclerView recipeRV;
    private RecipeStepAdapter mRecipeStepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        String bake = getIntent().getStringExtra(MainActivity.RECIPE_NAME_KEY);
        setTitle(bake);
        List<Steps> steps = getIntent().getExtras()
                .getParcelableArrayList(MainActivity.RECIPE_STEPS_KEY);

        List<Ingredients> ingredients = getIntent().getExtras()
                .getParcelableArrayList(MainActivity.RECIPE_INGREDIENTS_KEY);


        recipeRV = findViewById(R.id.recipe_recycler_view);
        mRecipeStepAdapter = new RecipeStepAdapter((ArrayList<Steps>) steps);
        recipeRV.setLayoutManager(new LinearLayoutManager(this));
        recipeRV.setAdapter(mRecipeStepAdapter);

    }
}

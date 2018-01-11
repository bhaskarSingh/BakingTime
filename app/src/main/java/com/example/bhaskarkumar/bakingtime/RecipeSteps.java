package com.example.bhaskarkumar.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bhaskarkumar.bakingtime.adapter.RecipeStepAdapter;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;
import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;
import java.util.List;

public class RecipeSteps extends AppCompatActivity implements RecipeStepAdapter.setRecipeStepClickListener{
    private static final String LOG_TAG = RecipeSteps.class.getSimpleName();
    public static final String STEPS_DETAIL_KEY = "steps-detail-key";
    private RecyclerView recipeRV;
    private RecipeStepAdapter mRecipeStepAdapter;
    private List<Steps> steps;
    private List<Ingredients> ingredients;
    private String bake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        bake = getIntent().getStringExtra(MainActivity.RECIPE_NAME_KEY);
        setTitle(bake);
         steps = getIntent().getExtras()
                .getParcelableArrayList(MainActivity.RECIPE_STEPS_KEY);

         ingredients = getIntent().getExtras()
                .getParcelableArrayList(MainActivity.RECIPE_INGREDIENTS_KEY);


        recipeRV = findViewById(R.id.recipe_recycler_view);
        mRecipeStepAdapter = new RecipeStepAdapter((ArrayList<Steps>) steps, this);
        recipeRV.setLayoutManager(new LinearLayoutManager(this));
        recipeRV.setAdapter(mRecipeStepAdapter);

    }

    @Override
    public void onRecipeCLick(int position) {
        Steps step = steps.get(position);
        Intent intent = new Intent(this, RecipeDetailStep.class);
        intent.putExtra(STEPS_DETAIL_KEY, step);
        intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
        startActivity(intent);
    }
}

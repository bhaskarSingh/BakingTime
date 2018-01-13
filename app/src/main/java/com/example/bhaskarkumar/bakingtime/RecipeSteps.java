package com.example.bhaskarkumar.bakingtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.bhaskarkumar.bakingtime.adapter.RecipeStepAdapter;
import com.example.bhaskarkumar.bakingtime.fragment.FragmentRecipeSteps;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;
import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;
import java.util.List;

public class RecipeSteps extends AppCompatActivity{

    private static final String LOG_TAG = RecipeSteps.class.getSimpleName();
    public static final String STEPS_DETAIL_KEY = "steps-detail-key";
    public static final String INGREDIENTS_LIST_KEY = "ingredients-list-key";
    private RecyclerView recipeRV;
    private RecipeStepAdapter mRecipeStepAdapter;
    private List<Steps> steps;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();
    private String bake;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_steps_activity_static);

        bake = getIntent().getStringExtra(MainActivity.RECIPE_NAME_KEY);
        setTitle(bake);
        steps = getIntent().getExtras()
                .getParcelableArrayList(MainActivity.RECIPE_STEPS_KEY);

        ingredients = getIntent().getExtras()
                .getParcelableArrayList(MainActivity.RECIPE_INGREDIENTS_KEY);

        FragmentRecipeSteps fragmentRecipeSteps = new FragmentRecipeSteps();
        fragmentRecipeSteps.setSteps(steps, bake);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.recipe_steps_activity_fragment_static_view, fragmentRecipeSteps)
                .commit();
    }


}

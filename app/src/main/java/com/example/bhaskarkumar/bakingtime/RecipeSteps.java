package com.example.bhaskarkumar.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.bhaskarkumar.bakingtime.adapter.RecipeStepAdapter;
import com.example.bhaskarkumar.bakingtime.fragment.FragmentRecipeSteps;
import com.example.bhaskarkumar.bakingtime.fragment.RecipeDetailStepFragment;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;
import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;
import java.util.List;

public class RecipeSteps extends AppCompatActivity {

    private static final String LOG_TAG = RecipeSteps.class.getSimpleName();
    public static final String STEPS_DETAIL_KEY = "steps-detail-key";
    public static final String INGREDIENTS_LIST_KEY = "ingredients-list-key";
    private RecyclerView recipeRV;
    private RecipeStepAdapter mRecipeStepAdapter;
    private List<Steps> steps;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();
    private String bake;
    private CardView mCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_steps_activity_static);

        recipeRV = findViewById(R.id.recipe_recycler_view);
        mCardView = findViewById(R.id.ingredientsButton);
        bake = getIntent().getStringExtra(MainActivity.RECIPE_NAME_KEY);
        setTitle(bake);
        steps = getIntent().getExtras()
                .getParcelableArrayList(MainActivity.RECIPE_STEPS_KEY);

        ingredients = getIntent().getExtras()
                .getParcelableArrayList(MainActivity.RECIPE_INGREDIENTS_KEY);

        FragmentRecipeSteps fragmentRecipeSteps = new FragmentRecipeSteps();
        fragmentRecipeSteps.setSteps(steps, bake);
        fragmentRecipeSteps.setTouchListener(getRecyclerTouchListener());



        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.recipe_steps_activity_fragment_static_view, fragmentRecipeSteps)
                .commit();




    }

    public RecyclerTouchListener getRecyclerTouchListener(){
        RecyclerTouchListener recyclerTouchListener =
                new RecyclerTouchListener(this, recipeRV,
                        new FragmentRecipeSteps.SetOnRecipeItemClickListener() {

                            @Override
                            public void onStepClick(View view, int position) {
                                Steps step = steps.get(position);
                                if (findViewById(R.id.steps_detail_fragment_view) != null) {
                                    RecipeDetailStepFragment fragment = new RecipeDetailStepFragment();
                                    fragment.setSteps(step);

                                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.steps_detail_fragment_view, fragment)
                                            .commit();
                                }else {

                                    Intent intent = new Intent(RecipeSteps.this, RecipeDetailStep.class);
                                    intent.putExtra(STEPS_DETAIL_KEY, step);
                                    intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
                                    startActivity(intent);
                                }
                            }
                        });
        return recyclerTouchListener;
    }

    public void onIngredientButtonClick(){
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findViewById(R.id.steps_detail_fragment_view) != null) {

                }else {
                    Intent intent = new Intent(RecipeSteps.this, IngredientsList.class);
                    intent.putExtra(INGREDIENTS_LIST_KEY, ingredients);
                    intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
                    startActivity(intent);
                }
            }
        });
    }


}

package com.example.bhaskarkumar.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.bhaskarkumar.bakingtime.adapter.RecipeStepAdapter;
import com.example.bhaskarkumar.bakingtime.fragment.FragmentRecipeSteps;
import com.example.bhaskarkumar.bakingtime.fragment.RecipeDetailStepFragment;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;
import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;
import java.util.List;

/**
 * Opens Recipe's detail view and depending on whether it's tablet view or not displays fragment accordingly.
 */
public class RecipeSteps extends AppCompatActivity {

    private static final String LOG_TAG = RecipeSteps.class.getSimpleName();
    public static final String STEPS_DETAIL_KEY = "steps-detail-key";
    public static final String INGREDIENTS_LIST_KEY = "ingredients-list-key";
    private static final String RECIPE_STEPS_FRAGMENT_KEY = "recipe-steps-fragment-key";
    private RecyclerView recipeRV;
    private RecipeStepAdapter mRecipeStepAdapter;
    private List<Steps> steps;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();
    private String bake;
    private FragmentRecipeSteps fragmentRecipeSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_steps_activity_static);

        recipeRV = findViewById(R.id.recipe_recycler_view);

        //Get clicked recipe's title from the main activity
        bake = getIntent().getStringExtra(MainActivity.RECIPE_NAME_KEY);

        //Get clicked recipe's ingredients array list
        ingredients = getIntent().getParcelableArrayListExtra(MainActivity.RECIPE_INGREDIENTS_KEY);

        steps = getIntent().getParcelableArrayListExtra(MainActivity.RECIPE_STEPS_KEY);

        //Sets recipe title as action bar title
        setTitle(bake);

        //Create recipes steps detail fragment and send steps,
        // bake and ingredients and touchListener to fragment
        if (savedInstanceState != null){
            fragmentRecipeSteps =
                    (FragmentRecipeSteps)
                            getSupportFragmentManager().getFragment(savedInstanceState
                                    , RECIPE_STEPS_FRAGMENT_KEY);
        }else {
            fragmentRecipeSteps = new FragmentRecipeSteps();
        }
        fragmentRecipeSteps.setSteps(steps, bake, ingredients);
        fragmentRecipeSteps.setTouchListener(getRecyclerTouchListener());


        //Attach fragment created above to the activity dynamically
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.recipe_steps_activity_fragment_static_view, fragmentRecipeSteps)
                .commit();


    }

    /**
     * Implements functionality for when recipe's steps recycler view item is clicked
     * to either open new activity or create new fragment depending on whether its a tablet
     * or not.
     * @return RecyclerTouchListener
     */
    public RecyclerTouchListener getRecyclerTouchListener(){
        return new RecyclerTouchListener(this, recipeRV,
                new FragmentRecipeSteps.SetOnRecipeItemClickListener() {

                    @Override
                    public void onStepClick(View view, int position) {
                        //Gets particular value from list of steps
                        Steps step = steps.get(position);
                        //checks whether its tablet or phone
                        if (findViewById(R.id.steps_detail_fragment_view) != null) {
                            //create RecipeDetailStepFragment and attach to the activity
                            RecipeDetailStepFragment fragment = new RecipeDetailStepFragment();
                            fragment.setSteps(step);
                            //creates fragment dynamically
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.steps_detail_fragment_view, fragment)
                                    .commit();
                        }else {
                            //open recipeDetailStep activity
                            Intent intent = new Intent(RecipeSteps.this, RecipeDetailStep.class);
                            intent.putExtra(STEPS_DETAIL_KEY, step);
                            intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState, RECIPE_STEPS_FRAGMENT_KEY, fragmentRecipeSteps);
        super.onSaveInstanceState(outState);
    }
}

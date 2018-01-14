package com.example.bhaskarkumar.bakingtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhaskarkumar.bakingtime.IngredientsList;
import com.example.bhaskarkumar.bakingtime.MainActivity;
import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.RecipeSteps;
import com.example.bhaskarkumar.bakingtime.RecyclerTouchListener;
import com.example.bhaskarkumar.bakingtime.adapter.RecipeStepAdapter;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;
import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;
import java.util.List;

public class FragmentRecipeSteps extends Fragment implements View.OnClickListener{

    private static final String LOG_TAG = RecipeSteps.class.getSimpleName();
    public static final String STEPS_DETAIL_KEY = "steps-detail-key";
    public static final String INGREDIENTS_LIST_KEY = "ingredients-list-key";
    private RecyclerView recipeRV;
    private RecipeStepAdapter mRecipeStepAdapter;
    private ArrayList<Steps> steps;
    private ArrayList<Ingredients> ingredients;
    private String bake;
    private RecyclerTouchListener recyclerTouchListener;
    private CardView mCardView;
    public FragmentRecipeSteps(){

    }

    /**
     * When ingredients view is clicked either open new activity
     * or create new ingredients fragment and attach to the current
     * activity depending whether its tablet or phone.
     * @param view
     */
    @Override
    public void onClick(View view) {
        //check whether it's tablet or phone
        //If the view(R.id.steps_detail_fragment_view) is present than its tablet else its phone
        if (getActivity().findViewById(R.id.steps_detail_fragment_view) != null){
            FragmentIngredients fragmentIngredients = new FragmentIngredients();
            fragmentIngredients.setIngredients(ingredients);
            //create fragment dynamically and attach to the current activity
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_detail_fragment_view, fragmentIngredients)
                    .commit();
        }else {
            //open IngredientsList activity
            Intent intent = new Intent(getContext(), IngredientsList.class);
            //ingredients variable contains list of ingredients of particular recipe
            intent.putExtra(INGREDIENTS_LIST_KEY, ingredients);
            //bake is the string value containing recipe's title
            intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
            startActivity(intent);
        }
    }

    /**
     * Interface to interact b/w fragment(this) and activity(RecipeSteps activity)
     */
    public interface SetOnRecipeItemClickListener{
        void onStepClick(View view, int position);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        //set value to local variable on orientation change
        if (savedInstanceState != null){
            steps = savedInstanceState.getParcelableArrayList("a");
            ingredients = savedInstanceState.getParcelableArrayList("b");
            bake = savedInstanceState.getString("c");
        }
        //Get recyclerView reference
        recipeRV = view.findViewById(R.id.recipe_recycler_view);
        //initialize RecipeStepAdapter
        mRecipeStepAdapter = new RecipeStepAdapter(steps);
        recipeRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Attach TouchListener to steps Recycler view
        recipeRV.addOnItemTouchListener(recyclerTouchListener);
        recipeRV.setAdapter(mRecipeStepAdapter);

        //Get reference of mCardView(will open on click recipe's Ingredients's list)
        // and set OnClickListener on it.
        mCardView = view.findViewById(R.id.ingredientsButton);
        mCardView.setOnClickListener(this);

        return view;
    }

    /**
     * Get Saves steps, ingredients and bake(contains selected recipe's tile)
     * variables from parent activity(RecipeSteps).
     * @param mSteps
     * @param Bake
     * @param ingredient
     */
    public void setSteps(List<Steps> mSteps, String Bake, ArrayList<Ingredients> ingredient){
        steps = (ArrayList<Steps>) mSteps;
        bake = Bake;
        ingredients = ingredient;
    }

    /**
     * Get recyclerTouchListener reference from the parent activity
     * @param touchListener
     */
    public void setTouchListener(RecyclerTouchListener touchListener){
        recyclerTouchListener = touchListener;
    }

    /**
     * Saves steps, ingredients and bake(contains selected recipe's tile)
     * variables on orientation change
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("a", steps);
        outState.putParcelableArrayList("b", ingredients);
        outState.putString("c", bake);
        super.onSaveInstanceState(outState);
    }
}

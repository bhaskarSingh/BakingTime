package com.example.bhaskarkumar.bakingtime.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhaskarkumar.bakingtime.MainActivity;
import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.RecipeDetailStep;
import com.example.bhaskarkumar.bakingtime.RecipeSteps;
import com.example.bhaskarkumar.bakingtime.RecyclerTouchListener;
import com.example.bhaskarkumar.bakingtime.adapter.RecipeStepAdapter;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;
import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;
import java.util.List;

public class FragmentRecipeSteps extends Fragment{

    private static final String LOG_TAG = RecipeSteps.class.getSimpleName();
    public static final String STEPS_DETAIL_KEY = "steps-detail-key";
    public static final String INGREDIENTS_LIST_KEY = "ingredients-list-key";
    private RecyclerView recipeRV;
    private RecipeStepAdapter mRecipeStepAdapter;
    private List<Steps> steps;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();
    private String bake;

    public FragmentRecipeSteps(){

    }

    public interface SetOnRecipeItemClickListener{
        void onStepClick(View view, int position);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        recipeRV = view.findViewById(R.id.recipe_recycler_view);
        mRecipeStepAdapter = new RecipeStepAdapter((ArrayList<Steps>) steps);
        recipeRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerTouchListener recyclerTouchListener =
                new RecyclerTouchListener(getActivity(), recipeRV, new SetOnRecipeItemClickListener() {
                    
            @Override
            public void onStepClick(View view, int position) {
                Steps step = steps.get(position);
        Intent intent = new Intent(getContext(), RecipeDetailStep.class);
        intent.putExtra(STEPS_DETAIL_KEY, step);
        intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
        startActivity(intent);
            }
        });
        recipeRV.addOnItemTouchListener(recyclerTouchListener);
        recipeRV.setAdapter(mRecipeStepAdapter);

        return view;
    }

//    @Override
//    public void onRecipeCLick(int position) {
//        Steps step = steps.get(position);
//        Intent intent = new Intent(getContext(), RecipeDetailStep.class);
//        intent.putExtra(STEPS_DETAIL_KEY, step);
//        intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
//        startActivity(intent);
//    }

//    public void onIngredientsClick(View view) {
//        Intent intent = new Intent(getContext(), IngredientsList.class);
//        intent.putExtra(INGREDIENTS_LIST_KEY, ingredients);
//        intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
//        startActivity(intent);
//    }

    public void setSteps(List<Steps> mSteps, String Bake){
        steps = mSteps;
        bake = Bake;
    }
}

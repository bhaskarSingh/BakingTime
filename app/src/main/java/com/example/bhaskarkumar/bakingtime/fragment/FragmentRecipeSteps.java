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
    private List<Steps> steps;
    private ArrayList<Ingredients> ingredients = new ArrayList<>();
    private String bake;
    private RecyclerTouchListener recyclerTouchListener;
    private CardView mCardView;
    public FragmentRecipeSteps(){

    }

    @Override
    public void onClick(View view) {
        if (getActivity().findViewById(R.id.steps_detail_fragment_view) != null){
            FragmentIngredients fragmentIngredients = new FragmentIngredients();
            fragmentIngredients.setIngredients(ingredients);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_detail_fragment_view, fragmentIngredients)
                    .commit();
        }else {
            Intent intent = new Intent(getContext(), IngredientsList.class);
            intent.putExtra(INGREDIENTS_LIST_KEY, ingredients);
            intent.putExtra(MainActivity.RECIPE_NAME_KEY, bake);
            startActivity(intent);
        }
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
        recipeRV.addOnItemTouchListener(recyclerTouchListener);
        recipeRV.setAdapter(mRecipeStepAdapter);
        mCardView = view.findViewById(R.id.ingredientsButton);
        mCardView.setOnClickListener(this);
        return view;
    }

    public void setSteps(List<Steps> mSteps, String Bake, ArrayList<Ingredients> ingredient){
        steps = mSteps;
        bake = Bake;
        ingredients = ingredient;
    }
    public void setTouchListener(RecyclerTouchListener touchListener){
        recyclerTouchListener = touchListener;
    }
}

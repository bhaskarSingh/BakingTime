package com.example.bhaskarkumar.bakingtime.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhaskarkumar.bakingtime.IngredientsList;
import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.adapter.IngredientsListAdapter;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;

import java.util.ArrayList;

public class FragmentIngredients extends Fragment {

    private static final String LOG_TAG = IngredientsList.class.getSimpleName();
    private ArrayList<Ingredients> ingredients;
    private RecyclerView recipeRV;
    private IngredientsListAdapter mIngredientsListAdapter;
    private String bake;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_ingredient_list, container, false);

        recipeRV = view.findViewById(R.id.ingredientsListRecyclerView);
        mIngredientsListAdapter = new IngredientsListAdapter(ingredients);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recipeRV.setLayoutManager(layoutManager);
        recipeRV.setAdapter(mIngredientsListAdapter);

        return view;
    }

    public void setIngredients(ArrayList<Ingredients> ingredient){
        ingredients = ingredient;
    }
}

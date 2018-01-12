package com.example.bhaskarkumar.bakingtime.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;

import java.util.ArrayList;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.RecipeStepsViewHolder> {

    private ArrayList<Ingredients> mIngredients;
    public IngredientsListAdapter(ArrayList<Ingredients> ingredients){
        mIngredients= ingredients;

    }


    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list_item, parent, false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int position) {
        holder.mQuantityTV.setText(""+mIngredients.get(position).getQuantity());
        holder.mMeasureTV.setText(mIngredients.get(position).getMeasure());
        holder.mIngredients.setText(mIngredients.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        if (mIngredients== null || mIngredients.size() == 0){
            return 0;
        }
        return mIngredients.size();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {
        TextView mQuantityTV, mMeasureTV, mIngredients;
        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            mQuantityTV = itemView.findViewById(R.id.IngreQuantityTV);
            mMeasureTV = itemView.findViewById(R.id.IngreMeasureTV);
            mIngredients= itemView.findViewById(R.id.IgreIngredientsTV);
        }

    }
}
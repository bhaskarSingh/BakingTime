package com.example.bhaskarkumar.bakingtime;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepsViewHolder> {

    private ArrayList<Steps> mSteps;
    public RecipeStepAdapter(ArrayList<Steps> steps){
        mSteps = steps;

    }

    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_steps_list_item, parent, false);
        return new RecipeStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int position) {
        holder.mStepsTV.setText(mSteps.get(position).getShortDescription());
        holder.mIdTV.setText(mSteps.get(position).getId() + ".");
    }

    @Override
    public int getItemCount() {
        if (mSteps == null || mSteps.size() == 0){
            return 0;
        }
        return mSteps.size();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {
        TextView mStepsTV;
        TextView mIdTV;
        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            mStepsTV = itemView.findViewById(R.id.recipeStepsTV);
            mIdTV = itemView.findViewById(R.id.recipeIdTV);
        }
    }
}

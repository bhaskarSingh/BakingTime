package com.example.bhaskarkumar.bakingtime;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhaskarkumar.bakingtime.object.Bake;

import java.util.ArrayList;

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.RecipeNameViewHolder> {
    private ArrayList<Bake> mArrayList;
    private SetRecipeNameClickListener mClickListener;

    public interface SetRecipeNameClickListener{
        void onRecipeClick(int position);
    }

    public RecipeNameAdapter(ArrayList<Bake> arrayList, SetRecipeNameClickListener clickListener){
        mArrayList = arrayList;
        mClickListener = clickListener;
    }


    @Override
    public RecipeNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_name_list_item, parent, false);
        return new RecipeNameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeNameViewHolder holder, int position) {
        holder.mRecipeNameTV.setText(mArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (mArrayList == null || mArrayList.size() == 0){
            return 0;
        }
        return mArrayList.size();
    }

    public class RecipeNameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mRecipeNameTV;
        public RecipeNameViewHolder(View itemView) {
            super(itemView);
            mRecipeNameTV = itemView.findViewById(R.id.recipeNameTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onRecipeClick(getAdapterPosition());
        }
    }

    public void swapArrayList(ArrayList<Bake> newArrayList){
        mArrayList = newArrayList;

        this.notifyDataSetChanged();
    }

}

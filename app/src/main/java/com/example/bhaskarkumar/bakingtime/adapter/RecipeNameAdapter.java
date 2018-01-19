package com.example.bhaskarkumar.bakingtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bhaskarkumar.bakingtime.R;
import com.example.bhaskarkumar.bakingtime.object.Bake;

import java.util.ArrayList;

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.RecipeNameViewHolder> {
    private Context mContext;
    private ArrayList<Bake> mArrayList;
    private SetRecipeNameClickListener mClickListener;

    public interface SetRecipeNameClickListener{
        void onRecipeClick(int position);
    }

    public RecipeNameAdapter(ArrayList<Bake> arrayList, SetRecipeNameClickListener clickListener , Context context){
        mArrayList = arrayList;
        mClickListener = clickListener;
        mContext = context;
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
        String imageUrl = mArrayList.get(position).getImage();
        if (imageUrl.length() != 0) {
            Glide.with(mContext)
                    .load(imageUrl)
                    .into(holder.mNameImageView);
        }
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
        ImageView mNameImageView;
        public RecipeNameViewHolder(View itemView) {
            super(itemView);
            mRecipeNameTV = itemView.findViewById(R.id.recipeNameTV);
            mNameImageView = itemView.findViewById(R.id.recipeNameImageView);
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

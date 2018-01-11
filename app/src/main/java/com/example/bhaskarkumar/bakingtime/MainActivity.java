package com.example.bhaskarkumar.bakingtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.bhaskarkumar.bakingtime.object.Bake;
import com.example.bhaskarkumar.bakingtime.object.Ingredients;
import com.example.bhaskarkumar.bakingtime.object.Steps;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<Bake>>,
        RecipeNameAdapter.SetRecipeNameClickListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String RECIPE_STEPS_KEY = "recipe-steps-key";
    public static final String RECIPE_NAME_KEY = "recipe-name-key";
    public static final String RECIPE_INGREDIENTS_KEY = "recipe-ingredients-keys";
    public String Base_url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private ArrayList<Bake> mArrayList;
    private ArrayList<Steps> mSteps;
    private ArrayList<Ingredients> mIngredients;
    private RecyclerView CommonRV;
    private RecipeNameAdapter mRecipeNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mArrayList = new ArrayList<>();
        mSteps = new ArrayList<>();
        mIngredients = new ArrayList<>();
        CommonRV = findViewById(R.id.common_recycler_view);
        mRecipeNameAdapter = new RecipeNameAdapter(mArrayList, this);
        CommonRV.setLayoutManager(new LinearLayoutManager(this));
        CommonRV.setAdapter(mRecipeNameAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Communicator communicator = retrofit.create(Communicator.class);

        Call<List<Bake>> call = communicator.bakeItems();
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Bake>> call, Response<List<Bake>> response) {
        if (response.isSuccessful()){
            for (Bake bake : response.body()){
                mArrayList.add(new Bake(bake.getId(), bake.getName(), bake.getSteps(), bake.getIngredients()));
            }
            Log.i(LOG_TAG, mArrayList.size() + "Array size");
            Log.i(LOG_TAG, mArrayList.get(0).getSteps().size() + "Array steps");
            Log.i(LOG_TAG, mArrayList.get(0).getIngredients().size() + " ingredients");
            mRecipeNameAdapter.swapArrayList(mArrayList);
        }
    }

    @Override
    public void onFailure(Call<List<Bake>> call, Throwable t) {

    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this, RecipeSteps.class);
        mSteps = (ArrayList<Steps>) mArrayList.get(position).getSteps();
        mIngredients = (ArrayList<Ingredients>) mArrayList.get(position).getIngredients();
        intent.putExtra(RECIPE_NAME_KEY, mArrayList.get(position).getName());
        intent.putExtra(RECIPE_STEPS_KEY, mSteps);
        intent.putExtra(RECIPE_INGREDIENTS_KEY, mIngredients);
        startActivity(intent);
    }
}

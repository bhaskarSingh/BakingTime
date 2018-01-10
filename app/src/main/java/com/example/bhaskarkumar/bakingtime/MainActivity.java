package com.example.bhaskarkumar.bakingtime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.bhaskarkumar.bakingtime.object.Bake;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<Bake>>{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public String Base_url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    @Override
    public void onFailure(Call<List<Bake>> call, Throwable t) {

    }
}

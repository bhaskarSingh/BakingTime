package com.example.bhaskarkumar.bakingtime;

import com.example.bhaskarkumar.bakingtime.object.Bake;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Communicator {
    @GET("baking.json")
    Call<List<Bake>> bakeItems();
}

package com.example.bhaskarkumar.bakingtime;

import com.example.bhaskarkumar.bakingtime.object.Bake;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interface for interacting with retrofit(3rd party library)
 */
public interface Communicator {
    @GET("baking.json")
    Call<List<Bake>> bakeItems();
}

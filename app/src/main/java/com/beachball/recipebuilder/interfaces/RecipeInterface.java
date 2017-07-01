package com.beachball.recipebuilder.interfaces;

import com.beachball.recipebuilder.model.RecipeInstructions;
import com.beachball.recipebuilder.model.RecipeResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Peter Emo on 25/06/2017.
 */

public interface RecipeInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("recipes/search")
    Call<RecipeResult[]> getByName(@Query("mashape-key") String msKey, @Query("query")
            String name, @Query("diet") String diet);

    @GET("recipes/findByIngredients?fillIngredients=false&limitLicense=false")
    Call<RecipeResult[]> getByIngredients(@Query("mashape-key") String msKey, @Query("ingredients")
            String ingredientStr, @Query("number") int number, @Query("ranking") int ranking);

    @GET("recipes/{id}/information")
    Call<RecipeInstructions> getById(@Path("id") String id, @Query("mashape-key") String msKey);
}

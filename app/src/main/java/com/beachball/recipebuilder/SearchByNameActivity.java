package com.beachball.recipebuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.beachball.recipebuilder.fragment.LoadingFragment;
import com.beachball.recipebuilder.fragment.RecipeFragment;
import com.beachball.recipebuilder.fragment.ResultsFragment;
import com.beachball.recipebuilder.fragment.SearchByNameFragment;
import com.beachball.recipebuilder.fragment.SearchIngredientListFragment;
import com.beachball.recipebuilder.interfaces.RecipeInterface;
import com.beachball.recipebuilder.model.RecipeInstructions;
import com.beachball.recipebuilder.model.RecipeResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchByNameActivity extends AppCompatActivity implements SearchByNameFragment.OnSearchByNameListener {

    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/";
    public static final String MS_KEY = "0lcJvnUTk2mshDVtruSuWf0hIBA2p1XyA4mjsnwhEeWAtgAwGY";
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Fragment searchByNameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ingredient);

        searchByNameFragment = new SearchIngredientListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, searchByNameFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onSearchByName(String ingredientStr, boolean isVegetarian, boolean isVegan) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoadingFragment()).commit();
        RecipeInterface apiService = retrofit.create(RecipeInterface.class);
        String dietStr = (isVegetarian ? "vegetarian":"") + (isVegan ? "vegan":"");
        Call<RecipeResult[]> call = apiService.getByName(MS_KEY, ingredientStr, dietStr);
        call.enqueue(new Callback<RecipeResult[]>() {
            @Override
            public void onResponse(Call<RecipeResult[]> call, Response<RecipeResult[]> response) {
                RecipeResult[] result = response.body();
                ResultsFragment resultsFragment = ResultsFragment.newInstance(result);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultsFragment).commit();
            }

            @Override
            public void onFailure(Call<RecipeResult[]> call, Throwable t) {
                Log.e("RecipeBuilder", t.toString());
            }
        });
    }
}

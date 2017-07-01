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
import com.beachball.recipebuilder.fragment.SearchIngredientListFragment;
import com.beachball.recipebuilder.interfaces.RecipeInterface;
import com.beachball.recipebuilder.model.RecipeInstructions;
import com.beachball.recipebuilder.model.RecipeResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchByIngredientActivity extends BaseNavActivity implements SearchIngredientListFragment.OnIngredientEnteredListener,
    ResultsFragment.OnResultSelectedListener {

    public static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/";
    public static final String MS_KEY = "0lcJvnUTk2mshDVtruSuWf0hIBA2p1XyA4mjsnwhEeWAtgAwGY";
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Fragment searchIngListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContent(getLayoutInflater().inflate(R.layout.activity_search_ingredient, null));

        searchIngListFragment = new SearchIngredientListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, searchIngListFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onIngredientEntered(String ingredientStr) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoadingFragment()).commit();
        RecipeInterface apiService = retrofit.create(RecipeInterface.class);
        Call<RecipeResult[]> call = apiService.getByIngredients(MS_KEY, ingredientStr, 10, 1);
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

    @Override
    public void onResultSelected(String id) {
        RecipeInterface apiService = retrofit.create(RecipeInterface.class);
        Call<RecipeInstructions> call = apiService.getById(id, MS_KEY);
        call.enqueue(new Callback<RecipeInstructions>() {
            @Override
            public void onResponse(Call<RecipeInstructions> call, Response<RecipeInstructions> response) {
                RecipeInstructions result = response.body();
                RecipeFragment recipeFragment = RecipeFragment.newInstance(result);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, recipeFragment).addToBackStack(null).commit();
            }

            @Override
            public void onFailure(Call<RecipeInstructions> call, Throwable t) {
                Log.e("RecipeBuilder", t.toString());
            }
        });
    }
}

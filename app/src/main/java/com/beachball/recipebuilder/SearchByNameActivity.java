package com.beachball.recipebuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.beachball.recipebuilder.fragment.LoadingFragment;
import com.beachball.recipebuilder.fragment.ResultsFragment;
import com.beachball.recipebuilder.fragment.SearchByNameFragment;
import com.beachball.recipebuilder.interfaces.RecipeInterface;
import com.beachball.recipebuilder.model.RecipeResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchByNameActivity extends BaseNavActivity implements SearchByNameFragment.OnSearchByNameListener {

    Fragment searchByNameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContent(getLayoutInflater().inflate(R.layout.activity_search_ingredient, null));

        searchByNameFragment = new SearchByNameFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, searchByNameFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onSearchByName(String ingredientStr, boolean isVegetarian, boolean isVegan) {
        hideKeyboard();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoadingFragment()).addToBackStack(LOADING_BACK_STACK).commit();
        RecipeInterface apiService = retrofit.create(RecipeInterface.class);
        String dietStr = (isVegetarian ? getString(R.string.vegetarian):"") + (isVegan ? getString(R.string.vegan):"");
        Call<RecipeResult[]> call;
        if (dietStr.equals("")) {
            call = apiService.getByName(msKey, ingredientStr);
        } else {
            call = apiService.getByName(msKey, ingredientStr, dietStr);
        }
        call.enqueue(new Callback<RecipeResult[]>() {
            @Override
            public void onResponse(Call<RecipeResult[]> call, Response<RecipeResult[]> response) {
                RecipeResult[] result = response.body();
                ResultsFragment resultsFragment = ResultsFragment.newInstance(result);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultsFragment).commit();
            }

            @Override
            public void onFailure(Call<RecipeResult[]> call, Throwable t) {
                onConnectionFailed(t.toString());
            }
        });
    }
}

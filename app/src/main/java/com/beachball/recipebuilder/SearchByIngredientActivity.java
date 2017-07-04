package com.beachball.recipebuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

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

public class SearchByIngredientActivity extends BaseNavActivity implements SearchIngredientListFragment.OnIngredientEnteredListener,
    ResultsFragment.OnResultSelectedListener {

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
        hideKeyboard();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoadingFragment()).addToBackStack(LOADING_BACK_STACK).commit();
        isLoading = true;
        RecipeInterface apiService = retrofit.create(RecipeInterface.class);
        Call<RecipeResult[]> call = apiService.getByIngredients(msKey, ingredientStr, 10, 1);
        call.enqueue(new Callback<RecipeResult[]>() {
            @Override
            public void onResponse(Call<RecipeResult[]> call, Response<RecipeResult[]> response) {
                isLoading = false;
                if(!isCancelled) {
                    RecipeResult[] result = response.body();
                    ResultsFragment resultsFragment = ResultsFragment.newInstance(result);
                    getSupportFragmentManager().popBackStack(LOADING_BACK_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultsFragment).addToBackStack(MAIN_BACK_STACK).commit();
                }
                isCancelled = false;
            }

            @Override
            public void onFailure(Call<RecipeResult[]> call, Throwable t) {
                onConnectionFailed(t.toString());
            }
        });
    }
}

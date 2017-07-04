package com.beachball.recipebuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.beachball.recipebuilder.fragment.LoadingFragment;
import com.beachball.recipebuilder.fragment.ResultsFragment;
import com.beachball.recipebuilder.fragment.SearchByNameFragment;
import com.beachball.recipebuilder.interfaces.RecipeInterface;
import com.beachball.recipebuilder.model.RecipeNameReturn;
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
        isLoading = true;
        RecipeInterface apiService = retrofit.create(RecipeInterface.class);
        String dietStr = (isVegetarian ? getString(R.string.vegetarian):"") + (isVegan ? getString(R.string.vegan):"");
        Call<RecipeNameReturn> call;
        if (dietStr.equals("")) {
            call = apiService.getByName(msKey, ingredientStr);
        } else {
            call = apiService.getByName(msKey, ingredientStr, dietStr);
        }
        call.enqueue(new Callback<RecipeNameReturn>() {
            @Override
            public void onResponse(Call<RecipeNameReturn> call, Response<RecipeNameReturn> response) {
                isLoading = false;
                if(!isCancelled) {
                    RecipeNameReturn result = response.body();
                    ResultsFragment resultsFragment = ResultsFragment.newInstance(result);
                    getSupportFragmentManager().popBackStack(LOADING_BACK_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultsFragment).addToBackStack(MAIN_BACK_STACK).commit();
                }
                isCancelled = false;
            }

            @Override
            public void onFailure(Call<RecipeNameReturn> call, Throwable t) {
                onConnectionFailed(t.toString());
            }
        });
    }
}

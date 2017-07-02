package com.beachball.recipebuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.beachball.recipebuilder.fragment.FavouriteSearchFragment;
import com.beachball.recipebuilder.fragment.LoadingFragment;
import com.beachball.recipebuilder.fragment.ResultsFragment;
import com.beachball.recipebuilder.fragment.SearchByNameFragment;
import com.beachball.recipebuilder.interfaces.RecipeInterface;
import com.beachball.recipebuilder.model.RecipeNameReturn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteSearchActivity extends BaseNavActivity implements FavouriteSearchFragment.FavouriteSearchListener {

    Fragment favouriteSearchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContent(getLayoutInflater().inflate(R.layout.activity_search_ingredient, null));

        favouriteSearchFragment = new FavouriteSearchFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, favouriteSearchFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onDeleteEntry() {

    }

    @Override
    public void onClickEntry() {

    }
}

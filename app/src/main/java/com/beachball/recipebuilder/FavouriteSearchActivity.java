package com.beachball.recipebuilder;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.beachball.recipebuilder.fragment.FavouriteSearchFragment;
import com.beachball.recipebuilder.fragment.ResultsFragment;
import com.beachball.recipebuilder.model.RecipeResult;
import com.beachball.recipebuilder.model.RecipeResultRealmModel;

import io.realm.Realm;

public class FavouriteSearchActivity extends BaseNavActivity implements FavouriteSearchFragment.FavouriteSearchListener, ResultsFragment.OnResultSelectedListener {

    Fragment favouriteSearchFragment;
    Fragment resultsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContent(getLayoutInflater().inflate(R.layout.activity_search_ingredient, null));

        favouriteSearchFragment = new FavouriteSearchFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, favouriteSearchFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onDeleteEntry(final RecipeResultRealmModel model) {
        Realm.init(FavouriteSearchActivity.this);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm();
            }
        });
    }

    @Override
    public void onClickEntry(RecipeResultRealmModel model) {
        RecipeResult[] resultArray = model.getResults().toArray(new RecipeResult[0]);
        resultsFragment = ResultsFragment.newInstance(resultArray);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultsFragment).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(favouriteSearchFragment != null) {
            ((FavouriteSearchFragment)favouriteSearchFragment).notifyAdapter();
        }
    }
}

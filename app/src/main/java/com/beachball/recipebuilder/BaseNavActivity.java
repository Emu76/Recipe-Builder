package com.beachball.recipebuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.beachball.recipebuilder.fragment.LoadingFragment;
import com.beachball.recipebuilder.fragment.RecipeFragment;
import com.beachball.recipebuilder.fragment.ResultsFragment;
import com.beachball.recipebuilder.interfaces.RecipeInterface;
import com.beachball.recipebuilder.model.RecipeInstructions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseNavActivity extends AppCompatActivity implements ResultsFragment.OnResultSelectedListener {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    Intent pendingIntent;
    public native String getNativeKey();

    protected static final String LOADING_BACK_STACK = "loading";
    protected static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/";
    protected String msKey;

    static {
        System.loadLibrary("keys");
    }

    protected Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nav);

        msKey = getNativeKey();

        String[] navTitles = {getString(R.string.search_by_ingredient),getString(R.string.search_by_name),getString(R.string.favourite_searches),getString(R.string.saved_recipes)};
        int[] drawableTitles = {R.drawable.ic_action_search, R.drawable.ic_action_search, R.drawable.ic_action_favorite, R.drawable.ic_action_save};

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_view_drawer);
        drawerList.setAdapter(new DrawerAdapter(navTitles, drawableTitles, LayoutInflater.from(getApplicationContext()), getApplicationContext()));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    pendingIntent = new Intent(BaseNavActivity.this, SearchByIngredientActivity.class);
                } else if(position == 1) {
                    pendingIntent = new Intent(BaseNavActivity.this, SearchByNameActivity.class);
                } else if(position == 2) {
                    pendingIntent = new Intent(BaseNavActivity.this, FavouriteSearchActivity.class);
                } else if(position == 3) {

                }
                drawerLayout.closeDrawers();
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                if (BaseNavActivity.this.pendingIntent != null) {
                    BaseNavActivity.this.startActivity(BaseNavActivity.this.pendingIntent);
                }
            }

            public void onDrawerOpened(View drawerView) {
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    protected void addContent(View layout) {
        ((FrameLayout) findViewById(R.id.content_layout)).addView(layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onConnectionFailed(String error) {
        getSupportFragmentManager().popBackStack(LOADING_BACK_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Toast.makeText(BaseNavActivity.this, R.string.failed_connect_string, Toast.LENGTH_LONG).show();
        Log.e("RecipeBuilder", error);
    }

    protected void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onResultSelected(String id) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoadingFragment()).addToBackStack(LOADING_BACK_STACK).commit();
        RecipeInterface apiService = retrofit.create(RecipeInterface.class);
        Call<RecipeInstructions> call = apiService.getById(id, msKey);
        call.enqueue(new Callback<RecipeInstructions>() {
            @Override
            public void onResponse(Call<RecipeInstructions> call, Response<RecipeInstructions> response) {
                RecipeInstructions result = response.body();
                RecipeFragment recipeFragment = RecipeFragment.newInstance(result);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, recipeFragment).addToBackStack(null).commit();
            }

            @Override
            public void onFailure(Call<RecipeInstructions> call, Throwable t) {
                onConnectionFailed(t.toString());
            }
        });
    }
}

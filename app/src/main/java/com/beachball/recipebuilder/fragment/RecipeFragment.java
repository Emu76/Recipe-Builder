package com.beachball.recipebuilder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beachball.recipebuilder.InstructionIngredientAdapter;
import com.beachball.recipebuilder.R;
import com.beachball.recipebuilder.model.ExtendedIngredient;
import com.beachball.recipebuilder.model.RecipeInstructions;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Peter Emo on 27/06/2017.
 */

public class RecipeFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe";

    private RecyclerView mRecyclerView;
    private InstructionIngredientAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    public RecipeFragment() {
    }

    public static RecipeFragment newInstance(RecipeInstructions recipe) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_recipe, container, false);

        RecipeInstructions results = (RecipeInstructions)getArguments().get(ARG_RECIPE);

        TextView title = (TextView)fragmentView.findViewById(R.id.recipe_title);
        title.setText(results.getTitle());
        ImageView imageView = (ImageView)fragmentView.findViewById(R.id.recipe_instructions_image);
        Picasso.with(getContext()).load(results.getImage()).into(imageView);
        TextView vegetarian = (TextView)fragmentView.findViewById(R.id.vegetarian_suitable);
        vegetarian.setText(getString(R.string.suitable_vegetarians) + " " + (results.getVegetarian() ? getString(R.string.yes) : getString(R.string.no)));
        TextView vegan = (TextView)fragmentView.findViewById(R.id.vegan_suitable);
        vegan.setText(getString(R.string.suitable_vegans) + " " + (results.getVegan() ? getString(R.string.yes) : getString(R.string.no)));
        TextView servings = (TextView)fragmentView.findViewById(R.id.servings_count);
        servings.setText(getString(R.string.servings) + " " + Integer.toString(results.getServings()));
        TextView readyIn = (TextView)fragmentView.findViewById(R.id.ready_in);
        readyIn.setText(getString(R.string.ready_in) + " " + Integer.toString(results.getReadyInMinutes()) + " " + getString(R.string.minutes));
        TextView instructions = (TextView)fragmentView.findViewById(R.id.recipe_instructions_text);
        instructions.setText(results.getInstructions());

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recipe_ingredients_list);
        setupRecyclerView(results.getExtendedIngredients());

        //saveToRealm((RecipeInstructions)getArguments().get(ARG_RECIPE));

        return fragmentView;
    }

    /*private void saveToRealm(final RecipeInstructions instructions) {
        int id = instructions.getId();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(instructions);
            }
        });
        RecipeInstructions instructions = realm.where(RecipeInstructions.class).equalTo("id", id).findFirst();
        instructions.getInstructions();
    }*/

    private void setupRecyclerView(List<ExtendedIngredient> dataSet) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new InstructionIngredientAdapter(dataSet);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }
}

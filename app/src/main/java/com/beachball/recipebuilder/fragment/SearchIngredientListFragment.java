package com.beachball.recipebuilder.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.beachball.recipebuilder.IngredientAdapter;
import com.beachball.recipebuilder.R;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private IngredientAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private OnIngredientEnteredListener mListener;

    public SearchIngredientListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_search_ingredients_list, container, false);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.ingredients_list);
        final List<String> dataset = new ArrayList<>();
        dataset.add("");
        setupRecyclerView(dataset);
        Button goButton = (Button) fragmentView.findViewById(R.id.go_button);
        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.add_ingredient_button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String formattedStr = "";
                for(int i=0; i<mAdapter.getDataSet().size(); i++) {
                    if(mAdapter.getDataSet().get(i).length() > 0) {
                        formattedStr = formattedStr + "," + mAdapter.getDataSet().get(i);
                    }
                }
                onGoPressed(formattedStr);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataset.add(0,"");
                mAdapter.notifyItemInserted(0);
                mRecyclerView.scrollToPosition(0);
            }
        });
        return fragmentView;
    }

    public void onGoPressed(String ingredientStr) {
        if (mListener != null) {
            mListener.onIngredientEntered(ingredientStr);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnIngredientEnteredListener) {
            mListener = (OnIngredientEnteredListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnIngredientEnteredListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setupRecyclerView(List<String> dataset) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setPreserveFocusAfterLayout(false);
        mAdapter = new IngredientAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    public interface OnIngredientEnteredListener {
        void onIngredientEntered(String ingredientStr);
    }
}

package com.beachball.recipebuilder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beachball.recipebuilder.FavouriteSearchAdapter;
import com.beachball.recipebuilder.R;
import com.beachball.recipebuilder.model.RecipeResultRealmModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavouriteSearchFragment extends Fragment {

    private FavouriteSearchListener mListener;
    private RecyclerView mRecyclerView;
    private FavouriteSearchAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private TextView noFavouritesView;

    public FavouriteSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_favourite_searches, container, false);

        Realm.init(getContext());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RecipeResultRealmModel> list = realm.where(RecipeResultRealmModel.class).findAll();

        noFavouritesView = (TextView) fragmentView.findViewById(R.id.no_favourites_text);
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.favourite_searches_list);
        setupRecyclerView(list);

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FavouriteSearchListener) {
            mListener = (FavouriteSearchListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FavouriteSearchListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface FavouriteSearchListener {
        void onDeleteEntry(RecipeResultRealmModel model);
        void onClickEntry(RecipeResultRealmModel model);
    }

    public void notifyAdapter() {
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private void setupRecyclerView(RealmResults<RecipeResultRealmModel> list) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavouriteSearchAdapter(list, new FavouriteSearchAdapter.FavouriteSearchAdapterListener() {
            @Override
            public void onDeleteEntry(RecipeResultRealmModel model) {
                mListener.onDeleteEntry(model);
                if(mAdapter.getItemCount() == 0) {
                    noFavouritesView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onClickEntry(RecipeResultRealmModel model) {
                mListener.onClickEntry(model);
            }
        });
        if(mAdapter.getItemCount() == 0) {
            noFavouritesView.setVisibility(View.VISIBLE);
        }
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }
}

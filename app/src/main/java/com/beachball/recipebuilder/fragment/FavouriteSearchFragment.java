package com.beachball.recipebuilder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public FavouriteSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_favourite_searches, container, false);

        Realm.init(getContext());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RecipeResultRealmModel> list = realm.where(RecipeResultRealmModel.class).findAll();

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
        void onDeleteEntry();
        void onClickEntry();
    }

    private void setupRecyclerView(RealmResults<RecipeResultRealmModel> list) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavouriteSearchAdapter(list, new FavouriteSearchAdapter.FavouriteSearchAdapterListener() {
            @Override
            public void onDeleteEntry() {
                mListener.onDeleteEntry();
            }

            @Override
            public void onClickEntry() {
                mListener.onClickEntry();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }
}

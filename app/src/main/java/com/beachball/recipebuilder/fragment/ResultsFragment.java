package com.beachball.recipebuilder.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beachball.recipebuilder.R;
import com.beachball.recipebuilder.ResultsAdapter;
import com.beachball.recipebuilder.model.RecipeResult;

import java.util.List;

/**
 * Created by Peter Emo on 27/06/2017.
 */

public class ResultsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ResultsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private OnResultSelectedListener mListener;

    private static final String ARG_RESULT_LIST = "result_list";

    public ResultsFragment() {
    }

    public static ResultsFragment newInstance(RecipeResult[] results) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(ARG_RESULT_LIST, results);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_results, container, false);

        RecipeResult[] results = (RecipeResult[])getArguments().get(ARG_RESULT_LIST);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recipes_list);
        setupRecyclerView(results);

        return fragmentView;
    }

    private void setupRecyclerView(RecipeResult[] dataSet) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ResultsAdapter(dataSet, getContext(), new ResultsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                onResultSelected(Integer.toString(id));
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    public void onResultSelected(String id) {
        if (mListener != null) {
            mListener.onResultSelected(id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnResultSelectedListener) {
            mListener = (OnResultSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnResultSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnResultSelectedListener {
        void onResultSelected(String id);
    }
}

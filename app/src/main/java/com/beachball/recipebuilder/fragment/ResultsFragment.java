package com.beachball.recipebuilder.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.beachball.recipebuilder.R;
import com.beachball.recipebuilder.ResultsAdapter;
import com.beachball.recipebuilder.model.RecipeNameReturn;
import com.beachball.recipebuilder.model.RecipeResult;
import com.beachball.recipebuilder.model.RecipeResultRealmModel;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Peter Emo on 27/06/2017.
 */

public class ResultsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ResultsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecipeResult[] results;
    private boolean searchSaved;

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

    public static ResultsFragment newInstance(RecipeNameReturn results) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        RecipeResult[] recipeResults = new RecipeResult[results.getResults().size()];
        for(int i=0; i<results.getResults().size(); i++) {
            recipeResults[i] = new RecipeResult();
            recipeResults[i].setId(results.getResults().get(i).getId());
            recipeResults[i].setImage(results.getBaseUri() + results.getResults().get(i).getImage());
            recipeResults[i].setTitle(results.getResults().get(i).getTitle());
        }
        args.putParcelableArray(ARG_RESULT_LIST, recipeResults);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_results, container, false);

        searchSaved = false;

        results = (RecipeResult[])getArguments().get(ARG_RESULT_LIST);

        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recipes_list);
        setupRecyclerView(results);

        return fragmentView;
    }

    private void setupRecyclerView(RecipeResult[] dataSet) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ResultsAdapter(dataSet, getContext(), new ResultsAdapter.ResultsAdapterListener() {
            @Override
            public void onItemClick(int id) {
                onResultSelected(Integer.toString(id));
            }

            @Override
            public void returnFavouriteButton(ImageButton button) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!searchSaved) {
                            showDialog();
                        } else {
                            Toast.makeText(getContext(), R.string.search_already_saved, Toast.LENGTH_LONG).show();
                        }
                    }
                });
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

    public void saveToRealm(final RecipeResult[] results, String searchName) {
        final RecipeResultRealmModel resultModel = new RecipeResultRealmModel();
        resultModel.setId(UUID.randomUUID().toString());
        resultModel.setName(searchName);
        resultModel.setResults(results);
        Realm.init(getContext());
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(resultModel);
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_save);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchName = (EditText)dialog.findViewById(R.id.dialog_edit_text);
                String searchNameStr = searchName.getText().toString();
                dialog.dismiss();
                saveToRealm(results, searchNameStr);
                Toast.makeText(getContext(), R.string.search_favourited, Toast.LENGTH_LONG).show();
                searchSaved = true;
            }
        });
        dialog.show();
    }
}

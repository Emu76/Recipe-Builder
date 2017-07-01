package com.beachball.recipebuilder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.beachball.recipebuilder.R;

public class SearchByNameFragment extends Fragment {

    private OnSearchByNameListener mListener;

    public SearchByNameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_search_by_name, container, false);

        final EditText nameBox = (EditText)fragmentView.findViewById(R.id.recipe_search_edt);
        final CheckBox vegetarianCheck = (CheckBox)fragmentView.findViewById(R.id.vegetarian_check);
        final CheckBox veganCheck = (CheckBox)fragmentView.findViewById(R.id.vegan_check);
        Button goButton = (Button)fragmentView.findViewById(R.id.go_name_button);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNameGoPressed(nameBox.getText().toString(), vegetarianCheck.isChecked(), veganCheck.isChecked());
            }
        });
        return fragmentView;
    }

    public void onNameGoPressed(String ingredientStr, boolean isVegetarian, boolean isVegan) {
        if (mListener != null) {
            mListener.onSearchByName(ingredientStr, isVegetarian, isVegan);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchByNameListener) {
            mListener = (OnSearchByNameListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchByNameListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSearchByNameListener {
        void onSearchByName(String ingredientStr, boolean isVegetarian, boolean isVegan);
    }
}

package com.beachball.recipebuilder.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beachball.recipebuilder.R;

import java.util.Random;

public class LoadingFragment extends Fragment {

    public LoadingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_loading, container, false);
        TextView loadingText = (TextView)fragmentView.findViewById(R.id.loading_text);
        String[] strArray = getResources().getStringArray(R.array.loading_str_array);
        Random rn = new Random();
        int randomInt = rn.nextInt(strArray.length);
        loadingText.setText(strArray[randomInt]);
        return fragmentView;
    }
}

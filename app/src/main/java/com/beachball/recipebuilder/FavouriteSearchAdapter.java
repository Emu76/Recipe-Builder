package com.beachball.recipebuilder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beachball.recipebuilder.model.RecipeResultRealmModel;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Peter Emo on 02/07/2017.
 */

public class FavouriteSearchAdapter extends RecyclerView.Adapter<FavouriteSearchAdapter.ViewHolder> {

    private RealmResults<RecipeResultRealmModel> mDataset;
    public FavouriteSearchAdapterListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView searchText;
        public ImageButton deleteButton;
        public ViewHolder(RelativeLayout v) {
            super(v);
            searchText = (TextView)v.findViewById(R.id.favourite_searches_text);
            deleteButton = (ImageButton)v.findViewById(R.id.favourite_searches_delete);
        }
    }

    public FavouriteSearchAdapter(RealmResults<RecipeResultRealmModel> dataSet, FavouriteSearchAdapterListener listener) {
        mDataset = dataSet;
        mListener = listener;
    }

    @Override
    public FavouriteSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_searches_row, parent, false);
        FavouriteSearchAdapter.ViewHolder vh = new FavouriteSearchAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(FavouriteSearchAdapter.ViewHolder holder, final int position) {
        holder.searchText.setText(mDataset.get(position).getName());
        holder.searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickEntry();
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteEntry();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface FavouriteSearchAdapterListener {
        void onDeleteEntry();
        void onClickEntry();
    }
}

package com.beachball.recipebuilder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beachball.recipebuilder.model.ExtendedIngredient;
import com.beachball.recipebuilder.model.RecipeResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Peter Emo on 26/06/2017.
 */

public class InstructionIngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ExtendedIngredient> mDataset;

    public static class ViewItem extends RecyclerView.ViewHolder {
        public TextView ingredientName;
        public ViewItem(LinearLayout v) {
            super(v);
            ingredientName = (TextView)v.findViewById(R.id.ingredient_name);
        }
    }

    public InstructionIngredientAdapter(List<ExtendedIngredient> dataSet) {
        mDataset = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_ingredient, parent, false);
        return new ViewItem(v);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewItem item = (ViewItem)holder;
        String nameCapitalised = mDataset.get(position).getName().substring(0,1).toUpperCase() + mDataset.get(position).getName().substring(1);
        item.ingredientName.setText(mDataset.get(position).getAmount() +
                " " + mDataset.get(position).getUnit() + (mDataset.get(position).getUnit().equals("") ? "" : " ")
                + nameCapitalised);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
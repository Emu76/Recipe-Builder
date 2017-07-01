package com.beachball.recipebuilder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beachball.recipebuilder.model.RecipeResult;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Peter Emo on 26/06/2017.
 */

public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecipeResult[] mDataset;
    private Context mContext;
    private static int TYPE_HEADER = 0;
    private static int TYPE_ITEM = 1;

    private OnItemClickListener mListener;

    public static class ViewHeader extends RecyclerView.ViewHolder {
        public ViewHeader(LinearLayout v) {
            super(v);
        }
    }

    public static class ViewItem extends RecyclerView.ViewHolder {
        public TextView recipeTitle;
        public TextView recipeLikes;
        public TextView recipeIngredientCount;
        public ImageView recipeImage;
        public View view;
        public ViewItem(LinearLayout v) {
            super(v);
            view = v;
            recipeTitle = (TextView)v.findViewById(R.id.recipe_title);
            recipeLikes = (TextView)v.findViewById(R.id.recipe_likes);
            recipeImage = (ImageView)v.findViewById(R.id.recipe_image);
            recipeIngredientCount = (TextView)v.findViewById(R.id.recipe_ingredients);
        }
    }

    public ResultsAdapter(RecipeResult[] dataset, Context context, OnItemClickListener listener) {
        mDataset = dataset;
        mContext = context;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_results_header, parent, false);
            return new ViewHeader(v);
        } else if(viewType == 1) {
            LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_entry, parent, false);
            return new ViewItem(v);
        }
        throw new RuntimeException("There is no compatible type");
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewItem) {
            final int newPosition = position - 1;
            ViewItem item = (ViewItem)holder;
            item.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mDataset[newPosition].getId());
                }
            });
            item.recipeTitle.setText(mDataset[newPosition].getTitle());
            Picasso.with(mContext).load(mDataset[newPosition].getImage()).into(item.recipeImage);
            String likeStr = "";
            if (mDataset[newPosition].getLikes() == 1) {
                likeStr = " like";
            } else {
                likeStr = " likes";
            }
            item.recipeLikes.setText(Integer.toString(mDataset[newPosition].getLikes()) + likeStr);
            item.recipeIngredientCount.setText("Ingredients available: " + mDataset[newPosition].getUsedIngredientCount());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    @Override
    public int getItemCount() {
        return mDataset.length + 1;
    }
}
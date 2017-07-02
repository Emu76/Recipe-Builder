package com.beachball.recipebuilder.model;

/**
 * Created by Peter Emo on 25/06/2017.
 */


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class RecipeResult extends RealmObject implements Parcelable {
    private int mData;

    public RecipeResult() {

    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("usedIngredientCount")
    @Expose
    private Integer usedIngredientCount;
    @SerializedName("missedIngredientCount")
    @Expose
    private Integer missedIngredientCount;
    @SerializedName("likes")
    @Expose
    private Integer likes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public void setUsedIngredientCount(Integer usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    public Integer getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public void setMissedIngredientCount(Integer missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<RecipeResult> CREATOR
            = new Parcelable.Creator<RecipeResult>() {
        public RecipeResult createFromParcel(Parcel in) {
            return new RecipeResult(in);
        }

        public RecipeResult[] newArray(int size) {
            return new RecipeResult[size];
        }
    };

    private RecipeResult(Parcel in) {
        mData = in.readInt();
    }
}
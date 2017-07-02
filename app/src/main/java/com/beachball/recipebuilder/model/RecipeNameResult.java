package com.beachball.recipebuilder.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Peter Emo on 02/07/2017.
 */

public class RecipeNameResult implements Parcelable {
    private int mData;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("readyInMinutes")
    @Expose
    private Integer readyInMinutes;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageUrls")
    @Expose
    private List<String> imageUrls = null;

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

    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<RecipeNameResult> CREATOR
            = new Creator<RecipeNameResult>() {
        public RecipeNameResult createFromParcel(Parcel in) {
            return new RecipeNameResult(in);
        }

        public RecipeNameResult[] newArray(int size) {
            return new RecipeNameResult[size];
        }
    };

    private RecipeNameResult(Parcel in) {
        mData = in.readInt();
    }
}

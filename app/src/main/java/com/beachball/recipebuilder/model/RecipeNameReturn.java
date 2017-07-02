package com.beachball.recipebuilder.model;

/**
 * Created by Peter Emo on 25/06/2017.
 */


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeNameReturn implements Parcelable{
    private int mData;

    @SerializedName("results")
    @Expose
    private List<RecipeNameResult> results = null;
    @SerializedName("baseUri")
    @Expose
    private String baseUri;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("totalResults")
    @Expose
    private Integer totalResults;
    @SerializedName("processingTimeMs")
    @Expose
    private Integer processingTimeMs;
    @SerializedName("expires")
    @Expose
    private Long expires;
    @SerializedName("isStale")
    @Expose
    private Boolean isStale;

    public List<RecipeNameResult> getResults() {
        return results;
    }

    public void setResults(List<RecipeNameResult> results) {
        this.results = results;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getProcessingTimeMs() {
        return processingTimeMs;
    }

    public void setProcessingTimeMs(Integer processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Boolean getIsStale() {
        return isStale;
    }

    public void setIsStale(Boolean isStale) {
        this.isStale = isStale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<RecipeNameReturn> CREATOR
            = new Creator<RecipeNameReturn>() {
        public RecipeNameReturn createFromParcel(Parcel in) {
            return new RecipeNameReturn(in);
        }

        public RecipeNameReturn[] newArray(int size) {
            return new RecipeNameReturn[size];
        }
    };

    private RecipeNameReturn(Parcel in) {
        mData = in.readInt();
    }
}
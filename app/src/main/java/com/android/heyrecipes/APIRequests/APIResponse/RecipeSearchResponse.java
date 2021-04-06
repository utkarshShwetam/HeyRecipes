package com.android.heyrecipes.APIRequests.APIResponse;

import androidx.annotation.Nullable;

import com.android.heyrecipes.DataModals.RecipeModal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("count")
    @Expose()
    private int count;

    @SerializedName("recipes")
    @Expose()
    private List<RecipeModal> recipes;

    @SerializedName("error")
    @Expose()
    private String error;

    public String getError() {
        return error;
    }

    public int getCount() {
        return count;
    }

    @Nullable
    public List<RecipeModal> getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + recipes +
                ", error='" + error + '\'' +
                '}';
    }
}

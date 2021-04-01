package com.android.heyrecipes.APIRequests.APIResponse;

import com.android.heyrecipes.DataModals.RecipeModal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("recipes")
    @Expose
    private List<RecipeModal> recipes;

    public int getCount() {
        return count;
    }

    public List<RecipeModal> getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + recipes +
                '}';
    }
}

package com.android.heyrecipes.APIRequests.APIResponse;


import androidx.annotation.Nullable;

import com.android.heyrecipes.DataModals.RecipeModal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {


    @SerializedName("recipe")
    @Expose()
    private RecipeModal recipe;

    @SerializedName("error")
    @Expose()
    private String error;

    public String getError() {
        return error;
    }

    @Nullable
    public RecipeModal getRecipe(){
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                ", error='" + error + '\'' +
                '}';
    }
}

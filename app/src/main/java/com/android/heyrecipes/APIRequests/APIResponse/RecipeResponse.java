package com.android.heyrecipes.APIRequests.APIResponse;


import com.android.heyrecipes.DataModals.RecipeModal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {

    @SerializedName("recipe")
    @Expose()
    private RecipeModal recipe;

    public RecipeModal getRecipe(){
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipeModal=" + recipe +
                '}';
    }
}

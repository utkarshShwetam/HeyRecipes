package com.android.heyrecipes.Constants.Utils;

import android.util.Log;

import com.android.heyrecipes.DataModals.RecipeModal;

import java.util.List;

public class Testing {
    public static void printRecipes(List<RecipeModal> list, String tag){
        for(RecipeModal recipe: list){
            Log.d("VALUES", "onChanged: " + recipe.getTitle());
        }
    }
}

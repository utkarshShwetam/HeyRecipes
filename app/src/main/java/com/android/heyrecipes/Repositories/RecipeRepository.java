package com.android.heyrecipes.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.heyrecipes.APIRequests.RecipeAPIClient;
import com.android.heyrecipes.DataModals.RecipeModal;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private static RecipeAPIClient recipeAPIClient;

    private RecipeRepository() {
        recipeAPIClient= RecipeAPIClient.getInstance();
    }

    public static RecipeRepository getInstance(){
        if(instance==null)
            instance=new RecipeRepository();
        return instance;
    }

    public LiveData<List<RecipeModal>> getRecipes(){
        return recipeAPIClient.getRecipes();
    }
}

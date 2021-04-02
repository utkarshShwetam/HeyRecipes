package com.android.heyrecipes.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.heyrecipes.APIRequests.RecipeAPIClient;
import com.android.heyrecipes.DataModals.RecipeModal;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private static RecipeAPIClient recipeAPIClient;
    private String nextQuery;
    private int nextPageNumber;

    private RecipeRepository() {
        recipeAPIClient = RecipeAPIClient.getInstance();
    }

    public static RecipeRepository getInstance() {
        if (instance == null)
            instance = new RecipeRepository();
        return instance;
    }

    public LiveData<List<RecipeModal>> getRecipes() {
        return recipeAPIClient.getRecipes();
    }

    public LiveData<RecipeModal> getRecipe() {
        return recipeAPIClient.getRecipe();
    }

    public void setRecipeByID(String recipe_id){
      recipeAPIClient.searchRecipeByID(recipe_id);
    }

    public void searchRecipeAPI(String query, int pagNumber) {
        if (pagNumber == 0)
            pagNumber = 1;
        nextQuery=query;
        nextPageNumber=pagNumber;
        recipeAPIClient.searchRecipesApi(query, pagNumber);
    }

    public void searchNextPage(){
            searchRecipeAPI(nextQuery,nextPageNumber+1);
    }

    public void cancelRequest(){
        recipeAPIClient.cancelRequest();
    }
}

package com.android.heyrecipes.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.heyrecipes.APIRequests.RecipeAPIClient;
import com.android.heyrecipes.DataModals.RecipeModal;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private static RecipeAPIClient recipeAPIClient;
    private String nextQuery;
    private int nextPageNumber;
    private MutableLiveData<Boolean> isQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<RecipeModal>> mediatorLiveData = new MediatorLiveData<>();

    private RecipeRepository() {
        recipeAPIClient = RecipeAPIClient.getInstance();
        initMediator();
    }

    public static RecipeRepository getInstance() {
        if (instance == null)
            instance = new RecipeRepository();
        return instance;
    }

    public LiveData<List<RecipeModal>> getRecipes() {
        return mediatorLiveData;
    }

    public LiveData<RecipeModal> getRecipe() {
        return recipeAPIClient.getRecipe();
    }

    private void initMediator() {
        LiveData<List<RecipeModal>> recipeAPISource = recipeAPIClient.getRecipes();
        mediatorLiveData.addSource(recipeAPISource, new Observer<List<RecipeModal>>() {
            @Override
            public void onChanged(List<RecipeModal> recipeModals) {
                if (recipeModals != null) {
                    mediatorLiveData.setValue(recipeModals);
                    doneQuery(recipeModals);
                } else {
                    //search database cache
                    doneQuery(null);
                }
            }
        });
    }

    public void doneQuery(List<RecipeModal> list) {
        if (list != null) {
            if (list.size() % 30 != 0) {
                isQueryExhausted.setValue(true);
            }
        }
    }

    public LiveData<Boolean> isQueryExhausted() {
        return isQueryExhausted;
    }

    public LiveData<Boolean> isRecipeRequestTimedOut() {
        return recipeAPIClient.isRecipeRequestTimedOut();
    }

    public void setRecipeByID(String recipe_id) {
        recipeAPIClient.searchRecipeByID(recipe_id);
    }

    public void searchRecipeAPI(String query, int pagNumber) {
        if (pagNumber == 0)
            pagNumber = 1;
        nextQuery = query;
        nextPageNumber = pagNumber;
        isQueryExhausted.setValue(false);
        recipeAPIClient.searchRecipesApi(query, pagNumber);
    }

    public void searchNextPage() {
        searchRecipeAPI(nextQuery, nextPageNumber + 1);
    }

    public void cancelRequest() {
        recipeAPIClient.cancelRequest();
    }
}

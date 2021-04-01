package com.android.heyrecipes.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.Repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository recipeRepository;

    public RecipeListViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<RecipeModal>> getRecipes() {
        return recipeRepository.getRecipes();
    }

    public void searchRecipeAPI(String query, int pagNumber) {
        if (pagNumber == 0)
            pagNumber = 1;
        recipeRepository.searchRecipeAPI(query, pagNumber);
    }


}

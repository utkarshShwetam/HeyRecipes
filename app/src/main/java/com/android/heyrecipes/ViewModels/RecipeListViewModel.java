package com.android.heyrecipes.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.Repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private boolean isViewingRecipesCheck;
    private boolean isPerformingQueryCheck;

    public RecipeListViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<RecipeModal>> getRecipes() {
        return recipeRepository.getRecipes();
    }

    public void searchRecipeAPI(String query, int pagNumber) {
        isViewingRecipesCheck = true;
        isPerformingQueryCheck = true;
        if (pagNumber == 0)
            pagNumber = 1;
        recipeRepository.searchRecipeAPI(query, pagNumber);
    }

    public boolean isViewingRecipesCheck() {
        return isViewingRecipesCheck;
    }

    public void setViewingRecipesCheck(boolean viewingRecipesCheck) {
        isViewingRecipesCheck = viewingRecipesCheck;
    }

    public boolean OnBackPressed() {
        if (isPerformingQueryCheck) {
            recipeRepository.cancelRequest();
            //Cancel request
            isPerformingQueryCheck=false;
        }
        if (isViewingRecipesCheck) {
            isViewingRecipesCheck = false;
            return false;
        }
        return true;
    }

    public boolean isPerformingQueryCheck() {
        return isPerformingQueryCheck;
    }

    public void setPerformingQueryCheck(boolean performingQueryCheck) {
        isPerformingQueryCheck = performingQueryCheck;
    }
}

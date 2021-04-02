package com.android.heyrecipes.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.Repositories.RecipeRepository;

public class RecipeViewModal extends ViewModel {


    private RecipeRepository recipeRepository;
    private String recipeID;

    public RecipeViewModal() {
        recipeRepository=RecipeRepository.getInstance();
    }

    public LiveData<RecipeModal> getRecipe(){
        return recipeRepository.getRecipe();
    }

    public void searchRecipeByID(String recipe_id){
        this.recipeID=recipe_id;
        recipeRepository.setRecipeByID(recipe_id);
    }

    public String getRecipeID() {
        return recipeID;
    }

}

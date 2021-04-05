package com.android.heyrecipes.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.Repositories.RecipeRepository;

public class RecipeViewModal extends AndroidViewModel {
    public RecipeViewModal(@NonNull Application application) {
        super(application);
    }


    /*private RecipeRepository recipeRepository;
    private String recipeID;
    private boolean didReceiveRecipe;

    public boolean isDidReceiveRecipe() {
        return didReceiveRecipe;
    }


    public void setDidReceiveRecipe(boolean didReceiveRecipe) {
        this.didReceiveRecipe = didReceiveRecipe;
    }

    public RecipeViewModal() {
        recipeRepository=RecipeRepository.getInstance();
        didReceiveRecipe=false;
    }

    public LiveData<RecipeModal> getRecipe(){
        return recipeRepository.getRecipe();
    }
    public LiveData<Boolean> isRecipeRequestTimedOut(){
        return recipeRepository.isRecipeRequestTimedOut();
    }

    public void searchRecipeByID(String recipe_id){
        this.recipeID=recipe_id;
        recipeRepository.setRecipeByID(recipe_id);
    }

    public String getRecipeID() {
        return recipeID;
    }*/

}

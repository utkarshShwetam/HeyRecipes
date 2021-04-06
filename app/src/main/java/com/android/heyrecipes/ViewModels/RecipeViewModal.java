package com.android.heyrecipes.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.heyrecipes.Constants.Utils.Resource;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.Repositories.RecipeRepository;

public class RecipeViewModal extends AndroidViewModel {
    private final RecipeRepository recipeRepository;
    public RecipeViewModal(@NonNull Application application) {
        super(application);
        recipeRepository=RecipeRepository.getInstance(application);
    }

    public LiveData<Resource<RecipeModal>> searchRecipeAPI(String recipeId){
        return recipeRepository.searchRecipeAPI(recipeId);
    }


}

package com.android.heyrecipes.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.heyrecipes.DataModals.RecipeModal;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private MutableLiveData<List<RecipeModal>>  recipesData =new MutableLiveData<>();

    public RecipeListViewModel() {

    }

    public LiveData<List<RecipeModal>> getRecipes(){
        return recipesData;
    }


}

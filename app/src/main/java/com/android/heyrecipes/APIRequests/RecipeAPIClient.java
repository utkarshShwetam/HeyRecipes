package com.android.heyrecipes.APIRequests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.heyrecipes.DataModals.RecipeModal;

import java.util.List;


public class RecipeAPIClient {

    private static RecipeAPIClient instance;
    private MutableLiveData<List<RecipeModal>> recipesData ;
    private RecipeAPIClient(){

    }

    public static  RecipeAPIClient getInstance(){
        if(instance==null)
            instance=new RecipeAPIClient();
        return instance;
    }
    public LiveData<List<RecipeModal>> getRecipes(){
        return recipesData;
    }

}

package com.android.heyrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.heyrecipes.APIRequests.APIResponse.RecipeResponse;
import com.android.heyrecipes.APIRequests.APIResponse.RecipeSearchResponse;
import com.android.heyrecipes.APIRequests.RecipeAPI;
import com.android.heyrecipes.APIRequests.RetrofitServiceGenerator;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.ViewModels.RecipeListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesListActivity extends BaseActivity {
    //View Model
    private RecipeListViewModel recipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        recipeListViewModel= new ViewModelProvider(this).get(RecipeListViewModel.class);
        subscribeObserver();
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetro();
            }
        });

    }

    //****************************************Observer********************************************
    private void subscribeObserver(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<RecipeModal>>() {
            @Override
            public void onChanged(List<RecipeModal> recipeModals) {
                if(recipeModals!=null) {
                    for (RecipeModal recipe : recipeModals) {
                        Log.e("RESPONSE", "onChanged: " + recipe.getTitle());
                    }
                }
            }
        });
    }

    private  void searchRecipeApi(String query,int pageNumber){
        recipeListViewModel.searchRecipeAPI(query,pageNumber);
    }

    //*********************************************retrofit calls*************************************
    private void testRetro(){
        searchRecipeApi("Chicken breast",1);
    }
}
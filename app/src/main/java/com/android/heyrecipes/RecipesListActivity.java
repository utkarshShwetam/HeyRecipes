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
        testRetro();
    }

    //****************************************Observer********************************************
    private void subscribeObserver(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<RecipeModal>>() {
            @Override
            public void onChanged(List<RecipeModal> recipeModals) {

            }
        });
    }

    //*********************************************retrofit calls*************************************
    private void testRetro(){
        //***************GET query result******************
        Log.e("Called", "testRetro: Called Retro" );
        RecipeAPI recipeAPI= RetrofitServiceGenerator.getRecipeAPI();

        Call<RecipeSearchResponse> responseCall = recipeAPI
                .searchRecipe(
                        "chicken",
                        "1"
                );
        responseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                Log.e("RESPONSE", "onResponse: "+ response.toString());
                if(response.code()==200){
                    Log.e("RESPONSE BODY", "onResponse: "+ response.body().toString());
                    List<RecipeModal> recipeModalList =new ArrayList<>(response.body().getRecipes());
                    for(RecipeModal recipe: recipeModalList)
                        Log.e("VALUES", "onResponse: "+recipe.getTitle());
                }else {
                    Log.e("ERROR", "onResponse: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                Log.e("FAILED","No Response"+t.getMessage());
            }
        });

        //*************************GET recipe from ID**************************
        Log.e("Called", "testRetro: Called Retro" );
        RecipeAPI recipeAPI2= RetrofitServiceGenerator.getRecipeAPI();

        Call<RecipeResponse> responseCall2 = recipeAPI2
                .getRecipe(
                        "1033dd"
                );
        responseCall2.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.e("RESPONSE", "onResponse: "+ response.toString());
                if(response.code()==200){
                    Log.e("RESPONSE BODY", "onResponse: "+ response.body().toString());
                    RecipeModal recipeModal=response.body().getRecipe();
                        Log.e("VALUES", "onResponse: "+recipeModal.toString());
                }else {
                    Log.e("ERROR", "onResponse: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.e("FAILED","No Response"+t.getMessage());
            }
        });
    }
}
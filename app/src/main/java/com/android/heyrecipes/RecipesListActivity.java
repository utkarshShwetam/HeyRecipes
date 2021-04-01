package com.android.heyrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.heyrecipes.APIRequests.APIResponse.RecipeResponse;
import com.android.heyrecipes.APIRequests.APIResponse.RecipeSearchResponse;
import com.android.heyrecipes.APIRequests.RecipeAPI;
import com.android.heyrecipes.APIRequests.RetrofitServiceGenerator;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesListActivity extends BaseActivity {
    MaterialButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        button=findViewById(R.id.test);


    }
    public void check(View view){
        testRetro();
        /*Log.e("Button", "check: Clicked");
        if(progressIndicator.getVisibility() == View.VISIBLE)
            showProgressBar(false);
        else
            showProgressBar(true);*/
    }

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
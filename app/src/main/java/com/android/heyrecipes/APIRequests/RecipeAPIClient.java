package com.android.heyrecipes.APIRequests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.heyrecipes.APIRequests.APIResponse.RecipeResponse;
import com.android.heyrecipes.APIRequests.APIResponse.RecipeSearchResponse;
import com.android.heyrecipes.APIRequests.RestCallExecutors.AppExecutors;
import com.android.heyrecipes.Constants.ConstantsValues;
import com.android.heyrecipes.DataModals.RecipeModal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;


public class RecipeAPIClient {

    private static RecipeAPIClient instance;
    private MutableLiveData<List<RecipeModal>> recipesData;
    private RetrieveRecipesRunnable retrieveRecipesRunnable;
    private RetrieveRecipeRunnable retrieveRecipeRunnable;
    private MutableLiveData<RecipeModal> recipeModalMutableLiveData;
    private MutableLiveData<Boolean> recipeRequestTimeOut = new MutableLiveData<>();

    private RecipeAPIClient() {
        recipesData = new MutableLiveData<>();
        recipeModalMutableLiveData = new MutableLiveData<>();
    }

    public static RecipeAPIClient getInstance() {
        if (instance == null)
            instance = new RecipeAPIClient();
        return instance;
    }

    public LiveData<List<RecipeModal>> getRecipes() {
        return recipesData;
    }

    public LiveData<RecipeModal> getRecipe() {
        return recipeModalMutableLiveData;
    }

    public LiveData<Boolean> isRecipeRequestTimedOut() {
        return recipeRequestTimeOut;
    }

    public void searchRecipeByID(String recipe_id) {
        if (retrieveRecipeRunnable != null)
            retrieveRecipeRunnable = null;
        retrieveRecipeRunnable = new RetrieveRecipeRunnable(recipe_id);
        final Future handler = AppExecutors.getInstance().newtorkIO().submit(retrieveRecipeRunnable);
        recipeRequestTimeOut.setValue(false);
        AppExecutors.getInstance().newtorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know timeout
                recipeRequestTimeOut.postValue(true);
                handler.cancel(true);
            }
        }, ConstantsValues.TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public void searchRecipesApi(String query, int pageNumber) {
        if (retrieveRecipesRunnable != null)
            retrieveRecipesRunnable = null;
        retrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);
        final Future handler = AppExecutors.getInstance().newtorkIO().submit(retrieveRecipesRunnable);

        AppExecutors.getInstance().newtorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know timeout
                //    handler.cancel(true);
            }
        }, ConstantsValues.TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class RetrieveRecipesRunnable implements Runnable {
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<RecipeModal> list = new ArrayList<>(((RecipeSearchResponse) response.body()).getRecipes());
                    if (pageNumber == 1)
                        recipesData.postValue(list);
                    else {
                        List<RecipeModal> currentRecipes = recipesData.getValue();
                        currentRecipes.addAll(list);
                        recipesData.postValue(currentRecipes);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e("ERROR", "run: " + error);
                    recipesData.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ERROR", "run: " + e.getMessage());
                recipesData.postValue(null);
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
            return RetrofitServiceGenerator.getRecipeAPI().searchRecipe(
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest() {
            Log.e("CANCEL", "cancelRequest: CANCELLED");
            cancelRequest = true;
        }
    }

    private class RetrieveRecipeRunnable implements Runnable {
        private String recipe_id;
        boolean cancelRequest;

        public RetrieveRecipeRunnable(String recipe_id) {
            this.recipe_id = recipe_id;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipe(recipe_id).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    RecipeModal recipeModal = ((RecipeResponse) response.body()).getRecipe();
                    recipeModalMutableLiveData.postValue(recipeModal);
                } else {
                    String error = response.errorBody().string();
                    Log.e("ERROR", "run: " + error);
                    recipeModalMutableLiveData.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ERROR", "run: " + e.getMessage());
                recipeModalMutableLiveData.postValue(null);
            }

        }

        private Call<RecipeResponse> getRecipe(String recipe_id) {
            return RetrofitServiceGenerator.getRecipeAPI().getRecipe(
                    recipe_id
            );
        }

        private void cancelRequest() {
            Log.e("CANCEL", "cancelRequest: CANCELLED");
            cancelRequest = true;
        }
    }

    public void cancelRequest() {
        if (retrieveRecipeRunnable != null) {
            retrieveRecipeRunnable.cancelRequest();
        }
    }
}

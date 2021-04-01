package com.android.heyrecipes.APIRequests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    private RecipeAPIClient() {
        recipesData = new MutableLiveData<>();
    }

    public static RecipeAPIClient getInstance() {
        if (instance == null)
            instance = new RecipeAPIClient();
        return instance;
    }

    public LiveData<List<RecipeModal>> getRecipes() {
        return recipesData;
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

}

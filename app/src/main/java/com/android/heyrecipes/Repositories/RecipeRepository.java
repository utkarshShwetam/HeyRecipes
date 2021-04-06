package com.android.heyrecipes.Repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.heyrecipes.APIRequests.APIResponse.APIResponse;
import com.android.heyrecipes.APIRequests.APIResponse.RecipeResponse;
import com.android.heyrecipes.APIRequests.APIResponse.RecipeSearchResponse;
import com.android.heyrecipes.APIRequests.RecipeAPIClient;
import com.android.heyrecipes.APIRequests.RestCallExecutors.AppExecutors;
import com.android.heyrecipes.APIRequests.RetrofitServiceGenerator;
import com.android.heyrecipes.Constants.ConstantsValues;
import com.android.heyrecipes.Constants.Utils.NetworkBoundResource;
import com.android.heyrecipes.Constants.Utils.Resource;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.RoomPersistence.RecipeDAO;
import com.android.heyrecipes.RoomPersistence.RecipeDatabase;

import java.util.List;

public class RecipeRepository {
    private static final String TAG = "RecipeRepository";

    private static RecipeRepository instance;
    private final RecipeDAO recipeDAO;

    public static RecipeRepository getInstance(Context context) {
        if(instance==null){
            instance=new RecipeRepository(context);
        }
        return instance;
    }

    public RecipeRepository(Context context){
        recipeDAO= RecipeDatabase.getInstance(context).getRecipeDAO();
    }

    public LiveData<Resource<List<RecipeModal>>> searchRecipesAPI(final String query, final int pageNumber){
        return new NetworkBoundResource<List<RecipeModal>, RecipeSearchResponse>(AppExecutors.getInstance()){

            @Override
            protected void saveCallResult(@NonNull RecipeSearchResponse item) {
                if(item.getRecipes()!=null){

                    RecipeModal [] recipes =new RecipeModal[item.getRecipes().size()];

                    int index=0;
                    for (long rabid:recipeDAO.insertRecipes((RecipeModal[]) (item.getRecipes().toArray(recipes)))){
                        if(rabid==-1){
                            Log.e(TAG, "saveCallResult: CONFLICT this recipe is already inserted in the cache");
                            //if the recipe already exists don't set the ingredients or timestamp bcz
                            // they will be erased
                            recipeDAO.updateRecipe(
                                    recipes[index].getRecipe_id(),
                                    recipes[index].getTitle(),
                                    recipes[index].getPublisher(),
                                    recipes[index].getImage_url(),
                                    recipes[index].getSocial_rank()
                            );
                        }
                        index++;
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RecipeModal> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<RecipeModal>> loadFromDb() {
                return recipeDAO.searchRecipes(query,pageNumber);
            }

            @NonNull
            @Override
            protected LiveData<APIResponse<RecipeSearchResponse>> createCall() {
                Log.e(TAG, "createCall: "+ RetrofitServiceGenerator.getRecipeAPI().searchRecipe(
                        query, String.valueOf(pageNumber)));
                return RetrofitServiceGenerator.getRecipeAPI().searchRecipe(
                        query, String.valueOf(pageNumber)
                );
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<RecipeModal>> searchRecipesAPI(final String recipeId){
        return new NetworkBoundResource<RecipeModal, RecipeResponse>(AppExecutors.getInstance()){

            @Override
            protected void saveCallResult(@NonNull RecipeResponse item) {
                if(item.getRecipe()!=null){
                    item.getRecipe().setTimestamp((int)(System.currentTimeMillis()/1000));
                    recipeDAO.insertRecipe(item.getRecipe());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable RecipeModal data) {
                Log.e(TAG, "shouldFetch: "+data.toString() );
                int currentTime=(int)System.currentTimeMillis()/100;
                Log.e(TAG, "shouldFetch: "+ currentTime );
                int lastRefresh= data.getTimestamp();
                Log.e(TAG, "shouldFetch: "+ lastRefresh );
                Log.e(TAG, "shouldFetch:  "+((currentTime-lastRefresh)/60/60/12)+ " days");
                if((currentTime)-data.getTimestamp()>= ConstantsValues.RECIPE_REFRESH_TIME){
                    Log.e(TAG, "shouldFetch: Refreshing recipe");
                    return true;
                }
                return false;
            }

            @NonNull
            @Override
            protected LiveData<RecipeModal> loadFromDb() {
                return recipeDAO.getRecipe(recipeId);
            }

            @NonNull
            @Override
            protected LiveData<APIResponse<RecipeResponse>> createCall() {
                return RetrofitServiceGenerator.getRecipeAPI().getRecipe(
                        recipeId
                );
            }
        }.getAsLiveData();
    }
}

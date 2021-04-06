package com.android.heyrecipes.APIRequests;

import androidx.lifecycle.LiveData;

import com.android.heyrecipes.APIRequests.APIResponse.APIResponse;
import com.android.heyrecipes.APIRequests.APIResponse.RecipeResponse;
import com.android.heyrecipes.APIRequests.APIResponse.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeAPI {

    //Search the API for a query
    @GET("api/search")
    LiveData<APIResponse<RecipeSearchResponse>> searchRecipe(
            @Query("q") String query,
            @Query("page") String page
    );

    //Get recipe request
    @GET("api/get")
    LiveData<APIResponse<RecipeResponse>> getRecipe(
            @Query("rId") String recipe_id
    );
}

package com.android.heyrecipes.APIRequests;

import com.android.heyrecipes.APIRequests.APIResponse.RecipeResponse;
import com.android.heyrecipes.APIRequests.APIResponse.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeAPI {

    //Search the API for a query
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("q") String query,
            @Query("page") String page
    );

    //Get recipe request
    @GET("api/get")
    Call<RecipeResponse> getRecipe(
            @Query("rId") String recipe_id
    );
}

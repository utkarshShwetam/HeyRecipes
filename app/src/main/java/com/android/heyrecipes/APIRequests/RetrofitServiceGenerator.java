package com.android.heyrecipes.APIRequests;

import com.android.heyrecipes.Constants.ConstantsValues;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {

    private static Retrofit.Builder retroFitBuilder =
            new Retrofit.Builder()
            .baseUrl(ConstantsValues.API_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit= retroFitBuilder.build();

    private static RecipeAPI recipeAPI=retrofit.create(RecipeAPI.class);

    public static RecipeAPI getRecipeAPI(){
        return recipeAPI;
    }
}

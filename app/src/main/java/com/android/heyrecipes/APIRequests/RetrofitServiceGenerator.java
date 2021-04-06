package com.android.heyrecipes.APIRequests;

import com.android.heyrecipes.Constants.ConstantsValues;
import com.android.heyrecipes.Constants.Utils.LiveDataCallAdapter;
import com.android.heyrecipes.Constants.Utils.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {

    private static final Retrofit.Builder retroFitBuilder =
            new Retrofit.Builder()
                    .baseUrl(ConstantsValues.API_URL)
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retroFitBuilder.build();

    private static final RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);

    public static RecipeAPI getRecipeAPI() {
        return recipeAPI;
    }
}

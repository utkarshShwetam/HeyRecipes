package com.android.heyrecipes.APIRequests;

import com.android.heyrecipes.Constants.ConstantsValues;
import com.android.heyrecipes.Constants.Utils.LiveDataCallAdapter;
import com.android.heyrecipes.Constants.Utils.LiveDataCallAdapterFactory;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceGenerator {

    private static final OkHttpClient client =new OkHttpClient().newBuilder()
            //establish connection to API
            .connectTimeout(ConstantsValues.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            //time between each byte read from server
            .readTimeout(ConstantsValues.READ_TIMEOUT,TimeUnit.SECONDS)
            //time between each byte send to server
            .writeTimeout(ConstantsValues.WRITE_TIMEOUT,TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();


    private static final Retrofit.Builder retroFitBuilder =
            new Retrofit.Builder()
                    .baseUrl(ConstantsValues.API_URL)
                    .client(client)
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retroFitBuilder.build();

    private static final RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);

    public static RecipeAPI getRecipeAPI() {
        return recipeAPI;
    }
}

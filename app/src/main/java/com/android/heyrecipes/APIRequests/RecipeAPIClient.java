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

    private RecipeAPIClient() {

    }

    public static RecipeAPIClient getInstance() {
        if (instance == null)
            instance = new RecipeAPIClient();
        return instance;
    }

}

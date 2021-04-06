package com.android.heyrecipes.Constants.Utils;

import androidx.lifecycle.LiveData;

import com.android.heyrecipes.APIRequests.APIResponse.APIResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    //This performs no of checks and then returns the response type for the the retrofit request.


    @Override
    @EverythingIsNonNull
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
       //CHECK 1
        //Make sure the call adapter is returning a type of a liveData
        if(CallAdapter.Factory.getRawType(returnType)!= LiveData.class){
            return null;
        }
        //CHECK 2
        //Type that liveData is wrapping
        Type observableType =CallAdapter.Factory.getParameterUpperBound(0,(ParameterizedType) returnType);

        //Check if it's of type APIResponse
        Type rawObservableType=CallAdapter.Factory.getRawType(observableType);
        if(rawObservableType!= APIResponse.class){
            throw new IllegalArgumentException("Type must be a defined resource");
        }

        //CHECK 3
        //Check if APIResponse is parameterized AKA : Does APIResponse<T> exists? (must wrap around T)
        //FYI : T is either RecipeResponse or T will be a RecipeSearchResponse
        if(!(observableType instanceof ParameterizedType)){
            throw new IllegalArgumentException("Type must be a defined resource");
        }

        Type bodyType= CallAdapter.Factory.getParameterUpperBound(0,(ParameterizedType) observableType);
        return new LiveDataCallAdapter<Type>(bodyType);
    }
}

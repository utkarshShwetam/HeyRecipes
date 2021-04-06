package com.android.heyrecipes.Constants.Utils;

import androidx.lifecycle.LiveData;

import com.android.heyrecipes.APIRequests.APIResponse.APIResponse;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<APIResponse<R>>> {
    private Type responseType;

    public LiveDataCallAdapter(Type responseType){
        this.responseType=responseType;
    }
    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<APIResponse<R>> adapt(Call<R> call) {
        return new LiveData<APIResponse<R>>() {
            @Override
            protected void onActive() {
                super.onActive();
                final APIResponse apiResponse=new APIResponse();
                if(!call.isExecuted()){
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            postValue(apiResponse.create(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable t) {
                            postValue(apiResponse.create(t));
                        }
                    });
                }

            }
        };
    }
}

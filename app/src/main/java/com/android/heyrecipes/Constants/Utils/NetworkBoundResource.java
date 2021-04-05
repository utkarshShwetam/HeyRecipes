package com.android.heyrecipes.Constants.Utils;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.heyrecipes.APIRequests.APIResponse.APIResponse;
import com.android.heyrecipes.APIRequests.RestCallExecutors.AppExecutors;
import com.android.heyrecipes.R;

import retrofit2.Response;

// CacheObject: Type for the Resource data. (database cache)
// RequestObject: Type for the API response. (network request)
public abstract class NetworkBoundResource<CacheObject, RequestObject> {

    private static final String TAG = "NetworkBoundResource";
    private AppExecutors appExecutors;
    private MediatorLiveData<Resource<CacheObject>> results = new MediatorLiveData<>();

    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        init();
    }

    //Observing local db and if <condition/> query the local db, stop observing the local db
    private void init() {
        //update liveData for loading status
        results.setValue(Resource.loading(null));

        //observe liveData from source from local db
        final LiveData<CacheObject> dbSource = loadFromDb();

        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                results.removeSource(dbSource);

                if (shouldFetch(cacheObject)) {
                    //get data from api
                    fetchFromNetwork(dbSource);
                } else {
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(CacheObject cacheObject) {

                        }
                    });
                }
            }
        });
    }

    //insert new data in local db and begin observing local db to see the refreshed data from network
    private void fetchFromNetwork(final LiveData<CacheObject> dbSource) {

        Log.e(TAG, "fetchFromNetwork: Called");
        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                setValue(Resource.loading(cacheObject));
            }
        });
        final LiveData<APIResponse<RequestObject>> apiResponseLiveData = createCall();
        results.addSource(apiResponseLiveData, new Observer<APIResponse<RequestObject>>() {
            @Override
            public void onChanged(APIResponse<RequestObject> requestObjectAPIResponse) {
                results.removeSource(dbSource);
                results.removeSource(apiResponseLiveData);

                /*3 cases
                 * 1) API Success Response
                 * 2) API Error Response
                 * 3) API Empty Response*/

                if (requestObjectAPIResponse instanceof APIResponse.APISuccessResponse) {
                    Log.e(TAG, "onChanged: Success response");
                    appExecutors.DiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            //save the response to db
                            saveCallResult((RequestObject) processResponse((APIResponse.APISuccessResponse) requestObjectAPIResponse));
                            appExecutors.MainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                        @Override
                                        public void onChanged(CacheObject cacheObject) {
                                            setValue(Resource.success(cacheObject));
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else if (requestObjectAPIResponse instanceof APIResponse.APIEmptyResponse) {
                    Log.e(TAG, "onChanged: empty response ");
                    appExecutors.MainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                @Override
                                public void onChanged(CacheObject cacheObject) {
                                    setValue(Resource.success(cacheObject));
                                }
                            });
                        }
                    });

                } else if (requestObjectAPIResponse instanceof APIResponse.APIErrorResponse) {
                    Log.e(TAG, "onChanged: Error response");
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(CacheObject cacheObject) {
                            setValue(
                                    Resource.error(
                                            ((APIResponse.APIErrorResponse<RequestObject>) requestObjectAPIResponse).getErrorMessage()
                                    ,cacheObject)
                            );
                        }
                    });
                }

            }
        });
    }

    private CacheObject processResponse(APIResponse.APISuccessResponse<CacheObject> response) {
        return  response.getBody();
    }

    private void setValue(Resource<CacheObject> newValue) {
        if (results.getValue() != newValue) {
            results.setValue(newValue);
        }
    }

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestObject item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<APIResponse<RequestObject>> createCall();

    // Returns a LiveData object that represents the resource that's implemented
    // in the base class.
    public final LiveData<Resource<CacheObject>> getAsLiveData() {
        return results;
    }

    ;
}

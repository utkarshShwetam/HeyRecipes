package com.android.heyrecipes.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.heyrecipes.Constants.Utils.Resource;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.Repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends AndroidViewModel {
    public enum ViewState {CATEGORIES, RECIPES}

    ;
    private static final String TAG = "RecipeListViewModel";
    private MutableLiveData<ViewState> viewState;
    private final MediatorLiveData<Resource<List<RecipeModal>>> recipes = new MediatorLiveData<>();
    private final RecipeRepository recipeRepository;

    private boolean isQueryExhausted;
    private boolean isPerformingQuery;
    private boolean cancelRequest;
    private int pageNumber;
    private String query;

    public RecipeListViewModel(@NonNull Application application) {
        super(application);
        recipeRepository = RecipeRepository.getInstance(application);
        init();
    }

    public void setViewCategories() {
        viewState.setValue(ViewState.CATEGORIES);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    private void init() {
        if (viewState == null) {
            viewState = new MutableLiveData<>();
            viewState.setValue(ViewState.CATEGORIES);
        }
    }

    public LiveData<ViewState> getViewState() {
        return viewState;
    }

    public LiveData<Resource<List<RecipeModal>>> getRecipes() {
        return recipes;
    }

    public void searchRecipesAPI(String query, int pageNumber) {
        if (!isPerformingQuery) {
            if (pageNumber == 0) {
                pageNumber = 1;
            }
            this.pageNumber = pageNumber;
            this.query = query;
            executeSearch();
        }
    }

    public void searchNextPage() {
        if (!isQueryExhausted && !isPerformingQuery) {
            pageNumber++;
            executeSearch();
        }
    }

    private void executeSearch() {
        cancelRequest = false;
        isPerformingQuery = true;
        viewState.setValue(ViewState.RECIPES);
        final LiveData<Resource<List<RecipeModal>>> repositorySource = recipeRepository.searchRecipeAPI(query, pageNumber);
        /*Log.e(TAG, "repoSource: "+repositorySource.getValue().data);*/
        recipes.addSource(repositorySource, new Observer<Resource<List<RecipeModal>>>() {
            @Override
            public void onChanged(Resource<List<RecipeModal>> listResource) {
                if (!cancelRequest) {
                    if (listResource != null) {
                        recipes.setValue(listResource);
                        if (listResource.status == Resource.Status.SUCCESS) {
                            isPerformingQuery = false;
                            if (listResource != null) {
                                if (listResource.data.size() == 0)
                                    Log.e(TAG, "onChanged: EXHAUSTED");
                                recipes.setValue(
                                        new Resource<List<RecipeModal>>(
                                                Resource.Status.ERROR,
                                                listResource.data,
                                                "No more resources"
                                        )
                                );
                            }
                            recipes.removeSource(repositorySource);
                        } else if (listResource.status == Resource.Status.ERROR) {
                            isPerformingQuery = false;
                            recipes.removeSource(repositorySource);
                        }
                    } else {
                        recipes.removeSource(repositorySource);
                    }
                }else {
                    recipes.removeSource(repositorySource);
                }
            }

        });
    }

    public void cancelSearchRequest() {
        if (isPerformingQuery) {
            cancelRequest = true;
            isPerformingQuery = false;
            pageNumber = 1;
        }
    }
}

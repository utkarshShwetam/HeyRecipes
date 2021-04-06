package com.android.heyrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.Adapters.RecipeRecyclerAdapter;
import com.android.heyrecipes.Constants.Utils.Resource;
import com.android.heyrecipes.Constants.Utils.Testing;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.ViewModels.RecipeListViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.List;

public class RecipesListActivity extends BaseActivity implements OnRecipeListener {
    private static final String TAG = "RecipesListActivity";
    //View Model
    private RecipeListViewModel recipeListViewModel;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private SearchView searchView;
    private RecyclerView recyclerView;
    TextView exhausted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        recyclerView = findViewById(R.id.recipe_recycler_list);
        searchView = findViewById(R.id.search_view);
        exhausted = findViewById(R.id.query_exhausted);

        recipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObserver();
        initSearchView();

    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeRecyclerAdapter.displayLoading();
                searchRecipesAPI(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }



    private void subscribeObserver() {
        recipeListViewModel.getRecipes().observe(this, new Observer<Resource<List<RecipeModal>>>() {
            @Override
            public void onChanged(Resource<List<RecipeModal>> listResource) {
                Log.e(TAG, "onChangedStatus: " + listResource.status);
                if (listResource.data != null)
                    switch (listResource.status) {
                        case LOADING: {
                            if (recipeListViewModel.getPageNumber() > 1)
                                recipeRecyclerAdapter.displayLoading();
                            else
                                recipeRecyclerAdapter.displayOnlyLoading();
                            break;
                        }
                        case ERROR: {
                            Log.e(TAG, "onChanged: Cannot refresh the cache");
                            Log.e(TAG, "onChanged: Error message:" + listResource.message);
                            Log.e(TAG, "onChanged: Error, recipes" + listResource.data.size());
                            recipeRecyclerAdapter.hideLoading();
                            recipeRecyclerAdapter.setRecipe(listResource.data);
                            //Toast.makeText(RecipesListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();
                            if (listResource.message.equals("No more resources")) {
                                recipeRecyclerAdapter.setQueryExhausted();
                            }

                            break;
                        }
                        case SUCCESS: {
                            Log.e(TAG, "onChanged: Cache refreshed");
                            Log.e(TAG, "onChanged: Status SUCCESS" + listResource.data.size());
                            recipeRecyclerAdapter.hideLoading();
                            recipeRecyclerAdapter.setRecipe(listResource.data);
                            break;
                        }
                    }
            }
        });
        recipeListViewModel.getViewState().observe(this, new Observer<RecipeListViewModel.ViewState>() {
            @Override
            public void onChanged(RecipeListViewModel.ViewState viewState) {
                if (viewState != null) {
                    switch (viewState) {
                        case RECIPES: {
                            break;
                        }
                        case CATEGORIES: {
                            displaySearchCategory();
                            break;
                        }
                    }
                }
            }

        });


    }

    private void searchRecipesAPI(String query) {
        recyclerView.smoothScrollToPosition(0);
        recipeListViewModel.searchRecipesAPI(query, 1);
        recyclerView.clearFocus();
    }

    private void displaySearchCategory() {
        recipeRecyclerAdapter.displaySearchCategory();
    }

    private RequestManager initGlide(){
        RequestOptions options=new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground);

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    private void initRecyclerView() {
        ViewPreloadSizeProvider<String> viewPreloadSizeProvider=new ViewPreloadSizeProvider<>();
        recipeRecyclerAdapter=new RecipeRecyclerAdapter(this,initGlide(),viewPreloadSizeProvider);
        recyclerView.setAdapter(recipeRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        RecyclerViewPreloader<String> preloader=new RecyclerViewPreloader<String>(Glide.with(this),
                recipeRecyclerAdapter,viewPreloadSizeProvider,30);

        recyclerView.addOnScrollListener(preloader);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1) &&
                        recipeListViewModel.getViewState().getValue()==RecipeListViewModel.ViewState.RECIPES){
                    recipeListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onRecipeClick(int position) {
        startActivity(new Intent(this, RecipeActivity.class).putExtra("recipe", recipeRecyclerAdapter.getSelectedRecipe(position)));
    }

    @Override
    public void onCategoryClick(String category) {
        searchRecipesAPI(category);
    }

    @Override
    public void onBackPressed() {
        if(recipeListViewModel.getViewState().getValue()==RecipeListViewModel.ViewState.CATEGORIES){
            super.onBackPressed();
        }else{
            recipeListViewModel.cancelSearchRequest();
            recipeListViewModel.setViewCategories();
        }
    }
}


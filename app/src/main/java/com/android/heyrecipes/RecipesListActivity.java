package com.android.heyrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.Adapters.RecipeRecyclerAdapter;
import com.android.heyrecipes.ViewModels.RecipeListViewModel;

public class RecipesListActivity extends BaseActivity implements OnRecipeListener {
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

        recipeListViewModel =  ViewModelProviders.of(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObserver();
        initSearchView();
       /* if (!recipeListViewModel.isViewingRecipesCheck()) {
            //display search category
            displaySearchCategories();
        }*/
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeRecyclerAdapter.displayLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /* recipeListViewModel.searchRecipeAPI(query, 1);*/

                    }
                }, 1000);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void subscribeObserver() {
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

    private void displaySearchCategory() {
        recipeRecyclerAdapter.displaySearchCategory();
    }

    private void initRecyclerView() {
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(this);
        recyclerView.setAdapter(recipeRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
    @Override
    public void onRecipeClick(int position) {
        startActivity(new Intent(this, RecipeActivity.class).putExtra("recipe", recipeRecyclerAdapter.getSelectedRecipe(position)));
    }

    @Override
    public void onCategoryClick(String category) {
        recipeRecyclerAdapter.displayLoading();
        /*recipeListViewModel.searchRecipeAPI(category, 1);*/
        searchView.clearFocus();
    }

 /*   //****************************************Observer********************************************
    private void subscribeObserver() {
        recipeListViewModel.getViewState().observe(this, new Observer<RecipeListViewModel.ViewState>() {
            @Override
            public void onChanged(RecipeListViewModel.ViewState viewState) {
                if(viewState!=null){
                    switch (viewState){
                        case RECIPES:{
                            break;
                        }
                        case CATEGORIES:{
                            displaySearchCategories();
                            break;
                        }
                    }
                }
            }

        });

        *//*recipeListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    Log.e("RESPONSE", "In ActivityList: Query Exhausted ");
                recipeRecyclerAdapter.setQueryExhausted();
            }
        });*//*
    }

    private void displaySearchCategories() {
        recipeRecyclerAdapter.displaySearchCategory();
    }

    @Override
    public void onRecipeClick(int position) {
        startActivity(new Intent(this, RecipeActivity.class).putExtra("recipe", recipeRecyclerAdapter.getSelectedRecipe(position)));
    }

    @Override
    public void onCategoryClick(String category) {
        recipeRecyclerAdapter.displayLoading();
        *//*recipeListViewModel.searchRecipeAPI(category, 1);*//*
        searchView.clearFocus();
    }

    @Override
    public void onBackPressed() {
        if (true*//*recipeListViewModel.OnBackPressed()*//*) {
            super.onBackPressed();
        } else {
            displaySearchCategories();
        }

    }*/
}


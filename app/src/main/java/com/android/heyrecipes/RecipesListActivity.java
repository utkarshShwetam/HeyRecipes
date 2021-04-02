package com.android.heyrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.Adapters.RecipeRecyclerAdapter;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.ViewModels.RecipeListViewModel;

import java.util.List;

public class RecipesListActivity extends BaseActivity implements OnRecipeListener {
    //View Model
    private RecipeListViewModel recipeListViewModel;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        recyclerView = findViewById(R.id.recipe_recycler_list);
        searchView = findViewById(R.id.search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (recyclerView.canScrollVertically(1)) {
                    //search the next page
                    recipeListViewModel.searchNextQuery();
                }
            }
        });
        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObserver();
        initSearchView();
        if (!recipeListViewModel.isViewingRecipesCheck()) {
            //display search category
            displaySearchCategories();
        }
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeRecyclerAdapter.displayLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recipeListViewModel.searchRecipeAPI(query, 1);

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

    private void initRecyclerView() {
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(this);
        recyclerView.setAdapter(recipeRecyclerAdapter);
    }

    //****************************************Observer********************************************
    private void subscribeObserver() {
        recipeListViewModel.getRecipes().observe(this, new Observer<List<RecipeModal>>() {
            @Override
            public void onChanged(List<RecipeModal> recipeModals) {
                if (recipeModals != null) {
                    if (recipeListViewModel.isViewingRecipesCheck())
                        for (RecipeModal recipe : recipeModals) {
                            Log.e("RESPONSE", "In Activity: " + recipe.getTitle());
                            recipeListViewModel.setPerformingQueryCheck(false);
                        }
                    recipeRecyclerAdapter.setRecipe(recipeModals);
                }
            }
        });
    }

    private void displaySearchCategories() {
        recipeListViewModel.setViewingRecipesCheck(false);
        recipeRecyclerAdapter.displaySearchCategory();
    }

    @Override
    public void onRecipeClick(int position) {
        startActivity(new Intent(this, RecipeActivity.class).putExtra("recipe", recipeRecyclerAdapter.getSelectedRecipe(position)));
    }

    @Override
    public void onCategoryClick(String category) {
        recipeRecyclerAdapter.displayLoading();
        recipeListViewModel.searchRecipeAPI(category, 1);
        searchView.clearFocus();
    }

    @Override
    public void onBackPressed() {
        if (recipeListViewModel.OnBackPressed()) {
            super.onBackPressed();
        } else {
            displaySearchCategories();
        }

    }
}
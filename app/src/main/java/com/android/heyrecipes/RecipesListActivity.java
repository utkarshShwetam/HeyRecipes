package com.android.heyrecipes;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

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
        recyclerView=findViewById(R.id.recipe_recycler_list);
        searchView=findViewById(R.id.search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObserver();
        initSearchView();
    }

    private void initSearchView(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recipeRecyclerAdapter.displayLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recipeListViewModel.searchRecipeAPI(query, 1);
                    }
                },2000);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initRecyclerView(){
        recipeRecyclerAdapter= new RecipeRecyclerAdapter(this);
        recyclerView.setAdapter(recipeRecyclerAdapter);
    }

    //****************************************Observer********************************************
    private void subscribeObserver() {
        recipeListViewModel.getRecipes().observe(this, new Observer<List<RecipeModal>>() {
            @Override
            public void onChanged(List<RecipeModal> recipeModals) {
                if (recipeModals != null) {
                    for (RecipeModal recipe : recipeModals) {
                        Log.e("RESPONSE", "In Activity: " + recipe.getTitle());
                    }
                    recipeRecyclerAdapter.setRecipe(recipeModals);
                }
            }
        });
    }

    //*********************************************retrofit calls*************************************

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}
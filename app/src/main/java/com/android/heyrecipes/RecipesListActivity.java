package com.android.heyrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        recyclerView=findViewById(R.id.recipe_recycler_list);
        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObserver();
        testRetro();
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

    private void searchRecipeApi(String query, int pageNumber) {
        recipeListViewModel.searchRecipeAPI(query, pageNumber);
    }

    //*********************************************retrofit calls*************************************
    private void testRetro() {
        searchRecipeApi("Pasta", 1);
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}
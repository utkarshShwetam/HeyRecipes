package com.android.heyrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.ViewModels.RecipeViewModal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.SimpleTimeZone;
import java.util.concurrent.BlockingDeque;

public class RecipeActivity extends BaseActivity {
    private ImageView imageView;
    private TextView recipeTitle, recipeRank;
    private LinearLayout recipeIngredientContainer;
    private ScrollView scrollView;
    private RecipeViewModal recipeViewModal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
        imageView = findViewById(R.id.recipe_image);
        recipeTitle = findViewById(R.id.recipe_title);
        recipeRank = findViewById(R.id.recipe_social_score);
        recipeIngredientContainer = findViewById(R.id.ingredients_container);
        scrollView = findViewById(R.id.parent);

        recipeViewModal =  ViewModelProviders.of(this).get(RecipeViewModal.class);
        getIncomingIntent();
        /*subscribeObserver();*/

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            RecipeModal recipeModal = getIntent().getParcelableExtra("recipe");
            Log.e("Recipe Act", "getIncomingIntent: " + recipeModal.getTitle());
            /*recipeViewModal.searchRecipeByID(recipeModal.getRecipe_id());*/
        }
    }

    /*private void subscribeObserver() {
        recipeViewModal.isRecipeRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !recipeViewModal.isDidReceiveRecipe()) {
                    Log.e("ERROR TIMEOUT", "onChanged: TIMEOUT");
                    displayError("Error occurred.Check Network Connection");
                }
            }
        });*/

        /*recipeViewModal.getRecipe().observe(this, new Observer<RecipeModal>() {
            @Override
            public void onChanged(RecipeModal recipeModal) {
                if (recipeModal != null) {
                    if (recipeModal.getRecipe_id().equals(recipeViewModal.getRecipeID())) {
                        setRecipeProperties(recipeModal);
                        recipeViewModal.setDidReceiveRecipe(true);
                    }
                    *//*Log.e("Ingredients", "onChanged: " + recipeModal.getTitle());*//*
                    *//*for (String ingredients : recipeModal.getIngredients()) {
                        Log.e("Ingredients", "onChanged: " + ingredients);
                    }*//*
                }
            }
        });
    }

    private void displayError(String error) {
        recipeTitle.setText("ERROR");
        TextView textView = new TextView(this);
        recipeRank.setText("");
        if (!error.equals("")) {
            textView.setText(error);
        } else {
            textView.setText("ERROR");
        }
        textView.setTextSize(15);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        recipeIngredientContainer.addView(textView);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_error);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.ic_error)
                .into(imageView);
        showParent();
    }

    private void setRecipeProperties(RecipeModal recipeModal) {
        if (recipeModal != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_error);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipeModal.getImage_url())
                    .into(imageView);
            recipeTitle.setText(recipeModal.getTitle());
            recipeRank.setText(String.valueOf(recipeModal.getSocial_rank()));
            recipeIngredientContainer.removeAllViews();

            for (String ingredients : recipeModal.getIngredients()) {
                TextView textView = new TextView(this);
                textView.setText(ingredients);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                recipeIngredientContainer.addView(textView);
            }

        }
        showParent();
    }*/

    private void showParent() {
        scrollView.setVisibility(View.VISIBLE);
    }
}

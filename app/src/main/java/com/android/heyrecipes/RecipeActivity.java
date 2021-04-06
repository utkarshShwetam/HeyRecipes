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

import com.android.heyrecipes.Constants.Utils.Resource;
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

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            RecipeModal recipeModal = getIntent().getParcelableExtra("recipe");
            Log.e("Recipe Act", "getIncomingIntent: " + recipeModal.getTitle());
            subscribeObservers(recipeModal.getRecipe_id());
        }
    }

    private void subscribeObservers(final String recipeId){
        recipeViewModal.searchRecipeAPI(recipeId).observe(this, new Observer<Resource<RecipeModal>>() {
            @Override
            public void onChanged(Resource<RecipeModal> recipeModalResource) {
                if(recipeModalResource!=null){
                    if (recipeModalResource.data!=null){
                        switch(recipeModalResource.status){
                            case LOADING:{
                                shimmerFrameLayout.startShimmer();
                                break;
                            }
                            case ERROR:{
                                showParent();
                                shimmerFrameLayout.stopShimmer();
                                break;
                            }
                            case SUCCESS:{
                                showParent();
                                shimmerFrameLayout.stopShimmer();
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void showParent() {
        scrollView.setVisibility(View.VISIBLE);
    }
}

package com.android.heyrecipes.Adapters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;
import com.google.android.material.textview.MaterialTextView;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    MaterialTextView title, publisher, socialScore;
    ImageView image;
    OnRecipeListener onRecipeListener;
    RequestManager requestManager;
    ViewPreloadSizeProvider<String> preloadSizeProvider;

    public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener
            , RequestManager requestManager, ViewPreloadSizeProvider<String> viewPreloadSizeProvider) {
        super(itemView);
        this.onRecipeListener = onRecipeListener;
        this.requestManager=requestManager;
        this.preloadSizeProvider=viewPreloadSizeProvider;
        title = itemView.findViewById(R.id.recipe_title);
        publisher = itemView.findViewById(R.id.recipe_publisher);
        socialScore = itemView.findViewById(R.id.recipe_social_score);
        image = itemView.findViewById(R.id.recipe_image);

        itemView.setOnClickListener(this);
    }

    public void OnBind (RecipeModal recipeModal){

        requestManager
                .load(recipeModal.getImage_url())
                .centerCrop()
                .into(image);

        title.setText(recipeModal.getTitle());
        publisher.setText(recipeModal.getPublisher());
        socialScore.setText(String.valueOf(Math.round(recipeModal.getSocial_rank())));

        preloadSizeProvider.setView(image);

    }
    @Override
    public void onClick(View v) {
        onRecipeListener.onRecipeClick(getAdapterPosition());
        Log.e("CAT CLICK", "onClick: clicked");
    }
}

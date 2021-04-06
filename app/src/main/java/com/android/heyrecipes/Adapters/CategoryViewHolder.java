package com.android.heyrecipes.Adapters;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    OnRecipeListener onRecipeListener;
    TextView categoryTitle;
    CircleImageView categoryImage;
    RequestManager requestManager;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener, RequestManager requestManager) {
        super(itemView);
        this.onRecipeListener=onRecipeListener;
        this.requestManager=requestManager;
        categoryImage=itemView.findViewById(R.id.category_image);
        categoryTitle=itemView.findViewById(R.id.category_title);

        itemView.setOnClickListener(this);
    }

    public void OnBind (RecipeModal recipeModal){
        Uri path = Uri.parse("android.resource://com.android.heyrecipes/drawable/" + recipeModal.getImage_url());
       requestManager
                .load(path)
                .into(categoryImage);
       categoryTitle.setText(recipeModal.getTitle());
    }

    @Override
    public void onClick(View v) {
        onRecipeListener.onCategoryClick(categoryTitle.getText().toString());
    }
}

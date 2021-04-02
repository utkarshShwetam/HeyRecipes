package com.android.heyrecipes.Adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.R;
import com.google.android.material.textview.MaterialTextView;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    MaterialTextView title,publisher,socialScore;
    AppCompatImageView image;
    OnRecipeListener onRecipeListener;
    public RecipeViewHolder(@NonNull View itemView,OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener=onRecipeListener;
        title=itemView.findViewById(R.id.recipe_title);
        publisher=itemView.findViewById(R.id.recipe_publisher);
        socialScore=itemView.findViewById(R.id.recipe_social_score);
        image=itemView.findViewById(R.id.recipe_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        OnRecipeListener.onRecipeClick(getAdapterPosition());
    }
}

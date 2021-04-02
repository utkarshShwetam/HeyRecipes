package com.android.heyrecipes.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    OnRecipeListener onRecipeListener;
    TextView categoryTitle;
    CircleImageView categoryImage;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener=onRecipeListener;
        categoryImage=itemView.findViewById(R.id.category_image);
        categoryTitle=itemView.findViewById(R.id.category_title);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRecipeListener.onCategoryClick(categoryTitle.getText().toString());
    }
}

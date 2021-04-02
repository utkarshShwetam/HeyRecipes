package com.android.heyrecipes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RecipeModal> recipeModalList;
    private OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item,parent,false);
        return new RecipeViewHolder(view,onRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_error);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(recipeModalList.get(position).getImage_url())
                .centerCrop()
                .into(((RecipeViewHolder)holder).image);

        ((RecipeViewHolder)holder).title.setText(recipeModalList.get(position).getTitle());
        ((RecipeViewHolder)holder).publisher.setText(recipeModalList.get(position).getPublisher());
        ((RecipeViewHolder)holder).socialScore.setText(String.valueOf(Math.round(recipeModalList.get(position).getSocial_rank())));

    }

    @Override
    public int getItemCount() {
        if(recipeModalList!=null)
            return recipeModalList.size();
        return  0;
    }

    public void setRecipe(List<RecipeModal> recipe){
        recipeModalList=recipe;
        notifyDataSetChanged();
    }
}

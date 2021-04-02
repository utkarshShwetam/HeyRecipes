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

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private List<RecipeModal> recipeModalList;
    private final OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case RECIPE_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, onRecipeListener);
            }

            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }

            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_error);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipeModalList.get(position).getImage_url())
                    .centerCrop()
                    .into(((RecipeViewHolder) holder).image);

            ((RecipeViewHolder) holder).title.setText(recipeModalList.get(position).getTitle());
            ((RecipeViewHolder) holder).publisher.setText(recipeModalList.get(position).getPublisher());
            ((RecipeViewHolder) holder).socialScore.setText(String.valueOf(Math.round(recipeModalList.get(position).getSocial_rank())));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(recipeModalList.get(position).getTitle().equals("Loading")){
            return LOADING_TYPE;
        }else {
            return RECIPE_TYPE;
        }
    }

    public void displayLoading(){
    if(!isLoading()){
        RecipeModal recipeModal=new RecipeModal();
        recipeModal.setTitle("Loading");;
        List<RecipeModal> loadingList=new ArrayList<>();
        loadingList.add(recipeModal);
        recipeModalList=loadingList;
        notifyDataSetChanged();
    }

    }

    private boolean isLoading(){
        if(recipeModalList!=null){
            if(recipeModalList.size()>0){
                return recipeModalList.get(recipeModalList.size() - 1).getTitle().equals("Loading");
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (recipeModalList != null)
            return recipeModalList.size();
        return 0;
    }

    public void setRecipe(List<RecipeModal> recipe) {
        recipeModalList = recipe;
        notifyDataSetChanged();
    }
}

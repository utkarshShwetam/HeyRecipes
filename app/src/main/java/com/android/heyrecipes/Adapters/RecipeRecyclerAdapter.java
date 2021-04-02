package com.android.heyrecipes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.Constants.ConstantsValues;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;
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
            case CATEGORY_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_item, parent, false);
                return new CategoryViewHolder(view, onRecipeListener);
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
        } else if (itemViewType == CATEGORY_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load("")
                    .centerCrop()
                    .into(((CategoryViewHolder) holder).categoryImage);

            ((CategoryViewHolder) holder).categoryTitle.setText(recipeModalList.get(position).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (recipeModalList.get(position).getSocial_rank() == -1) {
            return CATEGORY_TYPE;
        } else if (recipeModalList.get(position).getTitle().equals("Loading")) {
            return LOADING_TYPE;
        } else if (position==recipeModalList.size()-1 && position!=0 && !recipeModalList.get(position).getTitle().equals("Exhausted")) {
            return LOADING_TYPE;
        }else {
            return RECIPE_TYPE;
        }
    }

    public void displayLoading() {
        if (!isLoading()) {
            RecipeModal recipeModal = new RecipeModal();
            recipeModal.setTitle("Loading");
            List<RecipeModal> loadingList = new ArrayList<>();
            loadingList.add(recipeModal);
            recipeModalList = loadingList;
            notifyDataSetChanged();
        }

    }

    public void displaySearchCategory() {
        List<RecipeModal> categories = new ArrayList<>();
        for (int i = 0; i < ConstantsValues.CATEGORIES_TYPE.length; i++) {
            RecipeModal recipeModal = new RecipeModal();
            recipeModal.setTitle(ConstantsValues.CATEGORIES_TYPE[i]);
            recipeModal.setImage_url("");
            recipeModal.setSocial_rank(-1);
            categories.add(recipeModal);
        }
        recipeModalList = categories;
        notifyDataSetChanged();
    }

    private boolean isLoading() {
        if (recipeModalList != null) {
            if (recipeModalList.size() > 0) {
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

    public RecipeModal getSelectedRecipe(int position){
        if(recipeModalList!=null);
        {
            if (recipeModalList.size() > 0) {
                return recipeModalList.get(position);
            }
        }
        return null;
    }

}

package com.android.heyrecipes.Adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.Adapters.OnClickInterface.OnRecipeListener;
import com.android.heyrecipes.Constants.ConstantsValues;
import com.android.heyrecipes.DataModals.RecipeModal;
import com.android.heyrecipes.R;
import com.android.heyrecipes.ViewModels.RecipeViewModal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ListPreloader.PreloadModelProvider {
    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;
    private static final int QUERY_EXHAUSTED_TYPE = 4;
    private List<RecipeModal> recipeModalList;
    private final OnRecipeListener onRecipeListener;
    private RequestManager requestManager;
    private ViewPreloadSizeProvider<String> preloadSizeProvider;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener,RequestManager requestManager
    ,ViewPreloadSizeProvider<String> viewPreloadSizeProvider) {
        this.onRecipeListener = onRecipeListener;
        this.requestManager=requestManager;
        this.preloadSizeProvider=viewPreloadSizeProvider;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case RECIPE_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, onRecipeListener,requestManager,preloadSizeProvider);
            }

            case LOADING_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }
            case CATEGORY_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_list_item, parent, false);
                return new CategoryViewHolder(view, onRecipeListener,requestManager);
            }
            case QUERY_EXHAUSTED_TYPE: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_query_exhausted, parent, false);
                return new QueryExhaustedViewHolder(view);
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
            ((RecipeViewHolder)holder).OnBind(recipeModalList.get(position));
        } else if (itemViewType == CATEGORY_TYPE) {
            ((CategoryViewHolder) holder).OnBind(recipeModalList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (recipeModalList.get(position).getSocial_rank() == -1) {
            return CATEGORY_TYPE;
        } else if (recipeModalList.get(position).getTitle().equals("Loading")) {
            return LOADING_TYPE;
        } else if (recipeModalList.get(position).getTitle().equals("Exhausted")) {
            return QUERY_EXHAUSTED_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    public void displayOnlyLoading() {
        clearRecipeList();
        RecipeModal recipe = new RecipeModal();
        recipe.setTitle("Loading");
        recipeModalList.add(recipe);
        notifyDataSetChanged();
    }

    private void clearRecipeList() {
        if (recipeModalList == null) {
            recipeModalList = new ArrayList<>();
        } else {
            recipeModalList.clear();
        }
        notifyDataSetChanged();
    }

    public void setQueryExhausted() {
        hideLoading();
        RecipeModal exhaustedRecipeModal = new RecipeModal();
        exhaustedRecipeModal.setTitle("Exhausted");
        recipeModalList.add(exhaustedRecipeModal);
        notifyDataSetChanged();
    }

    public void hideLoading() {
        if (isLoading()) {
            if (recipeModalList.get(0).getTitle().equals("Loading")) {
                recipeModalList.remove(0);
            } else if (recipeModalList.get(recipeModalList.size() - 1).getTitle().equals("Loading")) {
                recipeModalList.remove(recipeModalList.size() - 1);
            }
            notifyDataSetChanged();
        }
    }

    //pagination
    public void displayLoading() {
        if (recipeModalList == null) {
            recipeModalList = new ArrayList<>();
        }
        if (!isLoading()) {
            RecipeModal recipe = new RecipeModal();
            recipe.setTitle("Loading");
            recipeModalList.add(recipe);
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

    public RecipeModal getSelectedRecipe(int position) {
        if (recipeModalList != null) ;
        {
            if (recipeModalList.size() > 0) {
                return recipeModalList.get(position);
            }
        }
        return null;
    }

    @NonNull
    @Override
    public List<String> getPreloadItems(int position) {
        String url =recipeModalList.get(position).getImage_url();
        if(TextUtils.isEmpty(url)){
           return Collections.emptyList();
        }
        return Collections.singletonList(url);
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull Object item) {
        return requestManager.load(item);
    }
}

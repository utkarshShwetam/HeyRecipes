package com.android.heyrecipes.Adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.findViewById(R.id.shimmerQuery);
    }
}

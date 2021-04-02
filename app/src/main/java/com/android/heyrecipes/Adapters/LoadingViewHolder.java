package com.android.heyrecipes.Adapters;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.R;
import com.facebook.shimmer.ShimmerFrameLayout;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    ShimmerFrameLayout shimmerFrameLayout;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        shimmerFrameLayout = itemView.findViewById(R.id.shimmerQuery);
        shimmerFrameLayout.startShimmer();
    }
}

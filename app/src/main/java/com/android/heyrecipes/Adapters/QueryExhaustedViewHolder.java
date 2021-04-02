package com.android.heyrecipes.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.heyrecipes.R;

class QueryExhaustedViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    public QueryExhaustedViewHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.query_exhausted);
        textView.setVisibility(View.VISIBLE);
    }
}

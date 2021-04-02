package com.android.heyrecipes;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.progressindicator.CircularProgressIndicator;

public abstract class BaseActivity extends AppCompatActivity {
    public ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        shimmerFrameLayout = constraintLayout.findViewById(R.id.shimmer_base_act);
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        super.setContentView(layoutResID);
    }

    public void showProgressBar(boolean visible) {
        Log.e("CHECK", "showProgressBar: Called");
        int value =visible ? View.VISIBLE : View.INVISIBLE;
        Log.e("CHECK", "showProgressBar: Called"+value+" "+visible);
        if(visible) {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
        }else{
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.INVISIBLE);
        }

    }
}

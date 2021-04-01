package com.android.heyrecipes;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.progressindicator.CircularProgressIndicator;

public abstract class BaseActivity extends AppCompatActivity {
    public CircularProgressIndicator progressIndicator;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        progressIndicator = constraintLayout.findViewById(R.id.progress_bar);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        super.setContentView(layoutResID);
    }

    public void showProgressBar(boolean visible) {
        Log.e("CHECK", "showProgressBar: Called");
        progressIndicator.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}

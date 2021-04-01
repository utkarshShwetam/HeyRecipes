package com.android.heyrecipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class RecipesListActivity extends BaseActivity {
    MaterialButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        button=findViewById(R.id.test);


    }
    public void check(View view){
        Log.e("Button", "check: Clicked");
        if(progressIndicator.getVisibility() == View.VISIBLE)
            showProgressBar(false);
        else
            showProgressBar(true);
    }
}
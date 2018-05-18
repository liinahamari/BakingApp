package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.base.LikeSyncActivity;

/**
 * Created by l1maginaire on 4/27/18.
 */

public class DetailActivity extends LikeSyncActivity implements StepsAdapter.Callbacks {
    public static final String ID = "id";
    private int recipeId;

    public static Intent newIntent(Context context, int recipeId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ID, recipeId);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        recipeId = getIntent().getIntExtra(ID, 0);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getFragment() {
        return DetailFragment.newInstance(recipeId);
    }

    @Override
    protected void notifyMainListAdapter() {
        //do not implement
    }

    @Override
    public void onStepClicked(int position) {
        startActivity(PagerActivity.newIntent(this, recipeId, position));
    }
}

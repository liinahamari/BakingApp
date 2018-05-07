package com.example.guest.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.base.BaseActivity;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;
import com.example.guest.bakingapp.utils.NetworkChecker;

import java.util.ArrayList;

/**
 * Created by l1maginaire on 4/27/18.
 */

public class DetailActivity extends BaseActivity implements StepsAdapter.Callbacks {
    public static final String ID = "id";
    private RecipeRemote recipeRemote;

    public static Intent newIntent(Context context, RecipeRemote recipeRemote) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ID, recipeRemote);
        return intent;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        recipeRemote = getIntent().getParcelableExtra(ID);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment getFragment() {
        return DetailFragment.newInstance(recipeRemote);
    }

    @Override
    public void onStepClicked(int position) {
        if (NetworkChecker.isNetAvailable(this))
            startActivity(PagerActivity.newIntent(this, (ArrayList<StepRemote>) recipeRemote.getStepRemotes(), position));
    }
}

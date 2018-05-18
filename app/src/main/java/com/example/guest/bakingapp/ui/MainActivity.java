package com.example.guest.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.base.LikeSyncActivity;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;
import com.example.guest.bakingapp.utils.SimpleIdlingResource;

import java.util.ArrayList;

public class MainActivity extends LikeSyncActivity implements MainFragment.Callbacks, StepsAdapter.Callbacks {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String RETAIN_POSITION = "position";
    private static final String RETAIN_FRAGMENT = "fragment";
    private static final String RETAIN_RECIPE_ID = "recipe";

    private int recipeId;
    private int position;
    private MainFragment mainFragment;

    @Nullable
    private SimpleIdlingResource idlingResource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getIdlingResource();
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(RETAIN_POSITION);
            recipeId = savedInstanceState.getInt(RETAIN_RECIPE_ID);
            mainFragment = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, RETAIN_FRAGMENT);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment getFragment() {
        mainFragment = new MainFragment();
        mainFragment.setIdleResource(idlingResource);
        return mainFragment;
    }

    @Override
    protected void notifyMainListAdapter() {
        mainFragment.notifyItemSetChanged(position);
    }

    @Override
    public void onItemClicked(int recipeId, int position) {
        this.position = position;
        this.recipeId = recipeId;
        Log.d(TAG, "Element №" + recipeId + " chosen.");
        if (findViewById(R.id.twopane_detail_container) == null) {
            startActivity(DetailActivity.newIntent(this, recipeId));
        } else {
            Fragment detailFragment = DetailFragment.newInstance(recipeId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.twopane_detail_container, detailFragment)
                    .commit();
        }
    }

    @Override
    public void onStepClicked(int position) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.twopane_detail_container, PagerFragment.newInstance(recipeId, position))
                .commit();
    }

    public void setFab(FloatingActionButton fab) {
        mainFragment.setFab(fab, position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RETAIN_POSITION, position);
        outState.putInt(RETAIN_RECIPE_ID, recipeId);
        getSupportFragmentManager().putFragment(outState, RETAIN_FRAGMENT, mainFragment);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }
}

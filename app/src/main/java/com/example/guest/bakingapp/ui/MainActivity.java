package com.example.guest.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.base.LikeSyncActivity;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends LikeSyncActivity implements MainFragment.Callbacks, StepsAdapter.Callbacks{
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String RETAIN_POSITION = "position";
    private static final String RETAIN_FRAGMENT = "fragment";
    private static final String RETAIN_RECIPE = "recipe";
    private RecipeRemote recipeRemote;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int position;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(RETAIN_POSITION);
            recipeRemote = savedInstanceState.getParcelable(RETAIN_RECIPE);
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
        return mainFragment;
    }

    @Override
    protected void notifyMainListAdapter() {
        mainFragment.notifyItemSetChanged(position);
    }

    @Override
    public void onItemClicked(RecipeRemote recipeRemote, int position) {
        this.position = position;
        this.recipeRemote = recipeRemote;
        Log.d(TAG, recipeRemote.getName() + " chosen.");
        if (findViewById(R.id.twopane_detail_container) == null) {
            startActivity(DetailActivity.newIntent(this, recipeRemote));
        } else {
            Fragment detailFragment = DetailFragment.newInstance(recipeRemote);
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
                .replace(R.id.twopane_detail_container, PagerFragment.newInstance((ArrayList<StepRemote>) recipeRemote.getStepRemotes(), position))
                .commit();
    }

    public void setFab(FloatingActionButton fab) {
        mainFragment.setFab(fab, position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RETAIN_POSITION, position);
        outState.putParcelable(RETAIN_RECIPE, recipeRemote);
        getSupportFragmentManager().putFragment(outState, RETAIN_FRAGMENT, mainFragment);
    }

    @Override
    protected void onDestroy() { //todo onStop?
        super.onDestroy();
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }


}

package com.example.guest.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.base.BaseActivity;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;
import com.example.guest.bakingapp.utils.NetworkChecker;

import java.util.ArrayList;

import static com.example.guest.bakingapp.utils.NetworkChecker.isNetAvailable;

public class MainActivity extends BaseActivity implements MainFragment.Callbacks, StepsAdapter.Callbacks {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecipeRemote recipeRemote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment getFragment() {
        return new MainFragment();
    }

    @Override
    public void onItemClicked(RecipeRemote recipeRemote, int position) {
        this.recipeRemote = recipeRemote;
        Log.d(TAG, recipeRemote.getName() + " chosen.");
        if (isNetAvailable(this)) {
            if (findViewById(R.id.twopane_detail_container) == null) {
                startActivity(DetailActivity.newIntent(this, recipeRemote));
            } else {
                Fragment detailFragment = DetailFragment.newInstance(recipeRemote);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.twopane_detail_container, detailFragment)
                        .commit();
            }
        } else {
            Toast.makeText(this, "Lack of connection, try again later...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStepClicked(int position) {
        if (NetworkChecker.isNetAvailable(this))
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.twopane_detail_container, PagerFragment.newInstance((ArrayList<StepRemote>) recipeRemote.getStepRemotes(), position))
                    .commit();
    }
}

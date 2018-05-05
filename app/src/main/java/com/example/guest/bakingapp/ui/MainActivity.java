package com.example.guest.bakingapp.ui;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.base.BaseActivity;
import com.example.guest.bakingapp.mvp.model.Recipe;
import com.example.guest.bakingapp.mvp.model.Step;
import com.example.guest.bakingapp.utils.NetworkChecker;

import java.util.ArrayList;

import static com.example.guest.bakingapp.utils.NetworkChecker.isNetAvailable;

public class MainActivity extends BaseActivity implements MainFragment.Callbacks, StepsAdapter.Callbacks {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Recipe recipe;

    @Override
    protected int getContentView() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment getFragment() {
        return new MainFragment();
    }

    @Override
    public void onItemClicked(Recipe recipe, int position) {
        this.recipe = recipe;
        Log.d(TAG, recipe.getName() + " chosen.");
        if (isNetAvailable(this)) {
            if (findViewById(R.id.twopane_detail_container) == null) {
                startActivity(DetailActivity.newIntent(this, recipe));
            } else {
                Fragment detailFragment = DetailFragment.newInstance(recipe);
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
                    .replace(R.id.twopane_detail_container, PagerFragment.newInstance((ArrayList<Step>) recipe.getSteps(), position))
                    .commit();
    }
}

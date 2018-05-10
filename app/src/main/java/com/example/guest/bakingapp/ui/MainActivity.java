package com.example.guest.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.adapters.StepsAdapter;
import com.example.guest.bakingapp.base.BaseActivity;
import com.example.guest.bakingapp.data.local.LocalDataSource;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;
import com.example.guest.bakingapp.utils.LikeButtonColorChanger;
import com.example.guest.bakingapp.utils.NetworkChecker;
import com.example.guest.bakingapp.utils.RxThreadManager;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

import static com.example.guest.bakingapp.utils.NetworkChecker.isNetAvailable;

public class MainActivity extends BaseActivity implements MainFragment.Callbacks, StepsAdapter.Callbacks, DetailFragment.Callbacks {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecipeRemote recipeRemote;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int position;
    private MainFragment mainFragment;

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
        mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public void onItemClicked(RecipeRemote recipeRemote, int position) {
        this.position = position;
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
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.twopane_detail_container, PagerFragment.newInstance((ArrayList<StepRemote>) recipeRemote.getStepRemotes(), position))
                    .commit();
    }

    @Override
    public void onLikeClicked(RecipeRemote recipeRemote, FloatingActionButton fab) {
        if (recipeRemote.isFavorite() == 0) {
            fab.setClickable(false);
            compositeDisposable.add(Single.fromCallable(() -> LocalDataSource.insert(recipeRemote, this))
                    .compose(RxThreadManager.manageSingle())
                    .subscribe(uri -> {
                        recipeRemote.setFavorite(1);
                        LikeButtonColorChanger.change(fab, this, 1);
                        fab.setClickable(true);
                    }));
        } else {
            fab.setClickable(false);
            compositeDisposable.add(Single.fromCallable(() -> LocalDataSource.delete(recipeRemote.getId(), this))
                    .compose(RxThreadManager.manageSingle())
                    .subscribe(rowsDeleted -> {
                        if (rowsDeleted != 0) {
                            recipeRemote.setFavorite(0);
                            LikeButtonColorChanger.change(fab, this, 0);
                        }
                        fab.setClickable(true);
                    }));
        }
    }

    public void setFab(FloatingActionButton fab){
        mainFragment.setFab(fab, position);
    }

    @Override
    protected void onDestroy() { //todo onStop?
        super.onDestroy();
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}

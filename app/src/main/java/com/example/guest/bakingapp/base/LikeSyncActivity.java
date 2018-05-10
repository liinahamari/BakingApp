package com.example.guest.bakingapp.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.data.local.LocalDataSource;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.ui.DetailFragment;
import com.example.guest.bakingapp.utils.LikeButtonColorChanger;
import com.example.guest.bakingapp.utils.RxThreadManager;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by l1maginaire on 4/14/18.
 */

public abstract class LikeSyncActivity extends SingleFragmentActivity implements DetailFragment.Callbacks {

    @SuppressLint("CheckResult")
    @Override
    public void onLikeClicked(FloatingActionButton fab, RecipeRemote recipe) {
        Single.fromCallable(() -> LocalDataSource.isFavorite(this, recipe.getId()))
                .observeOn(Schedulers.io())
                .flatMap(isFavorite -> {
                    if (isFavorite) {
                        return Single.fromCallable(() -> LocalDataSource.delete(recipe.getId(), this));
                    } else {
                        return Single.fromCallable(() -> LocalDataSource.insert(recipe, this));
                    }
                })
                .compose(RxThreadManager.manageSingle())
                .subscribe(isFavorite -> {
                    LikeButtonColorChanger.change(fab, this, isFavorite);
                    if (findViewById(R.id.twopane_detail_container) != null) //twopane mode
                        notifyMainListAdapter();
                    fab.setClickable(true);
                });
    }

    protected abstract void notifyMainListAdapter();
}

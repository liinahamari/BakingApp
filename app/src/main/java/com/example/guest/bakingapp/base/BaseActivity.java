package com.example.guest.bakingapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.mvp.model.Reciep;
import com.example.guest.bakingapp.ui.DetailFragment;
import com.example.guest.bakingapp.utils.DbOperations;
import com.example.guest.bakingapp.utils.LikeButtonColorChanger;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by l1maginaire on 4/14/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements DetailFragment.Callbacks {
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        setContentView(getContentView());
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = getFragment();
            manager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onLikeClicked(Reciep reciep, FloatingActionButton fab) {
        if (reciep.isFavorite() == 0) {
            fab.setClickable(false);
            compositeDisposable.add(Single.fromCallable(() -> DbOperations.insert(reciep, this))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(uri -> {
                        reciep.setFavorite(1);
                        LikeButtonColorChanger.change(fab, this, 1);
                        fab.setClickable(true);
                    }));
        } else {
            fab.setClickable(false);
            compositeDisposable.add(Single.fromCallable(() -> DbOperations.delete(reciep.getId(), this))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(rowsDeleted -> {
                        if (rowsDeleted != 0) {
                            reciep.setFavorite(0);
                            LikeButtonColorChanger.change(fab, this, 0);
                        }
                        fab.setClickable(true);
                    }));
        }
    }

    @Override
    protected void onDestroy() { //todo onStop?
        super.onDestroy();
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    protected abstract int getContentView();

    protected abstract Fragment getFragment();
}

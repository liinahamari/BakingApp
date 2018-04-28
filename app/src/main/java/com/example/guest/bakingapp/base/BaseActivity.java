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

/**
 * Created by l1maginaire on 4/14/18.
 */

public abstract class BaseActivity extends AppCompatActivity implements DetailFragment.Callbacks{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onLikeClicked(Reciep reciep, FloatingActionButton floatingButton) {

    }

    protected abstract int getContentView();
    protected abstract Fragment getFragment();
}

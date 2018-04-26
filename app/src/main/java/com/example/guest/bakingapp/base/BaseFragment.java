package com.example.guest.bakingapp.base;

/**
 * Created by l1maginaire on 4/14/18.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.guest.bakingapp.App;
import com.example.guest.bakingapp.di.components.ApplicationComponent;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        init();
    }

    protected abstract void init();

    protected abstract void resolveDaggerDependencies();

    protected ApplicationComponent getApplicationComponent(Activity activity) {
        return ((App) activity.getApplication()).getApplicationComponent();
    }
}
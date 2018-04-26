package com.example.guest.bakingapp;

import android.app.Application;

import com.example.guest.bakingapp.di.components.ApplicationComponent;
import com.example.guest.bakingapp.di.components.DaggerApplicationComponent;
import com.example.guest.bakingapp.di.modules.ApplicationModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by l1maginaire on 4/14/18.
 */

public class App extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        initializeAppComponent();
    }

    private void initializeAppComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}

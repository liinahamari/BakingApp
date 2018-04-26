package com.example.guest.bakingapp.di.modules;

/**
 * Created by l1maginaire on 4/14/18.
 */

import com.example.guest.bakingapp.BakingApi;
import com.example.guest.bakingapp.di.scope.PerActivity;
import com.example.guest.bakingapp.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by l1maginaire on 3/1/18.
 */

@Module
public class BakingModule {

    private MainView view;

    public BakingModule(MainView view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    BakingApi provideApiService(Retrofit retrofit) {
        return retrofit.create(BakingApi.class);
    }

    @PerActivity
    @Provides
    MainView provideView() {
        return view;
    }
}
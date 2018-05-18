package com.example.guest.bakingapp.di.modules;

/**
 * Created by l1maginaire on 4/14/18.
 */

import com.example.guest.bakingapp.BakingApi;
import com.example.guest.bakingapp.di.scope.PerFragment;
import com.example.guest.bakingapp.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class BakingModule {

    private MainView view;

    public BakingModule(MainView view) {
        this.view = view;
    }

    @PerFragment
    @Provides
    BakingApi provideApiService(Retrofit retrofit) {
        return retrofit.create(BakingApi.class);
    }

    @PerFragment
    @Provides
    MainView provideView() {
        return view;
    }
}
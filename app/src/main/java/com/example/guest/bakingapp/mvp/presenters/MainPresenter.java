package com.example.guest.bakingapp.mvp.presenters;

import android.content.Context;
import android.util.Log;

import com.example.guest.bakingapp.BakingApi;
import com.example.guest.bakingapp.base.BasePresenter;
import com.example.guest.bakingapp.mvp.view.MainView;
import com.example.guest.bakingapp.utils.RxThreadManager;
import com.example.guest.bakingapp.utils.SimpleIdlingResource;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by l1maginaire on 4/26/18.
 */

public class MainPresenter extends BasePresenter<MainView> {
    private static final String TAG = MainPresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;

    @Inject
    protected Context context;
    @Inject
    protected BakingApi apiService;

    @Inject
    public MainPresenter() {
        compositeDisposable = new CompositeDisposable();
    }

    public void getRecieps(SimpleIdlingResource resource) {
        if (resource != null) resource.setIdleState(false);
        compositeDisposable.add(apiService.getRecieps()
                .compose(RxThreadManager.manageObservable())
                .subscribe(recipes -> {
                            getView().onRecipesLoaded(recipes);
                            if (resource != null) resource.setIdleState(true);
                        },
                            throwable -> Log.e(TAG, throwable.getMessage())));
    }

    public void unsibscibe() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }
}

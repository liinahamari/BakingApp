package com.example.guest.bakingapp.mvp.presenters;

import android.content.Context;
import android.util.Log;

import com.example.guest.bakingapp.App;
import com.example.guest.bakingapp.BakingApi;
import com.example.guest.bakingapp.base.BasePresenter;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.mvp.view.MainView;
import com.example.guest.bakingapp.utils.RxThreadManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
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
    private List<RecipeLocal> recipeLocals;

    @Inject
    public MainPresenter() {
        compositeDisposable = new CompositeDisposable();
        recipeLocals = new ArrayList<>(0);
    }
    public int getLocalDataSize() {
        return recipeLocals.size();
    }

    public void getLocalData() {
        compositeDisposable.add(Single.fromCallable(() -> App.dbInstance.reciepe().getRecipes())
                .compose(RxThreadManager.manageSingle())
                .subscribe(this::setFetchedFromDbList));
    }

    public void getRecieps() {
        compositeDisposable.add(apiService.getRecieps()
                .compose(RxThreadManager.manageObservable())
                .subscribe(recipes -> getView().onReciepsLoaded(recipes, recipeLocals), throwable -> Log.e(TAG, throwable.getMessage())));
    }

    public void unsibscibe() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    private void setFetchedFromDbList(List<RecipeLocal> list) {
        this.recipeLocals = list;
    }
}

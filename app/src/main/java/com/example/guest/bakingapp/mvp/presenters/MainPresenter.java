package com.example.guest.bakingapp.mvp.presenters;

import android.content.Context;
import android.util.Log;

import com.example.guest.bakingapp.App;
import com.example.guest.bakingapp.BakingApi;
import com.example.guest.bakingapp.base.BasePresenter;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.mvp.view.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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
//        compositeDisposable.add(apiService.getRecieps()
//                .compose(RxThreadManager.manageObservable())
//                .subscribe(recipes -> getView().onReciepsLoaded(recipes, recipeLocals), throwable -> Log.e(TAG, throwable.getMessage())));
    }

    public void getRecieps() {
        compositeDisposable.add(Observable.fromCallable(() -> App.dbInstance.reciepe().getRecipes())
                .observeOn(Schedulers.io())
                .flatMap(dbList -> {
                    recipeLocals = dbList;
                    return apiService.getRecieps();
                })
                .flatMapIterable(list -> list)
                .map(this::favoritesStatusCheck)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> getView().onReciepsLoaded(recipes),
                        throwable -> Log.e(TAG, throwable.getMessage())));
    }

    public void unsibscibe() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    private RecipeRemote favoritesStatusCheck(RecipeRemote recipeRemote) {
        for (RecipeLocal recipeLocal : recipeLocals) {
            if (recipeLocal.recipeId.compareTo(recipeRemote.getId()) == 0)
                recipeRemote.setFavorite(1);
        }
        return recipeRemote;
    }
}

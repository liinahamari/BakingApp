package com.example.guest.bakingapp.mvp.presenters;

import android.content.Context;
import android.util.Log;

import com.example.guest.bakingapp.BakingApi;
import com.example.guest.bakingapp.base.BasePresenter;
import com.example.guest.bakingapp.mvp.view.MainView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by l1maginaire on 4/26/18.
 */

public class MainPresenter extends BasePresenter<MainView> {
    private static final String TAG = MainPresenter.class.getSimpleName();
    private Disposable disposable;

    @Inject
    protected Context context;
    @Inject
    protected BakingApi apiService;

    @Inject
    public MainPresenter() {
        int i = 0;
    }

    public void getRecieps() {
        disposable = apiService.getRecieps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recieps -> getView().onReciepsLoaded(recieps), throwable -> Log.e(TAG, throwable.getMessage()));
    }

    public void unsibscibe(){
        if (disposable!=null)
            disposable.dispose();
    }
}

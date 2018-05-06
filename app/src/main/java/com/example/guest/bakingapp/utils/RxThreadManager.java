package com.example.guest.bakingapp.utils;

import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by l1maginaire on 5/5/18.
 */

public class RxThreadManager {
    public static <T>SingleTransformer<T, T> manageSingle(){
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T>ObservableTransformer<T, T> manageObservable(){
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

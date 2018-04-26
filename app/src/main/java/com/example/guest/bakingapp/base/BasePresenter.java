package com.example.guest.bakingapp.base;

/**
 * Created by l1maginaire on 4/14/18.
 */

import javax.inject.Inject;

public class BasePresenter<V extends BaseView> {

    @Inject
    protected V view;

    protected V getView() {
        return view;
    }
}
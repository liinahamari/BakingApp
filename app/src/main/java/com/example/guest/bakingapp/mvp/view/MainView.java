package com.example.guest.bakingapp.mvp.view;

import com.example.guest.bakingapp.base.BaseView;
import com.example.guest.bakingapp.mvp.model.Recipe;

import java.util.List;

/**
 * Created by l1maginaire on 4/26/18.
 */

public interface MainView extends BaseView {
    void onReciepsLoaded(List<Recipe> recipes);
    void onClearItems();
}

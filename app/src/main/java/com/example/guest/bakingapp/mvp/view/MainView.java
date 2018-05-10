package com.example.guest.bakingapp.mvp.view;

import com.example.guest.bakingapp.base.BaseView;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;

import java.util.List;

/**
 * Created by l1maginaire on 4/26/18.
 */

public interface MainView extends BaseView {
    void onRecipesLoaded(List<RecipeRemote> recipeRemotes);
    void onClearItems();
}

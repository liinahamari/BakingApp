package com.example.guest.bakingapp.data;

import com.example.guest.bakingapp.data.remote.IngredientRemote;
import com.example.guest.bakingapp.data.remote.RecipeRemote;
import com.example.guest.bakingapp.data.remote.StepRemote;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by l1maginaire on 5/4/18.
 */

public interface RecipeDataSource {
    Observable<List<RecipeRemote>> getRecipes();
    Observable<List<IngredientRemote>> getRecipeIngredients(int recipeId);
    Observable<List<StepRemote>> getRecipeSteps(int recipeId);
    void saveRecipes(List<RecipeRemote> recipeRemotes);
}

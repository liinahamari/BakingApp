package com.example.guest.bakingapp.data;

import com.example.guest.bakingapp.mvp.model.Ingredient;
import com.example.guest.bakingapp.mvp.model.Recipe;
import com.example.guest.bakingapp.mvp.model.Step;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by l1maginaire on 5/4/18.
 */

public interface RecipeDataSource {
    Observable<List<Recipe>> getRecipes();
    Observable<List<Ingredient>> getRecipeIngredients(int recipeId);
    Observable<List<Step>> getRecipeSteps(int recipeId);
    void saveRecipes(List<Recipe> recipes);
}

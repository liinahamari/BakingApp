package com.example.guest.bakingapp.data;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by l1maginaire on 5/6/18.
 */

public interface RecipeDataSource {

    Observable<List<Recipe>> getRecipes();

    Observable<List<Ingredient>> getRecipeIngredients(int recipeId);

    Observable<List<Ingredient>> getRecipeIngredients(String recipeName);

    Observable<List<Step>> getRecipeSteps(int recipeId);

    void saveRecipes(List<Recipe> recipes);
}

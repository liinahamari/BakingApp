/*
package com.example.guest.bakingapp.data;

*/
/**
 * Created by l1maginaire on 5/4/18.
 *//*


import android.content.Context;

import com.example.guest.bakingapp.mvp.model.Ingredient;
import com.example.guest.bakingapp.mvp.model.Recipe;
import com.example.guest.bakingapp.mvp.model.Step;
import com.example.guest.bakingapp.utils.DbOperations;

import io.reactivex.Observable;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RecipeLocalDataSource implements RecipeDataSource {

    private Context context;

    @Inject
    public RecipeLocalDataSource() {}

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return Observable.just(DbOperations.recipesFromCursor(DbOperations.getAll(context)));
    }

    @Override
    public Observable<List<Ingredient>> getRecipeIngredients(int recipeId) {
        return Observable.just(DbOperations.ingredientsFromCursor(DbOperations.getAll(context)));
    }

    @Override
    public Observable<List<Step>> getRecipeSteps(int recipeId) {
        return Observable.just(DbOperations.recipesFromCursor(DbOperations.getAll(context)));
    }

    @Override
    public void saveRecipes(List<Recipe> recipes) {
        try {
            deleteAllRecipes();
            for (Recipe recipe : recipes) {
                int id = recipe.getId();
                for (Ingredient ingredient : recipe.ingredients()) {
                    databaseHelper.insert(IngredientEntry.TABLE_NAME,
                            DbUtils.ingredientToContentValues(ingredient, id));
                }
                for (Step step : recipe.getSteps()) {
                    databaseHelper.insert(StepEntry.TABLE_NAME,
                            DbUtils.stepToContentValues(step, id));
                }
                databaseHelper.insert(RecipeEntry.TABLE_NAME,
                        DbUtils.recipeToContentValues(recipe));
            }
        } finally {
            transaction.end();
        }
    }

    private void deleteAllRecipes() {
        databaseHelper.delete(RecipeEntry.TABLE_NAME, null);
        databaseHelper.delete(StepEntry.TABLE_NAME, null);
        databaseHelper.delete(IngredientEntry.TABLE_NAME, null);
    }
}
*/

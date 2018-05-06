/*
package com.example.guest.bakingapp.data;

*/
/**
 * Created by l1maginaire on 5/4/18.
 *//*


import android.content.Context;

import com.example.guest.bakingapp.mvp.model.IngredientLocal;
import com.example.guest.bakingapp.mvp.model.RecipeLocal;
import com.example.guest.bakingapp.mvp.model.StepLocal;
import com.example.guest.bakingapp.utils.LocalDataSource;

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
    public Observable<List<RecipeLocal>> getRecipes() {
        return Observable.just(LocalDataSource.recipesFromCursor(LocalDataSource.getAll(context)));
    }

    @Override
    public Observable<List<IngredientLocal>> getRecipeIngredients(int recipeId) {
        return Observable.just(LocalDataSource.ingredientsFromCursor(LocalDataSource.getAll(context)));
    }

    @Override
    public Observable<List<StepLocal>> getRecipeSteps(int recipeId) {
        return Observable.just(LocalDataSource.recipesFromCursor(LocalDataSource.getAll(context)));
    }

    @Override
    public void saveRecipes(List<RecipeLocal> recipes) {
        try {
            deleteAllRecipes();
            for (RecipeLocal recipe : recipes) {
                int id = recipe.getId();
                for (IngredientLocal ingredient : recipe.ingredients()) {
                    databaseHelper.insert(IngredientEntry.RECIPE_TABLE_NAME,
                            DbUtils.ingredientToContentValues(ingredient, id));
                }
                for (StepLocal step : recipe.getStepRemotes()) {
                    databaseHelper.insert(StepEntry.RECIPE_TABLE_NAME,
                            DbUtils.stepToContentValues(step, id));
                }
                databaseHelper.insert(RecipeEntry.RECIPE_TABLE_NAME,
                        DbUtils.recipeToContentValues(recipe));
            }
        } finally {
            transaction.end();
        }
    }

    private void deleteAllRecipes() {
        databaseHelper.delete(RecipeEntry.RECIPE_TABLE_NAME, null);
        databaseHelper.delete(StepEntry.RECIPE_TABLE_NAME, null);
        databaseHelper.delete(IngredientEntry.RECIPE_TABLE_NAME, null);
    }
}
*/

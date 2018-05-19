/*
package com.example.guest.bakingapp.widget;

import com.sedsoftware.bakingapp.data.model.Ingredient;
import com.sedsoftware.bakingapp.data.source.RecipeRepository;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import java.util.Set;


public class WidgetDataHelper {
    @Inject
    public WidgetDataHelper() {
    }

    void deleteRecipeFromPrefs(int widgetId) {
        recipeRepository.getPreferencesHelper().deleteRecipeName(widgetId);
    }

    void saveRecipeNameToPrefs(int appWidgetId, String name) {
        recipeRepository.getPreferencesHelper().saveChosenRecipeName(appWidgetId, name);
    }

    String getRecipeNameFromPrefs(int appWidgetId) {
        return recipeRepository.getPreferencesHelper().getChosenRecipeName(appWidgetId);
    }

    Observable<List<Ingredient>> getIngredientsList(String recipeName) {
        return recipeRepository.getRecipeIngredients(recipeName);
    }
}
*/

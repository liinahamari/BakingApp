package com.example.guest.bakingapp.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.data.local.pojo.IngredientLocal;
import com.example.guest.bakingapp.data.remote.pojo.IngredientRemote;
import com.example.guest.bakingapp.utils.RxThreadManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

import static com.example.guest.bakingapp.data.local.Provider.URI_INGREDIENTS;


public class WidgetProvider extends AppWidgetProvider {

    @SuppressLint("CheckResult")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeId = preferences.getString(WidgetConfigActivity.WIDGET_RECIPE_ID, null);
        List<IngredientRemote> ingredients = new ArrayList<>();
        if (recipeId != null)
            Single.fromCallable(() -> context.getContentResolver().query(URI_INGREDIENTS, null, null,
                    new String[]{recipeId}, null))
                    .compose(RxThreadManager.manageSingle())
                    .doOnError(throwable -> Log.e("TAG", "Something is wrong with App-class"))
                    .subscribe(cursor -> {
                        if (cursor.getCount() > 0) {
                            cursor.moveToPosition(-1);
                            while (cursor.moveToNext()) {
                                IngredientRemote ingredientRemote = new IngredientRemote();
                                ingredientRemote.setIngredient(cursor.getString(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_INGREDIENT)));
                                ingredientRemote.setMeasure(cursor.getString(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_MEASURE)));
                                ingredientRemote.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_QUANTITITY)));
                                ingredients.add(ingredientRemote);
                            }
                        }
                        for (int appWidgetId : appWidgetIds) {
                            updateAppWidgetContent(context, appWidgetManager, appWidgetId, "LOL", ingredients);
                        }
                    });
    }

    public static void updateAppWidgetContent(Context context, AppWidgetManager appWidgetManager,
                                              int appWidgetId, String recipeName, List<IngredientRemote> ingredients) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list);
        views.setTextViewText(R.id.widget_recipe_name, recipeName);
        views.removeAllViews(R.id.widget_ingredients_container);

        for (IngredientRemote ingredient : ingredients) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                    R.layout.widget_ingredients_list_item);

            String line = ingredient.getIngredient() + " " + ingredient.getQuantity() + " " + ingredient.getMeasure();
            ingredientView.setTextViewText(R.id.widget_ingredient_name, line);
            views.addView(R.id.widget_ingredients_container, ingredientView);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}

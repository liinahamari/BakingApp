package com.example.guest.bakingapp.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.guest.bakingapp.mvp.model.Ingredient;
import com.example.guest.bakingapp.mvp.model.Recipe;
import com.example.guest.bakingapp.mvp.model.Step;

import java.util.ArrayList;
import java.util.List;

import static com.example.guest.bakingapp.db.Provider.AUTHORITY;
import static com.example.guest.bakingapp.db.Provider.URI_INGREDIENTS;
import static com.example.guest.bakingapp.db.Provider.URI_RECIPE;
import static com.example.guest.bakingapp.db.Provider.URI_STEP;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_FAVORITE;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_ID;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_IMAGE;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_NAME;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_RECIPE_ID;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_SERVINGS;
import static com.example.guest.bakingapp.db.model.Recipe.RECIPE_TABLE_NAME;

/**
 * Created by l1maginaire on 5/2/18.
 */

public class ContentProviderOperations {
    public static List<Recipe> getAll(Context context) {
        Cursor cursor = context.getContentResolver().query(URI_INGREDIENTS, null, null,
                new String[]{String.valueOf(1)}, null);
        return recipesFromCursor(cursor);
    }

    public static int delete(int id, Context context) {
        int i = context.getContentResolver().delete(getUriItem(RECIPE_TABLE_NAME, id), null, null);
        return i;
    }

    public static Uri insert(Recipe recipe, Context context) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_ID, recipe.getId());
        values.put(COLUMN_IMAGE, recipe.getImage());
        values.put(COLUMN_NAME, recipe.getName());
        values.put(COLUMN_SERVINGS, recipe.getServings());
        values.put(COLUMN_FAVORITE, recipe.isFavorite());
        insertIngredients(recipe.getIngredients(), context, recipe.getId());
        insertSteps(recipe.getSteps(), context, recipe.getId());
        return context.getContentResolver().insert(URI_RECIPE, values);
    }

    public static int insertIngredients(List<Ingredient> ingredients, Context context, int recipeId) {
        ContentValues[] contentValues = new ContentValues[ingredients.size()];
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            ContentValues values = new ContentValues();
            values.put(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_RECIPE_ID, recipeId);
            values.put(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_QUANTITITY, ingredient.getQuantity());
            values.put(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_MEASURE, ingredient.getMeasure());
            values.put(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_INGREDIENT, ingredient.getIngredient());
            contentValues[i] = values;
        }
        int i = context.getContentResolver().bulkInsert(URI_INGREDIENTS, contentValues);
        return i;
    }

    public static int insertSteps(List<Step> steps, Context context, int recipeId) {
        ContentValues[] contentValues = new ContentValues[steps.size()];
        for (int i = 0; i < steps.size(); i++) {
            Step step = steps.get(i);
            ContentValues values = new ContentValues();
            values.put(com.example.guest.bakingapp.db.model.Step.COLUMN_RECIPE_ID, recipeId);
            values.put(com.example.guest.bakingapp.db.model.Step.COLUMN_DESCRIPTION, step.getDescription());
            values.put(com.example.guest.bakingapp.db.model.Step.COLUMN_S_DESCRIPTION, step.getShortDescription());
            values.put(com.example.guest.bakingapp.db.model.Step.COLUMN_VIDEO_URL, step.getVideoURL());
            values.put(com.example.guest.bakingapp.db.model.Step.COLUMN_THUMB_URL, step.getThumbnailURL());
            contentValues[i] = values;
        }
        int i = context.getContentResolver().bulkInsert(URI_STEP, contentValues);
        return i;
    }

    public static List<Recipe> recipesFromCursor(@NonNull Cursor cursor) {
        List<Recipe> recipeList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                /*Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                recipe.setServings(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVINGS)));
                recipe.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                recipe.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)));
                recipeList.add(recipe);*/

                Ingredient recipe = new Ingredient();
                recipe.setIngredient(cursor.getString(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_QUANTITITY)));
                recipe.setMeasure(cursor.getString(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_MEASURE)));
                recipe.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(com.example.guest.bakingapp.db.model.Ingredient.COLUMN_QUANTITITY)));
                int i = 0;
            }
        }
        return recipeList;
    }

    private static Uri getUriItem(String table, int id) {
        return Uri.parse("content://" + AUTHORITY + "/" + table + "/" + id);
    }

    public static Integer isFavorite(Context context, int id) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = null;
        if (id != 0) {
            c = contentResolver.query(getUriItem(RECIPE_TABLE_NAME, id), null, null, null, null);
        }
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0 && c.getInt(c.getColumnIndex(COLUMN_ID)) == id) {
                c.close();
                return 1;
            }
            c.close();
        }
        return 0;
    }
}

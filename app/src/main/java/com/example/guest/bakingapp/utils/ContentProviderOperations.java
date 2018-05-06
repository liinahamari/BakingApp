package com.example.guest.bakingapp.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.guest.bakingapp.mvp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.example.guest.bakingapp.db.Provider.AUTHORITY;
import static com.example.guest.bakingapp.db.Provider.URI_RECIPE;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_FAVORITE;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_ID;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_IMAGE;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_NAME;
import static com.example.guest.bakingapp.db.model.Recipe.COLUMN_SERVINGS;

/**
 * Created by l1maginaire on 5/2/18.
 */

public class ContentProviderOperations {
    public static List<Recipe> getAll(Context context) {
        Cursor cursor = context.getContentResolver().query(URI_RECIPE, null, null, null, null);
        return recipesFromCursor(cursor);
    }

    public static int delete(int id, Context context) {//todo тут bulk
        int i = context.getContentResolver().delete(getUriItem(id), null,null);
        return i;
    }

    public static Uri insert(Recipe recipe, Context context) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, recipe.getId());
        values.put(COLUMN_IMAGE, recipe.getImage());
        values.put(COLUMN_NAME, recipe.getName());
        values.put(COLUMN_SERVINGS, recipe.getServings());
        values.put(COLUMN_FAVORITE, recipe.isFavorite());
        return context.getContentResolver().insert(URI_RECIPE, values);
    }

    public static int bulkInsert(List<Recipe> recipes, Context context) {
        ContentValues[] contentValues = new ContentValues[recipes.size()];
        for (int i = 0; i<recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, recipe.getId());
            values.put(COLUMN_IMAGE, recipe.getImage());
            values.put(COLUMN_NAME, recipe.getName());
            values.put(COLUMN_SERVINGS, recipe.getServings());
            values.put(COLUMN_FAVORITE, recipe.isFavorite());
            contentValues[i] = values;
        }
        int i =context.getContentResolver().bulkInsert(URI_RECIPE, contentValues);
        return i;
    }

    public static List<Recipe> recipesFromCursor(@NonNull Cursor cursor) {
        List<Recipe> recipeList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                recipe.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                recipe.setServings(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVINGS)));
                recipe.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                recipe.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAVORITE)));
                recipeList.add(recipe);
            }
        }
        return recipeList;
    }

    private static Uri getUriItem(int id) {
        return Uri.parse("content://" + AUTHORITY + "/" + com.example.guest.bakingapp.db.model.Recipe.RECIPE_TABLE_NAME + "/" + id);
    }

    public static Integer isFavorite(Context context, int id) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = null;
        if (id != 0) {
            c = contentResolver.query(getUriItem(id),null,null, null,null);
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

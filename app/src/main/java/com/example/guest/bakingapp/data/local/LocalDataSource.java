package com.example.guest.bakingapp.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.guest.bakingapp.data.local.pojo.IngredientLocal;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.data.local.pojo.StepLocal;
import com.example.guest.bakingapp.data.remote.pojo.IngredientRemote;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;

import java.util.ArrayList;
import java.util.List;

import static com.example.guest.bakingapp.data.local.Provider.URI_INGREDIENTS;
import static com.example.guest.bakingapp.data.local.Provider.URI_RECIPE;
import static com.example.guest.bakingapp.data.local.Provider.URI_STEP;
import static com.example.guest.bakingapp.data.local.pojo.RecipeLocal.COLUMN_FAVORITE;
import static com.example.guest.bakingapp.data.local.pojo.RecipeLocal.COLUMN_ID;
import static com.example.guest.bakingapp.data.local.pojo.RecipeLocal.COLUMN_IMAGE;
import static com.example.guest.bakingapp.data.local.pojo.RecipeLocal.COLUMN_NAME;
import static com.example.guest.bakingapp.data.local.pojo.RecipeLocal.COLUMN_RECIPE_ID;
import static com.example.guest.bakingapp.data.local.pojo.RecipeLocal.COLUMN_SERVINGS;

/**
 * Created by l1maginaire on 5/2/18.
 */

public class LocalDataSource {
    private static final String TAG = LocalDataSource.class.getSimpleName();

    public static List<RecipeRemote> getAll(Context context) {
        Cursor cursor = context.getContentResolver().query(URI_INGREDIENTS, null, null,
                new String[]{String.valueOf(1)}, null);
        return recipesFromCursor(cursor);
    }

    /**
     * @return successful insert returns 3
     */

    public static int delete(int id, Context context) {
        int rowsDeleted = 0;
        rowsDeleted += (context.getContentResolver().delete(URI_STEP, null, new String[]{String.valueOf(id)})) > 0 ? 1 : 0;
        rowsDeleted += (context.getContentResolver().delete(URI_RECIPE, null, new String[]{String.valueOf(id)})) > 0 ? 1 : 0;
        rowsDeleted += (context.getContentResolver().delete(URI_INGREDIENTS, null, new String[]{String.valueOf(id)})) > 0 ? 1 : 0;
        Log.i(TAG, "Rows deleted: " + rowsDeleted);
        return 0;
    }

    /**
     * @return successful insert returns 3
     */

    public static int insert(RecipeRemote recipeRemote, Context context) {
        int i = 0;
        i += (insertRecipes(recipeRemote, context));
        i += insertIngredients(recipeRemote.getIngredientRemotes(), context, recipeRemote.getId());
        i += insertSteps(recipeRemote.getStepRemotes(), context, recipeRemote.getId());
        Log.d(TAG, "Inserted "+String.valueOf(i) + " of (3)");
        return 1;
    }

    private static int insertRecipes(RecipeRemote recipeRemote, Context context) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_ID, recipeRemote.getId());
        values.put(COLUMN_IMAGE, recipeRemote.getImage());
        values.put(COLUMN_NAME, recipeRemote.getName());
        values.put(COLUMN_SERVINGS, recipeRemote.getServings());
        return (context.getContentResolver().insert(URI_RECIPE, values) != null) ? 1 : 0;
    }

    public static int insertIngredients(List<IngredientRemote> ingredientRemotes, Context context, int recipeId) {
        ContentValues[] contentValues = new ContentValues[ingredientRemotes.size()];
        for (int i = 0; i < ingredientRemotes.size(); i++) {
            IngredientRemote ingredientRemote = ingredientRemotes.get(i);
            ContentValues values = new ContentValues();
            values.put(IngredientLocal.COLUMN_RECIPE_ID, recipeId);
            values.put(IngredientLocal.COLUMN_QUANTITITY, ingredientRemote.getQuantity());
            values.put(IngredientLocal.COLUMN_MEASURE, ingredientRemote.getMeasure());
            values.put(IngredientLocal.COLUMN_INGREDIENT, ingredientRemote.getIngredient());
            contentValues[i] = values;
        }
        int i = context.getContentResolver().bulkInsert(URI_INGREDIENTS, contentValues);
        return (i > 0) ? 1 : 0;
    }

    public static int insertSteps(List<StepRemote> stepRemotes, Context context, int recipeId) {
        ContentValues[] contentValues = new ContentValues[stepRemotes.size()];
        for (int i = 0; i < stepRemotes.size(); i++) {
            StepRemote stepRemote = stepRemotes.get(i);
            ContentValues values = new ContentValues();
            values.put(StepLocal.COLUMN_RECIPE_ID, recipeId);
            values.put(StepLocal.COLUMN_DESCRIPTION, stepRemote.getDescription());
            values.put(StepLocal.COLUMN_S_DESCRIPTION, stepRemote.getShortDescription());
            values.put(StepLocal.COLUMN_VIDEO_URL, stepRemote.getVideoURL());
            values.put(StepLocal.COLUMN_THUMB_URL, stepRemote.getThumbnailURL());
            contentValues[i] = values;
        }
        int i = context.getContentResolver().bulkInsert(URI_STEP, contentValues);
        return (i > 0) ? 1 : 0;
    }

    public static List<RecipeRemote> recipesFromCursor(@NonNull Cursor cursor) {
        List<RecipeRemote> recipeRemoteList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                RecipeRemote recipeRemote = new RecipeRemote();
                recipeRemote.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                recipeRemote.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                recipeRemote.setServings(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SERVINGS)));
                recipeRemote.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                recipeRemoteList.add(recipeRemote);
            }
        }
        return recipeRemoteList;
    }

    public static boolean isFavorite(Context context, long id) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = contentResolver.query(URI_RECIPE, null, null, new String[]{String.valueOf(id)}, null);
        c.moveToPosition(-1);
        while (c.moveToNext()) {
            int i = (c.getInt(c.getColumnIndex(RecipeLocal.COLUMN_RECIPE_ID)));
            if (i==id)
                return true;
            else return false;
        }
        return false;
    }
}

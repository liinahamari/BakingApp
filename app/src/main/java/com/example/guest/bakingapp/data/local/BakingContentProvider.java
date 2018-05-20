package com.example.guest.bakingapp.data.local;

/**
 * Created by l1maginaire on 5/5/18.
 */

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.guest.bakingapp.App;
import com.example.guest.bakingapp.data.local.pojo.IngredientLocal;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.data.local.pojo.StepLocal;

public class BakingContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.guest.bakingapp.db";

    public static final Uri URI_RECIPE = Uri.parse("content://" + AUTHORITY + "/" + RecipeLocal.RECIPE_TABLE_NAME);
    public static final Uri URI_INGREDIENTS = Uri.parse("content://" + AUTHORITY + "/" + IngredientLocal.INGREDIENTS_TABLE_NAME);
    public static final Uri URI_STEP = Uri.parse("content://" + AUTHORITY + "/" + StepLocal.STEPS_TABLE_NAME);

    private static final int RECIPE_DIR = 1001;
    private static final int RECIPE_ITEM = 1002;
    private static final int INGREDIENT_DIR = 1101;
    private static final int INGREDIENT_ITEM = 1102;
    private static final int STEP_DIR = 1201;
    private static final int STEP_ITEM = 1202;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
        sURIMatcher.addURI(authority, RecipeLocal.RECIPE_TABLE_NAME, RECIPE_DIR);
        sURIMatcher.addURI(authority, RecipeLocal.RECIPE_TABLE_NAME + "/*", RECIPE_ITEM);
        sURIMatcher.addURI(authority, IngredientLocal.INGREDIENTS_TABLE_NAME, INGREDIENT_DIR);
        sURIMatcher.addURI(authority, IngredientLocal.INGREDIENTS_TABLE_NAME + "/*", INGREDIENT_ITEM);
        sURIMatcher.addURI(authority, StepLocal.STEPS_TABLE_NAME, STEP_DIR);
        sURIMatcher.addURI(authority, StepLocal.STEPS_TABLE_NAME + "/*", STEP_ITEM);
        return sURIMatcher;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = uriMatcher.match(uri);
        if (code == RECIPE_DIR || code == RECIPE_ITEM || code == INGREDIENT_DIR || code == INGREDIENT_ITEM
                || code == STEP_DIR || code == STEP_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            RecipeDao reciepe = App.dbInstance.reciepe();//todo ask mentor's opinion
            Cursor cursor = null;
            if (code == RECIPE_DIR) {
                cursor = reciepe.getRecipe(Long.valueOf(selectionArgs[0]));
            } else if (code == INGREDIENT_DIR) {
                cursor = reciepe.getIngredients(Long.valueOf(selectionArgs[0]));
            } else if (code == STEP_DIR) {
                cursor = reciepe.getSteps(Integer.valueOf(selectionArgs[0]));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);
        switch (match) {
            case RECIPE_DIR:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + RecipeLocal.RECIPE_TABLE_NAME;
            case RECIPE_ITEM:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + RecipeLocal.RECIPE_TABLE_NAME;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case RECIPE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long insertRecipe = App.dbInstance.reciepe().insertRecipe(RecipeLocal.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, insertRecipe);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final Context context = getContext();
        if (context == null) {
            return 0;
        }
        switch (uriMatcher.match(uri)) {
            case RECIPE_DIR:
                App.dbInstance.reciepe().deleteRecipes(Integer.valueOf(selectionArgs[0]));
                return 1;
            case INGREDIENT_DIR:
                App.dbInstance.reciepe().deleteIngredients(Integer.valueOf(selectionArgs[0]));
                return 1;
            case STEP_DIR:
                App.dbInstance.reciepe().deleteSteps(Integer.valueOf(selectionArgs[0]));
                return 1;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
        final RecipeDb database = App.dbInstance;
        switch (uriMatcher.match(uri)) {
            case RECIPE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final RecipeLocal[] recipeLocals = new RecipeLocal[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    recipeLocals[i] = RecipeLocal.fromContentValues(valuesArray[i]);
                }
                return database.reciepe().insertRecipes(recipeLocals).length;
            case INGREDIENT_DIR:
                return database.reciepe().insertIngredients(IngredientLocal.fromContentValues(valuesArray)).length;
            case STEP_DIR:
                return database.reciepe().insertSteps(StepLocal.fromContentValues(valuesArray)).length;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
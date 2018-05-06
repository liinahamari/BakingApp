package com.example.guest.bakingapp.db;

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
import com.example.guest.bakingapp.db.model.Recipe;

public class Provider extends ContentProvider {
    public static final String AUTHORITY = "com.example.guest.bakingapp.db";

    public static final Uri URI_RECIPE = Uri.parse(
            "content://" + AUTHORITY + "/" + Recipe.RECIPE_TABLE_NAME);

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
        sURIMatcher.addURI(authority, Recipe.RECIPE_TABLE_NAME, RECIPE_DIR);
        sURIMatcher.addURI(authority, Recipe.RECIPE_TABLE_NAME + "/*", RECIPE_ITEM);
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
        if (code == RECIPE_DIR || code == RECIPE_ITEM) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            RecipeDao reciepe = App.dbInstance.reciepe();
            final Cursor cursor;
            if (code == RECIPE_DIR) {
                cursor = reciepe.getRecipes();
            } else {
                cursor = reciepe.getRecipe(ContentUris.parseId(uri));
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
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + Recipe.RECIPE_TABLE_NAME;
            case RECIPE_ITEM:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + Recipe.RECIPE_TABLE_NAME;
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
                final long id = App.dbInstance.reciepe()
                        .insertRecipe(Recipe.fromContentValues(values));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case RECIPE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case RECIPE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                App.dbInstance.reciepe().deleteRecipes();
                context.getContentResolver().notifyChange(uri, null);
                return 1;
            case RECIPE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID " + uri);
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
        switch (uriMatcher.match(uri)) {
            case RECIPE_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final RecipeDb database = App.dbInstance;
                final Recipe[] recipes = new Recipe[valuesArray.length];
                for (int i = 0; i < valuesArray.length; i++) {
                    recipes[i] = Recipe.fromContentValues(valuesArray[i]);
                }
                return database.reciepe().insertRecipes(recipes).length;
            case RECIPE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
package com.example.guest.bakingapp.db;

/**
 * Created by l1maginaire on 5/5/18.
 */

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.guest.bakingapp.App;

import java.net.URI;
import java.util.ArrayList;

public class Provider extends ContentProvider {
    public static final String AUTHORITY = "com.example.guest.bakingapp.db";

    public static final Uri URI_RECIPE = Uri.parse(
            "content://" + AUTHORITY + "/" + Recipe.TABLE_NAME);

    private static final int RECIPE_DIR = 1111;
    private static final int RECIPE_ITEM = 2222;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;
        sURIMatcher.addURI(authority, Recipe.TABLE_NAME, RECIPE_DIR);
        sURIMatcher.addURI(authority, Recipe.TABLE_NAME + "/*", RECIPE_ITEM);
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
                cursor = reciepe.selectAll();
            } else {
                cursor = reciepe.selectById(ContentUris.parseId(uri));
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
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + Recipe.TABLE_NAME;
            case RECIPE_ITEM:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + Recipe.TABLE_NAME;
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
                        .insert(Recipe.fromContentValues(values));
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
                throw new IllegalArgumentException("Invalid URI, cannot update without ID " + uri);
            case RECIPE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = App.dbInstance.reciepe().deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case RECIPE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case RECIPE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final Recipe recipe = Recipe.fromContentValues(values);
                recipe.id = ContentUris.parseId(uri);
                final int count = App.dbInstance.reciepe().update(recipe);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(
            @NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final Context context = getContext();
        if (context == null) {
            return new ContentProviderResult[0];
        }
        final RecipeDb database = App.dbInstance;
        database.beginTransaction();
        try {
            final ContentProviderResult[] result = super.applyBatch(operations);
            database.setTransactionSuccessful();
            return result;
        } finally {
            database.endTransaction();
        }
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
                return database.reciepe().insertAll(recipes).length;
            case RECIPE_ITEM:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

}
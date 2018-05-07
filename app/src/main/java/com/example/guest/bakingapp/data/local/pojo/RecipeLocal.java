package com.example.guest.bakingapp.data.local.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Created by l1maginaire on 5/5/18.
 */
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

@Entity(tableName = RecipeLocal.RECIPE_TABLE_NAME)
public class RecipeLocal {
    public static final String RECIPE_TABLE_NAME = "recipe";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SERVINGS = "servings";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_FAVORITE = "favorite";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    public Integer id;
    @ColumnInfo(name = COLUMN_RECIPE_ID)
    public Integer recipeId;
    @ColumnInfo(name = COLUMN_NAME)
    public String name;
    @ColumnInfo(name = COLUMN_SERVINGS)
    public Integer servings;
    @ColumnInfo(name = COLUMN_IMAGE)
    public String image;
    @ColumnInfo(name = COLUMN_FAVORITE)
    public Integer favorite;

    public static RecipeLocal fromContentValues(ContentValues values) {
        final RecipeLocal recipeLocal = new RecipeLocal();
        if (values.containsKey(COLUMN_ID)) {
            recipeLocal.id = values.getAsInteger(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_RECIPE_ID)) {
            recipeLocal.recipeId = values.getAsInteger(COLUMN_RECIPE_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            recipeLocal.name = values.getAsString(COLUMN_NAME);
        }
        if (values.containsKey(COLUMN_SERVINGS)) {
            recipeLocal.servings = values.getAsInteger(COLUMN_SERVINGS);
        }
        if (values.containsKey(COLUMN_IMAGE)) {
            recipeLocal.image = values.getAsString(COLUMN_IMAGE);
        }
        if (values.containsKey(COLUMN_FAVORITE)) {
            recipeLocal.image = values.getAsString(COLUMN_IMAGE);
        }
        return recipeLocal;
    }
}

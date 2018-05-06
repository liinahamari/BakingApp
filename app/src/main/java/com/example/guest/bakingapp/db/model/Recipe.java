package com.example.guest.bakingapp.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

/**
 * Created by l1maginaire on 5/5/18.
 */
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

@Entity(tableName = Recipe.RECIPE_TABLE_NAME)
public class Recipe{
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

    public static Recipe fromContentValues(ContentValues values) {
        final Recipe recipe = new Recipe();
        if (values.containsKey(COLUMN_ID)) {
            recipe.id = values.getAsInteger(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_RECIPE_ID)) {
            recipe.recipeId = values.getAsInteger(COLUMN_RECIPE_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            recipe.name = values.getAsString(COLUMN_NAME);
        }
        if (values.containsKey(COLUMN_SERVINGS)) {
            recipe.servings = values.getAsInteger(COLUMN_SERVINGS);
        }
        if (values.containsKey(COLUMN_IMAGE)) {
            recipe.image = values.getAsString(COLUMN_IMAGE);
        }
        if (values.containsKey(COLUMN_FAVORITE)) {
            recipe.image = values.getAsString(COLUMN_IMAGE);
        }
        return recipe;
    }
}

package com.example.guest.bakingapp.db;

import android.arch.persistence.room.Entity;

/**
 * Created by l1maginaire on 5/5/18.
 */
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

@Entity(tableName = Recipe.TABLE_NAME)
public class Recipe{
    public static final String TABLE_NAME = "recipe";
    public static final String _ID = BaseColumns._ID;
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SERVINGS = "servings";
    public static final String COLUMN_IMAGE = "image";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = _ID)
    public long _id;
    @ColumnInfo(name = COLUMN_ID)
    public Long id;
    @ColumnInfo(name = COLUMN_NAME)
    public String name;
    @ColumnInfo(name = COLUMN_SERVINGS)
    public Integer servings;
    @ColumnInfo(name = COLUMN_IMAGE)
    public String image;

    public static Recipe fromContentValues(ContentValues values) {
        final Recipe recipe = new Recipe();
        if (values.containsKey(_ID)) {
            recipe._id = values.getAsLong(_ID);
        }
        if (values.containsKey(COLUMN_ID)) {
            recipe.id = values.getAsLong(COLUMN_ID);
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
        return recipe;
    }
}

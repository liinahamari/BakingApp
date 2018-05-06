package com.example.guest.bakingapp.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

import static com.example.guest.bakingapp.db.model.Ingredient.INGREDIENTS_TABLE_NAME;

/**
 * Created by l1maginaire on 5/6/18.
 */

@Entity(tableName = INGREDIENTS_TABLE_NAME,
        foreignKeys = @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE))
public class Ingredient {
    public static final String INGREDIENTS_TABLE_NAME = "ingredients";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPE_ID = "r_id";
    public static final String COLUMN_QUANTITITY = "quantity";
    public static final String COLUMN_MEASURE = "measure";
    public static final String COLUMN_INGREDIENT = "ingredient";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private int id;
    @ColumnInfo(name = COLUMN_RECIPE_ID)
    private int recipeId;
    @ColumnInfo(name = COLUMN_QUANTITITY)
    private Double quantity;
    @ColumnInfo(name = COLUMN_MEASURE)
    private String measure;
    @ColumnInfo(name = COLUMN_INGREDIENT)
    private String ingredient;

    public static Ingredient[] fromContentValues(ContentValues[] values) {
        Ingredient[] ingredients = new Ingredient[values.length];
        for (int i = 0; i<values.length; i++) {
            ContentValues values1 = values[i];
            Ingredient ingredient = new Ingredient();
            if (values1.containsKey(COLUMN_ID)) {
                ingredient.id = values1.getAsInteger(COLUMN_ID);
            }
            if (values1.containsKey(COLUMN_RECIPE_ID)) {
                ingredient.recipeId = values1.getAsInteger(COLUMN_RECIPE_ID);
            }
            if (values1.containsKey(COLUMN_QUANTITITY)) {
                ingredient.quantity = values1.getAsDouble(COLUMN_QUANTITITY);
            }
            if (values1.containsKey(COLUMN_MEASURE)) {
                ingredient.measure = values1.getAsString(COLUMN_MEASURE);
            }
            if (values1.containsKey(COLUMN_INGREDIENT)) {
                ingredient.ingredient = values1.getAsString(COLUMN_INGREDIENT);
            }
            ingredients[i] = ingredient;
        }
        return ingredients;
    }
}
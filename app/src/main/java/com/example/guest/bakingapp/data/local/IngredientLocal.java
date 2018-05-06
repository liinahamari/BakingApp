package com.example.guest.bakingapp.data.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

import static com.example.guest.bakingapp.data.local.IngredientLocal.INGREDIENTS_TABLE_NAME;

/**
 * Created by l1maginaire on 5/6/18.
 */

@Entity(tableName = INGREDIENTS_TABLE_NAME)
public class IngredientLocal {
    public static final String INGREDIENTS_TABLE_NAME = "ingredients";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_QUANTITITY = "quantity";
    public static final String COLUMN_MEASURE = "measure";
    public static final String COLUMN_INGREDIENT = "ingredient";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    public int id;
    @ColumnInfo(name = COLUMN_RECIPE_ID)
    public int recipeId;
    @ColumnInfo(name = COLUMN_QUANTITITY)
    public Double quantity;
    @ColumnInfo(name = COLUMN_MEASURE)
    public String measure;
    @ColumnInfo(name = COLUMN_INGREDIENT)
    public String ingredient;

    public static IngredientLocal[] fromContentValues(ContentValues[] values) {
        IngredientLocal[] ingredientLocals = new IngredientLocal[values.length];
        for (int i = 0; i<values.length; i++) {
            ContentValues values1 = values[i];
            IngredientLocal ingredientLocal = new IngredientLocal();
            if (values1.containsKey(COLUMN_ID)) {
                ingredientLocal.id = values1.getAsInteger(COLUMN_ID);
            }
            if (values1.containsKey(COLUMN_RECIPE_ID)) {
                ingredientLocal.recipeId = values1.getAsInteger(COLUMN_RECIPE_ID);
            }
            if (values1.containsKey(COLUMN_QUANTITITY)) {
                ingredientLocal.quantity = values1.getAsDouble(COLUMN_QUANTITITY);
            }
            if (values1.containsKey(COLUMN_MEASURE)) {
                ingredientLocal.measure = values1.getAsString(COLUMN_MEASURE);
            }
            if (values1.containsKey(COLUMN_INGREDIENT)) {
                ingredientLocal.ingredient = values1.getAsString(COLUMN_INGREDIENT);
            }
            ingredientLocals[i] = ingredientLocal;
        }
        return ingredientLocals;
    }
}
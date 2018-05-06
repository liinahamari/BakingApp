package com.example.guest.bakingapp.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

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

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int recipeId;
    private Double quantity;
    private String measure;
    private String ingredient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

}
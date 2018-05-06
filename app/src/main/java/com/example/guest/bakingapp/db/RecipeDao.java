package com.example.guest.bakingapp.db;

/**
 * Created by l1maginaire on 5/5/18.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.guest.bakingapp.db.model.Ingredient;
import com.example.guest.bakingapp.db.model.Recipe;
import com.example.guest.bakingapp.db.model.Step;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertRecipes(Recipe[] recipes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRecipe(Recipe recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertIngredients(Ingredient[] ingredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertSteps(Step[] steps);

    @Query("SELECT * FROM " + Recipe.RECIPE_TABLE_NAME)
    List<Recipe> getRecipes();

    @Query("SELECT * FROM " + Recipe.RECIPE_TABLE_NAME + " WHERE recipe_id=:recipeId")
    Cursor getRecipe(long recipeId);

    @Query("SELECT * FROM " + Ingredient.INGREDIENTS_TABLE_NAME + " WHERE recipe_id=:recipeId")
    Cursor getIngredients(long recipeId);

    @Query("SELECT * FROM " + Step.STEPS_TABLE_NAME + " WHERE recipe_id=:recipeId")
    Cursor getSteps(long recipeId);

    @Query("DELETE FROM " + Recipe.RECIPE_TABLE_NAME + " WHERE recipe_id=:recipeId")
    void deleteRecipes(long recipeId);

    @Query("DELETE FROM " + Ingredient.INGREDIENTS_TABLE_NAME + " WHERE recipe_id=:recipeId")
    void deleteIngredients(long recipeId);

    @Query("DELETE FROM " + Step.STEPS_TABLE_NAME + " WHERE recipe_id=:recipeId")
    void deleteSteps(long recipeId);
}

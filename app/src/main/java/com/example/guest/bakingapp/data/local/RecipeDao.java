package com.example.guest.bakingapp.data.local;

/**
 * Created by l1maginaire on 5/5/18.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.example.guest.bakingapp.data.local.pojo.IngredientLocal;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.data.local.pojo.StepLocal;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertRecipes(RecipeLocal[] recipeLocals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRecipe(RecipeLocal recipeLocal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertIngredients(IngredientLocal[] ingredientLocals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertSteps(StepLocal[] stepLocals);

    @Query("SELECT * FROM " + RecipeLocal.RECIPE_TABLE_NAME)
    List<RecipeLocal> getRecipes();

    @Query("SELECT * FROM " + RecipeLocal.RECIPE_TABLE_NAME + " WHERE recipe_id=:recipeId")
    Cursor getRecipe(long recipeId);

    @Query("SELECT * FROM " + IngredientLocal.INGREDIENTS_TABLE_NAME + " WHERE recipe_id=:recipeId")
    Cursor getIngredients(long recipeId);

    @Query("SELECT * FROM " + StepLocal.STEPS_TABLE_NAME + " WHERE recipe_id=:recipeId")
    Cursor getSteps(long recipeId);

    @Query("DELETE FROM " + RecipeLocal.RECIPE_TABLE_NAME + " WHERE recipe_id=:recipeId")
    void deleteRecipes(long recipeId);

    @Query("DELETE FROM " + IngredientLocal.INGREDIENTS_TABLE_NAME + " WHERE recipe_id=:recipeId")
    void deleteIngredients(long recipeId);

    @Query("DELETE FROM " + StepLocal.STEPS_TABLE_NAME + " WHERE recipe_id=:recipeId")
    void deleteSteps(long recipeId);
}

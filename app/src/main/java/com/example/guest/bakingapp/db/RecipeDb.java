package com.example.guest.bakingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.guest.bakingapp.db.model.Ingredient;
import com.example.guest.bakingapp.db.model.Recipe;
import com.example.guest.bakingapp.db.model.Step;

/**
 * Created by l1maginaire on 5/5/18.
 */

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1)
public abstract class RecipeDb extends RoomDatabase {

    @SuppressWarnings("WeakerAccess")
    public abstract RecipeDao reciepe();
}

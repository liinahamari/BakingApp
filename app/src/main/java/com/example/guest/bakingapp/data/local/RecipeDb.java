package com.example.guest.bakingapp.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.guest.bakingapp.data.local.pojo.IngredientLocal;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.data.local.pojo.StepLocal;

/**
 * Created by l1maginaire on 5/5/18.
 */

@Database(entities = {RecipeLocal.class, IngredientLocal.class, StepLocal.class}, version = 1)
public abstract class RecipeDb extends RoomDatabase {

    @SuppressWarnings("WeakerAccess")
    public abstract RecipeDao reciepe();
}

package com.example.guest.bakingapp.db;

/**
 * Created by l1maginaire on 5/5/18.
 */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

@Dao
public interface RecipeDao {
    @Query("SELECT COUNT(*) FROM " + Recipe.TABLE_NAME)
    int count();

    @Insert
    long insert(Recipe recipe);

    @Insert
    long[] insertAll(Recipe[] recipes);

    @Query("SELECT * FROM " + Recipe.TABLE_NAME)
    Cursor selectAll();

    @Query("SELECT * FROM " + Recipe.TABLE_NAME + " WHERE " + Recipe._ID + " = :id")
    Cursor selectById(long id);

    @Query("DELETE FROM " + Recipe.TABLE_NAME + " WHERE " + Recipe._ID + " = :id")
    int deleteById(long id);

    @Update
    int update(Recipe Recipe);
}

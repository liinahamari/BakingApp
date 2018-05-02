package com.example.guest.bakingapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.example.guest.bakingapp.mvp.model.Reciep;

import static android.provider.DocumentsContract.Root.COLUMN_TITLE;
import static com.example.guest.bakingapp.db.BakingProvider.SquawkMessages.CONTENT_URI;
import static com.example.guest.bakingapp.db.Contract.COLUMN_ID;
import static com.example.guest.bakingapp.db.Contract.COLUMN_IMAGE;
import static com.example.guest.bakingapp.db.Contract.COLUMN_INGREDIENTS;
import static com.example.guest.bakingapp.db.Contract.COLUMN_NAME;
import static com.example.guest.bakingapp.db.Contract.COLUMN_SERVINGS;
import static com.example.guest.bakingapp.db.Contract.COLUMN_STEPS;

/**
 * Created by l1maginaire on 5/2/18.
 */

public class DbOperations {
    public static Cursor getAll(Context context) {
        return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
    }

    public static int delete(int id, Context context){
        return context.getContentResolver().delete(CONTENT_URI, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public static Uri insert(Reciep reciep, Context context){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, reciep.getId());
        values.put(COLUMN_IMAGE, reciep.getImage());
        values.put(COLUMN_NAME, reciep.getName());
        values.put(COLUMN_INGREDIENTS, TextUtils.join(",", reciep.getIngredients()));
        values.put(COLUMN_STEPS, TextUtils.join(",", reciep.getSteps()));
        values.put(COLUMN_SERVINGS, reciep.getServings());
        return context.getContentResolver().insert(CONTENT_URI, values);
    }
}

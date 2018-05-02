package com.example.guest.bakingapp.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import com.example.guest.bakingapp.mvp.model.Reciep;

import static com.example.guest.bakingapp.db.BakingProvider.SquawkMessages.CONTENT_URI;
import static com.example.guest.bakingapp.db.Contract.COLUMN_ID;

/**
 * Created by l1maginaire on 5/2/18.
 */

public class FavoritesChecker {
    public static Integer isFavorite(Context context, Reciep reciep) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor c = null;
        if (reciep.getId() != 0) {
            c = contentResolver.query(CONTENT_URI,null,COLUMN_ID + " = ?",
                    new String[]{String.valueOf(reciep.getId())},null);
        }
        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0 && c.getInt(c.getColumnIndex(COLUMN_ID)) == reciep.getId()) {
                c.close();
                return 1;
            }
            c.close();
        }
        return 0;
    }
}

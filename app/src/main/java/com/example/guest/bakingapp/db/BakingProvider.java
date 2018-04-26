package com.example.guest.bakingapp.db;

/**
 * Created by l1maginaire on 4/26/18.
 */

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(
        authority = BakingProvider.AUTHORITY,
        database = BakingDatabase.class)
public final class BakingProvider {

    public static final String AUTHORITY = "com.example.guest.bakingapp.db.provider";


    @TableEndpoint(table = BakingDatabase.BAKING)
    public static class SquawkMessages {

        @ContentUri(
                path = "messages",
                type = "vnd.android.cursor.dir/messages",
                defaultSort = Contract.COLUMN_ID + " DESC")
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/messages");
    }
}
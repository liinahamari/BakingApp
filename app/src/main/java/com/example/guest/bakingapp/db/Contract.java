package com.example.guest.bakingapp.db;

import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by l1maginaire on 4/26/18.
 */

public class Contract {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    public static final String COLUMN_ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_MESSAGE = "message";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_INGREDIENTS = "message";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_STEPS = "message";
}

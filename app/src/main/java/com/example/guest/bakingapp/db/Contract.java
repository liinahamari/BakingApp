package com.example.guest.bakingapp.db;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;

/**
 * Created by l1maginaire on 4/26/18.
 */

public class Contract {
    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String COLUMN_ID = "id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_IMAGE = "image";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_NAME = "name";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_INGREDIENTS = "ingredients";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_STEPS = "steps";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_SERVINGS = "servings";
}

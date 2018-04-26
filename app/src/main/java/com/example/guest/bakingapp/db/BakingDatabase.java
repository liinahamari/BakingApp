package com.example.guest.bakingapp.db;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by l1maginaire on 4/26/18.
 */

@Database(version = BakingDatabase.VERSION)
public class BakingDatabase {
    public static final int VERSION = 1;

    @Table(Contract.class)
    public static final String BAKING = "baking";
}

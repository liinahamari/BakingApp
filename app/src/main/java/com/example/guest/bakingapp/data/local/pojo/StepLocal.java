package com.example.guest.bakingapp.data.local.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

import static com.example.guest.bakingapp.data.local.pojo.StepLocal.STEPS_TABLE_NAME;

/**
 * Created by l1maginaire on 5/6/18.
 */

@Entity(tableName = STEPS_TABLE_NAME)
public class StepLocal {
    public static final String STEPS_TABLE_NAME = "steps";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_S_DESCRIPTION = "short_description";
    public static final String COLUMN_VIDEO_URL = "video_url";
    public static final String COLUMN_THUMB_URL = "thumb_url";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    public Integer id;
    @ColumnInfo(name = COLUMN_RECIPE_ID)
    public Integer recipeId;
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    public String description;
    @ColumnInfo(name = COLUMN_S_DESCRIPTION)
    public String shortDescription;
    @ColumnInfo(name = COLUMN_VIDEO_URL)
    public String videoURL;
    @ColumnInfo(name = COLUMN_THUMB_URL)
    public String thumbnailURL;

    public static StepLocal[] fromContentValues(ContentValues[] values) {
        StepLocal[] stepLocals = new StepLocal[values.length];
        for (int i = 0; i<values.length; i++) {
            ContentValues values1 = values[i];
            StepLocal stepLocal = new StepLocal();
            if (values1.containsKey(COLUMN_ID)) {
                stepLocal.id = values1.getAsInteger(COLUMN_ID);
            }
            if (values1.containsKey(COLUMN_RECIPE_ID)) {
                stepLocal.recipeId = values1.getAsInteger(COLUMN_RECIPE_ID);
            }
            if (values1.containsKey(COLUMN_DESCRIPTION)) {
                stepLocal.description = values1.getAsString(COLUMN_DESCRIPTION);
            }
            if (values1.containsKey(COLUMN_S_DESCRIPTION)) {
                stepLocal.shortDescription = values1.getAsString(COLUMN_S_DESCRIPTION);
            }
            if (values1.containsKey(COLUMN_VIDEO_URL)) {
                stepLocal.videoURL = values1.getAsString(COLUMN_VIDEO_URL);
            }
            if (values1.containsKey(COLUMN_THUMB_URL)) {
                stepLocal.thumbnailURL = values1.getAsString(COLUMN_THUMB_URL);
            }
            stepLocals[i] = stepLocal;
        }
        return stepLocals;
    }
}
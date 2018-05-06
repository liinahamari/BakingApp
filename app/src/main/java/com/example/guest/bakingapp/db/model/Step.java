package com.example.guest.bakingapp.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;

import static com.example.guest.bakingapp.db.model.Step.STEPS_TABLE_NAME;

/**
 * Created by l1maginaire on 5/6/18.
 */

@Entity(tableName = STEPS_TABLE_NAME,
        foreignKeys = @ForeignKey(entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE))

public class Step {
    public static final String STEPS_TABLE_NAME = "steps";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPE_ID = "r_id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_S_DESCRIPTION = "short_description";
    public static final String COLUMN_VIDEO_URL = "video_url";
    public static final String COLUMN_THUMB_URL = "thumb_url";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    private Integer id;
    @ColumnInfo(name = COLUMN_RECIPE_ID)
    private Integer recipeId;
    @ColumnInfo(name = COLUMN_DESCRIPTION)
    private String description;
    @ColumnInfo(name = COLUMN_S_DESCRIPTION)
    private String shortDescription;
    @ColumnInfo(name = COLUMN_VIDEO_URL)
    private String videoURL;
    @ColumnInfo(name = COLUMN_THUMB_URL)
    private String thumbnailURL;

    public static Step[] fromContentValues(ContentValues[] values) {
        Step[] steps = new Step[values.length];
        for (int i = 0; i<values.length; i++) {
            ContentValues values1 = values[i];
            Step step = new Step();
            if (values1.containsKey(COLUMN_ID)) {
                step.id = values1.getAsInteger(COLUMN_ID);
            }
            if (values1.containsKey(COLUMN_RECIPE_ID)) {
                step.recipeId = values1.getAsInteger(COLUMN_RECIPE_ID);
            }
            if (values1.containsKey(COLUMN_DESCRIPTION)) {
                step.description = values1.getAsString(COLUMN_DESCRIPTION);
            }
            if (values1.containsKey(COLUMN_S_DESCRIPTION)) {
                step.shortDescription = values1.getAsString(COLUMN_S_DESCRIPTION);
            }
            if (values1.containsKey(COLUMN_VIDEO_URL)) {
                step.videoURL = values1.getAsString(COLUMN_VIDEO_URL);
            }
            if (values1.containsKey(COLUMN_THUMB_URL)) {
                step.thumbnailURL = values1.getAsString(COLUMN_THUMB_URL);
            }
            steps[i] = step;
        }
        return steps;
    }
}
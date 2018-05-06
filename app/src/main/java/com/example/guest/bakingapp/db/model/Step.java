package com.example.guest.bakingapp.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

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

    private int recipeId;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int _id;

    private Integer idx;

    private String description;

    private String shortDescription;

    private String videoURL;

    private String thumbnailURL;

    public int getId(){
        return _id;
    }

    public void setId(int id) { this._id = id; }

    public int getRecipeId() { return recipeId; }

    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }

    public Integer getIdx(){
        return idx;
    }

    public void setIdx (Integer idx){
        this.idx = idx;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getShortDescription(){
        return shortDescription;
    }

    public void setShortDescription(String shortdescription){
        this.shortDescription = shortdescription;
    }

    public String getVideoURL(){
        return videoURL;
    }

    public void setVideoURL(String videoURL){
        this.videoURL = videoURL;
    }

    public String getThumbnailURL(){
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL){
        this.thumbnailURL = thumbnailURL;
    }

}
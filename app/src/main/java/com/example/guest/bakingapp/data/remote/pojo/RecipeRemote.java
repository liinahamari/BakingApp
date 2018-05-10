package com.example.guest.bakingapp.data.remote.pojo;

/**
 * Created by l1maginaire on 4/14/18.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeRemote implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<IngredientRemote> ingredientRemotes = null;
    @SerializedName("steps")
    @Expose
    private List<StepRemote> stepRemotes = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    protected RecipeRemote(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientRemotes = new ArrayList<>();
        in.readTypedList(ingredientRemotes, IngredientRemote.CREATOR);
        stepRemotes = new ArrayList<>();
        in.readTypedList(stepRemotes, StepRemote.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public RecipeRemote() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientRemote> getIngredientRemotes() {
        return ingredientRemotes;
    }

    public void setIngredientRemotes(List<IngredientRemote> ingredientRemotes) {
        this.ingredientRemotes = ingredientRemotes;
    }

    public List<StepRemote> getStepRemotes() {
        return stepRemotes;
    }

    public void setStepRemotes(List<StepRemote> stepRemotes) {
        this.stepRemotes = stepRemotes;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredientRemotes);
        dest.writeTypedList(stepRemotes);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    public static final Creator<RecipeRemote> CREATOR = new Creator<RecipeRemote>() {
        @Override
        public RecipeRemote createFromParcel(Parcel in) {
            return new RecipeRemote(in);
        }

        @Override
        public RecipeRemote[] newArray(int size) {
            return new RecipeRemote[size];
        }
    };
}

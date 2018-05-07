package com.example.guest.bakingapp.data.remote.pojo;

/**
 * Created by l1maginaire on 4/14/18.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IngredientRemote implements Parcelable {

    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;

    protected IngredientRemote(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public IngredientRemote() {}

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public static final Creator<IngredientRemote> CREATOR = new Creator<IngredientRemote>() {
        @Override
        public IngredientRemote createFromParcel(Parcel in) {
            return new IngredientRemote(in);
        }

        @Override
        public IngredientRemote[] newArray(int size) {
            return new IngredientRemote[size];
        }
    };
}
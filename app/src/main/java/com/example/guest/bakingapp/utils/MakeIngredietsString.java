package com.example.guest.bakingapp.utils;

import com.example.guest.bakingapp.mvp.model.Ingredient;

import java.util.List;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class MakeIngredietsString {
    public static String make(List<Ingredient> ingredientList){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i<ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);
            builder.append(String.valueOf(i+1));
            builder.append(". ");
            builder.append(ingredient.getIngredient().substring(0,1).toUpperCase());
            builder.append(ingredient.getIngredient().substring(1));
            builder.append(" - ");
            builder.append(ingredient.getQuantity());
            builder.append(ingredient.getMeasure());
            builder.append("\n");
        }
        return builder.toString();
    }
}

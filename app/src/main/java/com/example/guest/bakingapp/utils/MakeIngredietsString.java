package com.example.guest.bakingapp.utils;

import com.example.guest.bakingapp.data.remote.IngredientRemote;

import java.util.List;

/**
 * Created by l1maginaire on 4/29/18.
 */

public class MakeIngredietsString { //todo расшифровка аббревеатур
    public static String make(List<IngredientRemote> ingredientRemoteList){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i< ingredientRemoteList.size(); i++) {
            IngredientRemote ingredientRemote = ingredientRemoteList.get(i);
            builder.append(String.valueOf(i+1));
            builder.append(". ");
            builder.append(ingredientRemote.getIngredient().substring(0,1).toUpperCase());
            builder.append(ingredientRemote.getIngredient().substring(1));
            builder.append(" - ");
            builder.append(ingredientRemote.getQuantity());
            builder.append(ingredientRemote.getMeasure());
            builder.append("\n");
        }
        return builder.toString();
    }
}

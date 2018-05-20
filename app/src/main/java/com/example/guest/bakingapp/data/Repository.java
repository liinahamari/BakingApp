package com.example.guest.bakingapp.data;

import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.data.remote.pojo.StepRemote;

import java.util.List;

/**
 * Created by l1maginaire on 5/18/18.
 */
public class Repository {
    private static Repository repository;
    private List<RecipeRemote> recipes;

    public static Repository get() {
        if (repository == null)
            repository = new Repository();
        return repository;
    }

    private Repository() {
    }

    public void setRecipes(List<RecipeRemote> recipes){
        this.recipes = recipes;
    }

    public RecipeRemote getRecipe(int id) {
        for (RecipeRemote recipe : recipes) {
            if (recipe.getId().equals(id)) {
                return recipe;
            }
        }
        return null;
    }

    public List<StepRemote> getSteps(int recipeId) {
        return getRecipe(recipeId).getStepRemotes();
    }
}

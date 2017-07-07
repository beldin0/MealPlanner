package com.example.android.mealplanner;

import java.util.HashMap;

/**
 * Created by beldi on 03/07/2017.
 */

public class IngredientList extends HashMap<String, Ingredient> {

    public void remove(Ingredient ingredient) {
        this.remove (ingredient.toString());
    }
}
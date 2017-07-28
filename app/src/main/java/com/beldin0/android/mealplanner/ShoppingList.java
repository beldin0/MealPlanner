package com.beldin0.android.mealplanner;

import android.util.Pair;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by beldi on 03/07/2017.
 */

public class ShoppingList extends TreeMap<String, Pair<Ingredient, Quantity>> {

    private void add(Meal meal) {

        IngredientMap mealIngredients = meal.getIngredients();

        for (Map.Entry<Ingredient, Quantity> entry : mealIngredients.entrySet()) {
            String newKey = mealIngredients.getIngredientAndUnit(entry.getKey());

            if (this.containsKey(newKey)) {
                int newAmount = this.get(newKey).second.getAmount() + entry.getValue().getAmount();
                String unit = entry.getValue().getUnitOnly();
                this.put(newKey, new Pair(entry.getKey(), new Quantity(newAmount, unit)
                        ));
            } else {
                this.put(newKey, new Pair(entry.getKey(), entry.getValue()));
            }
        }
    }

    public void generate(Day[] week) {

        for (Day d : week) {
            this.add(d.getMeal());
        }

    }
}

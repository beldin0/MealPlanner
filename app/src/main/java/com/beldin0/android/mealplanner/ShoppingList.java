package com.beldin0.android.mealplanner;

import android.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by beldi on 03/07/2017.
 */

public class ShoppingList extends HashMap<
        String, //ingredient and unit
        Pair<Ingredient, Quantity>> {

    public void add(Ingredient i) {
        this.add(i, new Quantity(0, ""));
    }

    public void add(Ingredient i, Quantity q) {
        this.put(i.toString(), new Pair<>(i, q));
    }

    private void add(Meal meal) {

        ShoppingList mealIngredients = meal.getIngredients();

        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : mealIngredients.entrySet()) {
            String newKey = entry.getKey();

            if (this.containsKey(newKey)) {
                int newAmount = this.get(newKey).second.getAmount() + entry.getValue().second.getAmount();
                String unit = entry.getValue().second.getUnitOnly();
                this.put(newKey, new Pair<>(entry.getValue().first, new Quantity(newAmount, unit)
                        ));
            } else {
                this.put(newKey, new Pair<>(entry.getValue().first, entry.getValue().second));
            }
        }
    }

    public void generate(Week.Day[] week) {

        for (Week.Day d : week) {
            this.add(d.getMeal());
        }

    }

    public String outputToString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : this.entrySet()) {
            sb.append(entry.getValue().first.toString() + " " + entry.getValue().second.toString());
            sb.append("\n");
        }


        return sb.toString();
    }

    public String getIngredientAndUnit(Ingredient i) {
        return String.format("%s (%s)", i.toString(), this.get(i).second.getUnitOnly());
    }

    public boolean hasCarb() {
        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : this.entrySet()) {
            if (entry.getValue().first.isCarb()) return true;
        }
        return false;
    }

    public boolean hasProtein() {
        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : this.entrySet()) {
            if (entry.getValue().first.isProtein()) return true;
        }
        return false;
    }

    public String getCarbName() {
        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : this.entrySet()) {
            if (entry.getValue().first.isCarb()) return entry.getKey();
        }
        return null;
    }

    public String getProteinName() {
        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : this.entrySet()) {
            if (entry.getValue().first.isProtein()) return entry.getKey();
        }
        return null;
    }

    public String[] list() {
        String[] arr = this.keySet().toArray(new String[this.keySet().size()]);
        Arrays.sort(arr);
        return arr;
    }

    public boolean contains(Ingredient i) {
        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : this.entrySet()) {
            if (entry.getValue().first.equals(i)) return true;
        }
        return false;
    }

    public void addAll(IngredientList list) {
        for (Ingredient i : list.values()) {
            this.add(i);
        }
    }

    public void remove(Ingredient i) {
        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : this.entrySet()) {
            if (entry.getValue().first.equals(i)) this.remove(entry.getKey());
        }
    }
}

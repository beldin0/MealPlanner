package com.example.android.mealplanner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beldi on 03/07/2017.
 */

public class IngredientMap extends HashMap<Ingredient, Quantity> {

    public String getIngredientAndUnit(Ingredient i) {
        return String.format("%s (%s)",i.toString(),this.get(i).getUnitOnly());
    }

    public boolean hasCarb() {
        for (Map.Entry<Ingredient, Quantity> entry : this.entrySet())
        {
            if (entry.getKey().isCarb()) return true;
        }
        return false;
    }

    public boolean hasProtein() {
        for (Map.Entry<Ingredient, Quantity> entry : this.entrySet()) {
            if (entry.getKey().isProtein()) return true;
        }
        return false;
    }

    public String getCarbName() {
        for (Map.Entry<Ingredient, Quantity> entry : this.entrySet()) {
            if (entry.getKey().isCarb()) return entry.getKey().toString();
        }
        return null;
    }

    public String getProteinName() {
        for (Map.Entry<Ingredient, Quantity> entry : this.entrySet()) {
            if (entry.getKey().isProtein()) return entry.getKey().toString();
        }
        return null;
    }

    public String getAll() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Ingredient, Quantity> entry : this.entrySet()) {
            sb.append(entry.getKey().toString());
        }
        return sb.toString();
    }


    public void removeAll(IngredientList iList) {
        for (Entry<String, Ingredient> entry : iList.entrySet()) {
            this.remove(entry.getKey());
        }
    }
}

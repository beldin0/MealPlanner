package com.example.android.mealplanner;

import java.util.Arrays;
import java.util.HashMap;

import static com.example.android.mealplanner.ActivityMain.dr;
import static org.apache.commons.lang.WordUtils.capitalizeFully;

/**
 * Created by beldi on 03/07/2017.
 */

public class IngredientList extends HashMap<String, Ingredient> {

    public Ingredient get(String s) {
        return super.get(capitalizeFully(s));
    }

    public void remove(Ingredient ingredient) {
        this.remove (ingredient.toString());
        dr.delete(ingredient);
    }

    public Ingredient put (Ingredient ingredient) {
        Ingredient r = super.put(ingredient.toString(), ingredient);
        dr.addToDatabase(ingredient);
        return r;
    }

    public Ingredient put (Ingredient ingredient, boolean addToDatabase) {
        if (addToDatabase) {
            return this.put(ingredient);
        } else {
            return super.put(ingredient.toString(), ingredient);
        }
    }

    @Override
    public Ingredient put (String string, Ingredient ingredient) {
        return this.put(ingredient);
    }

    public String[] list () {
        String[] a = this.keySet().toArray(new String[this.size()]);
        Arrays.sort(a);
        return a;
    }

    public Ingredient getByNumber (int i) {
        return this.get(this.list()[i]);
    }

    public void removeAll(IngredientList iList) {
        for (Entry<String, Ingredient> entry : iList.entrySet()) {
            this.remove(entry.getKey());
        }
    }

    public void removeAll(IngredientMap iMap) {
        for (Entry<Ingredient, Quantity> entry : iMap.entrySet()) {
            this.remove(entry.getKey().toString());
        }
    }
}


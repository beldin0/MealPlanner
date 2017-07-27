package com.example.android.mealplanner;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static com.example.android.mealplanner.ActivityMain.dr;
import static org.apache.commons.lang.WordUtils.capitalizeFully;

/**
 * Created by beldi on 03/07/2017.
 */

public class IngredientList extends HashMap<String, Ingredient> implements Comparable<Ingredient> {

    private static IngredientList master = null;

    public static IngredientList getMasterList() {
        if (master == null) {
            master = new IngredientList();
        }
        return master;
    }

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

    public ArrayList<Ingredient> list() {
        ArrayList<Ingredient> a = new ArrayList<>(this.values());
        Collections.sort(a);
        return a;
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

    @Override
    public int compareTo(@NonNull Ingredient o) {
        Ingredient c = (Ingredient) o;
        return this.toString().compareTo(c.toString());
    }
}


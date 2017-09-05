package com.beldin0.android.mealplanner;

import android.support.annotation.NonNull;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
        Ingredient i = super.get(capitalizeFully(s));
        if (i == null) {
            i = new Ingredient.IngredientBuilder()
                    .setName(s)
                    .create();
            master.put(i);
        }
        return i;
    }

    public void remove(Ingredient ingredient) {
        this.remove (ingredient.toString());
    }

    public Ingredient put (Ingredient ingredient) {
        Ingredient r = super.put(ingredient.toString(), ingredient);
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
    public Ingredient put(String string, Ingredient ingredient) {
        return this.put(ingredient);
    }

    public ArrayList<Ingredient> list() {
        ArrayList<Ingredient> a = new ArrayList<>(this.values());
        Collections.sort(a);
        return a;
    }

//    public void removeAll(ShoppingList iList) {
//        for (Entry<String, Ingredient> entry : iList.entrySet()) {
//            this.remove(entry.getKey());
//        }
//    }
//
//    public void removeAll(ShoppingList iMap) {
//        for (Entry<Ingredient, Quantity> entry : iMap.entrySet()) {
//            this.remove(entry.getKey().toString());
//        }
//    }

    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"ingredients\":[");
        int c = 0;
        for (String i : master.keySet()) {
            sb.append(master.get(i).toJSON());
            if (++c < master.size()) {
                sb.append(",");
            }
        }
        sb.append("]};");
        return sb.toString();
    }

    public void putAllFromJSON(String jsonString) throws JSONException {

        IngredientList tmp = new IngredientList();
        JSONArray m_jArry = new JSONObject(jsonString).getJSONArray("ingredients");

        for (int i = 0; i < m_jArry.length(); i++) {
            Ingredient ingredient = new Ingredient.IngredientBuilder()
                    .fromJSONObject(m_jArry.getJSONObject(i))
                    .create();
            if (ingredient != null) {
                tmp.put(ingredient);
            }
        }
        this.putAll(tmp);
    }

    @Override
    public int compareTo(@NonNull Ingredient o) {
        Ingredient c = (Ingredient) o;
        return this.toString().compareTo(c.toString());
    }

    public void removeAll(ShoppingList list) {
        for (Map.Entry<String, Pair<Ingredient, Quantity>> entry : list.entrySet()) {
            this.remove(entry.getValue().first);
        }
    }
}


package com.beldin0.android.mealplanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static org.apache.commons.lang.WordUtils.capitalizeFully;

/**
 * Created by beldi on 07/07/2017.
 */

public class MealList extends ArrayList<Meal> {

    private static MealList master = null;

    public static MealList getMasterList() {
        if (master == null) {
            master = new MealList();
        }
        Collections.sort(master);
        return master;
    }

    @Override
    public boolean add (Meal m) {
        return this.add(m, true);
    }

    public boolean add (Meal m, boolean addToDatabase) {
        if (this.contains(m)) {
            this.remove(m);
        }
        boolean b = super.add(m);
        if (addToDatabase) {
        }
        Collections.sort(this);
        return b;
    }

    @Override
    public boolean remove (Object o) {
        boolean b = super.remove(o);
        return b;
    }

    public String toJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"meals\":[");
        int c = 0;
        for (Meal m : master) {
            sb.append(m.toJSON());
            if (++c < master.size()) {
                sb.append(",");
            }
        }
        ;
        sb.append("]};");
        return sb.toString();
    }

    public void addAllFromJSON(String jsonString) throws JSONException {
        MealList tmp = new MealList();
        JSONObject json = new JSONObject(jsonString);
        JSONArray m_jArry = json.getJSONArray("meals");

        for (int i = 0; i < m_jArry.length(); i++) {
            Meal tmpMeal = new Meal.MealBuilder()
                    .fromJSONObject(m_jArry.getJSONObject(i))
                    .create();
            tmp.add(tmpMeal, true);
        }
        this.addAll(tmp);
    }

    public boolean contains(Meal m) {
        for (Meal meal : master) {
            if (meal.toString().equals(m.toString())) return true;
        }
        return false;
    }

    public boolean contains(String s) {
        for (Meal meal : master) {
            if (meal.toString().equals(capitalizeFully(s))) return true;
        }
        return false;
    }

    public Meal get(String s) {
        s = capitalizeFully(s);
        for (Meal meal : master) {
            if (meal.toString().equals(s)) return meal;
        }
        return new Meal.MealBuilder().setName("[" + s.replace("[", "").replace("]", "") + "]").create();
    }

}

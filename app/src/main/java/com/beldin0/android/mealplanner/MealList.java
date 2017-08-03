package com.beldin0.android.mealplanner;

import java.util.ArrayList;
import java.util.Collections;

import static com.beldin0.android.mealplanner.ActivityMain.dr;

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
            dr.addToDatabase(m);
        }
        return b;
    }

    @Override
    public boolean remove (Object o) {
        boolean b = super.remove(o);
        dr.delete((Meal)o);
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

    public boolean contains(Meal m) {
        for (Meal meal : master) {
            if (meal.toString().equals(m.toString())) return true;
        }
        return false;
    }
}

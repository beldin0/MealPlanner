package com.example.android.mealplanner;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.android.mealplanner.ActivityMain.dr;

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
        boolean b = super.add(m);
        dr.addToDatabase(m);
        return b;
    }

    public boolean add (Meal m, boolean addToDatabase) {
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
}

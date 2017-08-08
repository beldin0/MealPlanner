package com.beldin0.android.mealplanner;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Jim on 29/06/2017.
 */

public class MealSelector {

    private ArrayList<Meal> meals;

    public MealSelector () {
        this.refresh();
    }

    public void refresh() {
        meals = new ArrayList<>(MealList.getMasterList());
    }

    public Meal get() {

        if (meals.size()==0) return new Meal("Out of meals!");

        int selection = ThreadLocalRandom.current().nextInt(0,meals.size());

        Meal tmpMeal = meals.get(selection);
        meals.remove(selection);
        return tmpMeal;

    }

    public void remove(Meal meal) {
        meals.remove(meal);
    }

}

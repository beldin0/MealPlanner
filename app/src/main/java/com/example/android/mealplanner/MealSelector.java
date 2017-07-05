package com.example.android.mealplanner;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Jim on 29/06/2017.
 */

public class MealSelector {

    ArrayList<Meal> meals;

    public MealSelector () {
        meals = new ArrayList<Meal>(ArrayFiller.getMeals());
    }

    public Meal get() {

        if (meals.size()==0) return new Meal("Out of meals!");

        int selection = ThreadLocalRandom.current().nextInt(0,meals.size());

        Meal tmpMeal = meals.get(selection);
        meals.remove(selection);
        return tmpMeal;

    }

}

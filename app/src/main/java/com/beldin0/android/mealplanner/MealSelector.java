package com.beldin0.android.mealplanner;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Jim on 29/06/2017.
 */

public class MealSelector extends ArrayList<Meal> {

    public MealSelector() {
        this.refresh();
    }

    public void refresh() {
        this.clear();
        this.addAll(MealList.getMasterList());
    }

    public Meal get() {

        if (this.size() == 0) return new Meal.MealBuilder().setName("# Out of meals! #").create();

        int selection = ThreadLocalRandom.current().nextInt(0, this.size());

        Meal tmpMeal = this.get(selection);
        this.remove(selection);
        return tmpMeal;

    }

    public Meal get(Meal.Options options) {

        if (options == null) {
            return get();
        }

        MealSelector tmpMs = new MealSelector();
        tmpMs.clear();
        tmpMs.addAll(this);
        Meal selection;
        if (options.getInAdvance()) {
            for (Meal m : this) {
                if (!m.inAdvance()) {
                }
            }
        }
        if (options.getType() != null) {
            Meal.MealType type = options.getType();
            for (Meal m : this) {
                if (m.getType() != type) {
                    tmpMs.remove(m);
                }
            }
        }
        if (options.getCookTime() > 0) {
            int cookTime = options.getCookTime();
            for (Meal m : this) {

                if (m.getCookTime() > cookTime) {
                    tmpMs.remove(m);
                }
            }
        }
        selection = tmpMs.get();
        this.remove(selection);

        return selection;
    }
}

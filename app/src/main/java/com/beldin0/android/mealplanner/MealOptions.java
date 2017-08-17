package com.beldin0.android.mealplanner;

/**
 * Created by beldi on 16/08/2017.
 */

import com.beldin0.android.mealplanner.Meal.*;

public class MealOptions {

    private MealType type = null;
    private int cookTime;
    private boolean inAdvance = false;

    public void setTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public MealType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = MealType.toValue(type);
    }

    public int getCookTime() {
        return cookTime;
    }

    public boolean getInAdvance() {
        return inAdvance;
    }

    public void setInAdvance(boolean inAdvance) {
        this.inAdvance = inAdvance;
    }

    public String toString() {
        return "Type: " + type + " In Advance: " + inAdvance + " cookTime: " + cookTime;
    }

}

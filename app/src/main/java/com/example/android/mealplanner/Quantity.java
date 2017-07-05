package com.example.android.mealplanner;

/**
 * Created by beldi on 03/07/2017.
 */

class Quantity {

    private int amount;
    private String unit;

    public Quantity (int amount, String unit) {
        this.amount = amount;
        this.unit = (unit==null? "" : unit);
    }

    public String toString() {
        return "" + amount + (unit.equals("")? "" : " " + unit);
    }

    public String getUnitOnly() {
        return unit;
    }

    public int getAmount() {return amount;}

}
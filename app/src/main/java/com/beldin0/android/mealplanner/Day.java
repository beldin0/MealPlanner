package com.beldin0.android.mealplanner;

class Day {

    private static final String[] weekDays = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday",
    };
    private static Day[] masterWeek = null;
    private String name;
    private Meal meal;

    private Day(String dayName) {
        this.name = dayName;
    }

    public static Day[] makeWeek(int startDay) {
        masterWeek = new Day[7];
        int day = Math.max(0, startDay - 1);

        for (int i = 0; i < 7; i++) {
            masterWeek[i] = new Day(weekDays[day]);
            if (++day > 6) {
                day = 0;
            }
        }
        return masterWeek;
    }

    public static Day[] getMasterWeek() {
        if (masterWeek == null) {
            masterWeek = makeWeek(1);
        }
        return masterWeek;
    }


    public String toString() {
        return this.name;
    }

    public Meal getMeal() {
        return this.meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

}

package com.beldin0.android.mealplanner;

class Day {

    public static final String[] WEEK_DAYS = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday",
    };
    private static Day[] masterWeek = null;
    private static int currentStartDay = 0;
    private String name;
    private Meal meal;

    private Day(String dayName) {
        this.name = dayName;
    }

    public static int getWeekStart() {
        return currentStartDay;
    }

    public static void setWeekStart(int startDay) {
        currentStartDay = startDay;
        makeWeek(currentStartDay);
    }

    public static Day[] makeWeek(int startDay) {
        if (startDay < 0 || startDay > 7) startDay = 0;
        int day = startDay;
        if (masterWeek == null) {
            masterWeek = new Day[7];
            for (int i = 0; i < 7; i++) {
                masterWeek[i] = new Day(WEEK_DAYS[day]);
                if (++day > 6) {
                    day = 0;
                }
            }
        } else {
            for (int i = 0; i < 7; i++) {
                masterWeek[i].name = WEEK_DAYS[day];
                if (++day > 6) {
                    day = 0;
                }
            }
        }
        return masterWeek;
    }

    public static Day[] getMasterWeek() {
        if (masterWeek == null) {
            masterWeek = makeWeek(currentStartDay);
        }
        return masterWeek;
    }

    public static String getWeekAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < masterWeek.length; i++) {
            sb.append(masterWeek[i].name + ": " + masterWeek[i].getMeal().toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String getMealsAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < masterWeek.length; i++) {
            sb.append(masterWeek[i].getMeal().toString());
            sb.append("%");
        }
        return sb.toString();
    }

    public static boolean exists() {
        return (masterWeek != null);
    }

    public static void clear() {
        for (int i = 0; i < masterWeek.length; i++) {
            masterWeek[i].setMeal(null);
        }
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

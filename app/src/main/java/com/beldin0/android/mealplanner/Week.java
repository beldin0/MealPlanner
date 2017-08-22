package com.beldin0.android.mealplanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

class Week {

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

    public static String getMasterWeekStartDate() {
        Calendar c = Calendar.getInstance();
        int correctDay = Calendar.SUNDAY;

        switch (currentStartDay) {
            case 1:
                correctDay = Calendar.MONDAY;
                break;
            case 2:
                correctDay = Calendar.TUESDAY;
                break;
            case 3:
                correctDay = Calendar.WEDNESDAY;
                break;
            case 4:
                correctDay = Calendar.THURSDAY;
                break;
            case 5:
                correctDay = Calendar.FRIDAY;
                break;
            case 6:
                correctDay = Calendar.SATURDAY;
                break;
        }

        while (c.get(Calendar.DAY_OF_WEEK) != correctDay) {
            c.add(Calendar.DATE, 1);
        }

        return WEEK_DAYS[currentStartDay] + "_" + (c.get(Calendar.DATE) + 1) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR);
    }

    public static String masterWeekToJSON() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"masterWeek\":[");
        for (int i = 0; i < masterWeek.length; i++) {
            sb.append(masterWeek[i].toJson());
            if (i < 6) {
                sb.append(",");
            }
        }
        sb.append("]}");
        return sb.toString();
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
        masterWeek = null;
    }

    public static Day[] buildWeekFromJSON(String jsonString) {
        Day[] week = new Day[7];
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray m_jArry = json.getJSONArray("masterWeek");

            for (int i = 0; i < 7; i++) {
                Day d = dayFromJSONObject(m_jArry.getJSONObject(i));
                week[i] = d;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return week;
    }

    private static Day dayFromJSONObject(JSONObject jsonObject) {

        Day d = null;
        try {
            String name = jsonObject.getString("name");
            String meal = jsonObject.getString("meal");
            d = new Day(name);
            d.setMeal(MealList.getMasterList().get(meal));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return d;
    }

    public static class Day {
        private String name;
        private Meal meal;

        private Day(String dayName) {
            this.name = dayName;
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

        public String toJson() {
            return "{\"name\":\"" + name + "\",\"meal\":\"" + meal.toString() + "\"}";
        }
    }
}

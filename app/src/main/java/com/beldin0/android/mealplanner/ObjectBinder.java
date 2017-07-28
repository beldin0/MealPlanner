package com.beldin0.android.mealplanner;

/**
 * Created by beldi on 28/07/2017.
 */

public class ObjectBinder {

    private static Object thisObj = null;

    public static Object getObj() {
        return thisObj;
    }

    public static void setObj(Object obj) {
        thisObj = obj;
    }

    public static boolean hasObj() {
        return !(thisObj == null);
    }

    public static void clear() {
        thisObj = null;
    }
}

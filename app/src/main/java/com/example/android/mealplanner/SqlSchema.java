package com.example.android.mealplanner;

import android.provider.BaseColumns;

/**
 * Created by Jim on 05/07/2017.
 */

public final class SqlSchema {

    private SqlSchema() {
    }

    public static class IngredientsTable implements BaseColumns {
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_CARB = "carb";
        public static final String COLUMN_PROTEIN = "protein";
    }

    public static String SQL_CREATE_INGREDIENTS_TABLE() {
        return "CREATE TABLE " + IngredientsTable.TABLE_NAME + " (" +
                IngredientsTable._ID + " INTEGER PRIMARY KEY," +
                IngredientsTable.COLUMN_NAME + " TEXT," +
                IngredientsTable.COLUMN_LOCATION + " TEXT," +
                IngredientsTable.COLUMN_CARB + " BOOLEAN," +
                IngredientsTable.COLUMN_PROTEIN + " BOOLEAN)";
    }

    public static String SQL_DELETE_INGREDIENTS_TABLE() {
        return "DROP TABLE IF EXISTS " + IngredientsTable.TABLE_NAME;
    }
}

package com.example.android.mealplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by beldi on 03/07/2017.
 */

public class DataRetriever extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MealPlanner.db";

    public DataRetriever(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static IngredientList queryIngredients () {
        return new IngredientList();
    }

    public static ArrayList<Meal> queryMeals() {
        return new ArrayList<Meal>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlSchema.SQL_CREATE_INGREDIENTS_TABLE());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqlSchema.SQL_DELETE_INGREDIENTS_TABLE());
    }
}

package com.beldin0.android.mealplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Handles passing SQL queries and parsing and returning results
 */

public class DataManager extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "MealPlanner.db";

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Sends the
     */
    public IngredientList queryIngredients () {
        IngredientList tmp = new IngredientList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SqlSchema.getIngredients(), null);

        Log.d("queryIngredients","Loading ingredients: found " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                tmp.put(SqlSchema.parseIngredient(cursor), false);
            } while (cursor.moveToNext());
        }

        return tmp;
    }

    public MealList queryMeals() {
        MealList tmp = new MealList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SqlSchema.getMeals(), null);

        Log.d("queryIngredients","Loading ingredients: found " + cursor.getCount());

        if (cursor.getCount()==0) return tmp;

        if (cursor.moveToFirst()) {
            do {
                Meal meal = SqlSchema.parseMeal(cursor);
                meal.add(queryMealIngredients(meal.toString()));
                tmp.add(meal, false);
            } while (cursor.moveToNext());
        }
        return tmp;
    }

    private IngredientMap queryMealIngredients(String meal) {
        IngredientMap tmpMap = new IngredientMap();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SqlSchema.getMealIngredients(meal), null);

        Log.d("queryMealIngredients","Loading ingredients for " + meal + ": found " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                IngredientMap mealIngredient = SqlSchema.parseMealIngredient(cursor);
                tmpMap.putAll(mealIngredient);
            } while (cursor.moveToNext());
        }
        return tmpMap;
    }

    public void addToDatabase (Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.sqlAddIngredient(ingredient));
    }

    public void addToDatabase (Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.sqlAddMeal(meal));
        for (Ingredient ingredient : meal.getIngredients().keySet()) {
            db.execSQL(SqlSchema.sqlAddMealIngredient(meal, ingredient, meal.getQuantity(ingredient)));
        }
    }

    public void delete (Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.deleteIngredient(ingredient));
    }

    public void delete (Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.deleteMeal(meal));
        db.execSQL(SqlSchema.deleteMealIngredients(meal));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlSchema.SQL_CREATE_INGREDIENTS_TABLE());
        db.execSQL(SqlSchema.SQL_CREATE_MEALS_TABLE());
        db.execSQL(SqlSchema.SQL_CREATE_MEAL_INGREDIENTS_TABLE());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//         Load previous database contents
//         IngredientList tmpIngredientList = queryIngredients();
//         MealList tmpMealList = queryMeals();
//
//         Delete and recreate tables
        db.execSQL(SqlSchema.SQL_DELETE_INGREDIENTS_TABLE());
        db.execSQL(SqlSchema.SQL_DELETE_MEALS_TABLE());
        db.execSQL(SqlSchema.SQL_DELETE_MEAL_INGREDIENTS_TABLE());
        onCreate(db);

//         Put data back into database
//        for (String ingredient : tmpIngredientList.list()) {
//            addToDatabase(tmpIngredientList.get(ingredient));
//        }
//        for (int i=0; i< tmpMealList.size(); i++) {
//            addToDatabase(tmpMealList.get(i));
//        }
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.SQL_DELETE_INGREDIENTS_TABLE());
        db.execSQL(SqlSchema.SQL_DELETE_MEALS_TABLE());
        db.execSQL(SqlSchema.SQL_DELETE_MEAL_INGREDIENTS_TABLE());
        onCreate(db);
    }


}

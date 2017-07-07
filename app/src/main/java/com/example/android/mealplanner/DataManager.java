package com.example.android.mealplanner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.android.mealplanner.SqlSchema.parseIngredient;

/**
 * Created by beldi on 03/07/2017.
 */

public class DataManager extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MealPlanner.db";

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public IngredientList queryIngredients () {
        IngredientList tmp = new IngredientList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SqlSchema.getIngredients(), null);

        Log.d("queryIngredients","Loading ingredients: found " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                tmp.put(parseIngredient(cursor), false);
            } while (cursor.moveToNext());
        }

        return tmp;
    }

    public IngredientList queryIngredients () {
        IngredientList tmp = new IngredientList();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SqlSchema.getIngredients(), null);

        Log.d("queryIngredients","Loading ingredients: found " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                tmp.put(parseIngredient(cursor), false);
            } while (cursor.moveToNext());
        }

        return tmp;
    }    public static MealList queryMeals() {
        return new MealList();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlSchema.SQL_CREATE_INGREDIENTS_TABLE());
        db.execSQL(SqlSchema.SQL_CREATE_MEALS_TABLE());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqlSchema.SQL_DELETE_INGREDIENTS_TABLE());
        db.execSQL(SqlSchema.SQL_CREATE_MEALS_TABLE());
        onCreate(db);
    }

    public void addToDatabase (Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.sqlAddIngredient(ingredient));
    }

    public void addToDatabase (Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.sqlAddMeal(meal));
    }

    public void delete (Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.deleteIngredient(ingredient));
    }

    public void delete (Meal meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SqlSchema.deleteMeal(meal));
    }
}

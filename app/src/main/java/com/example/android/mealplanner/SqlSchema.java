package com.example.android.mealplanner;

import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Jim on 05/07/2017.
 */

public final class SqlSchema {

    private SqlSchema() {
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

    public static String SQL_CREATE_MEALS_TABLE() {
        return "CREATE TABLE " + MealsTable.TABLE_NAME + " (" +
                MealsTable._ID + " INTEGER PRIMARY KEY," +
                MealsTable.COLUMN_NAME + " TEXT," +
                MealsTable.COLUMN_PREP + " BOOLEAN," +
                MealsTable.COLUMN_SLOW + " BOOLEAN," +
                MealsTable.COLUMN_TYPE + " TEXT)";
    }

    public static String SQL_DELETE_MEAL_INGREDIENTS_TABLE() {
        return "DROP TABLE IF EXISTS " + MealIngredientsTable.TABLE_NAME;
    }

    public static String SQL_CREATE_MEAL_INGREDIENTS_TABLE() {
        return "CREATE TABLE " + MealIngredientsTable.TABLE_NAME + " (" +
                MealIngredientsTable._ID + " INTEGER PRIMARY KEY," +
                MealIngredientsTable.COLUMN_MEAL_NAME + " TEXT," +
                MealIngredientsTable.COLUMN_INGREDIENT + " TEXT," +
                MealIngredientsTable.COLUMN_AMOUNT + " INTEGER," +
                MealIngredientsTable.COLUMN_UNIT + " TEXT)";
    }

    public static String SQL_DELETE_MEALS_TABLE() {
        return "DROP TABLE IF EXISTS " + MealsTable.TABLE_NAME;
    }

    public static String sqlAddIngredient(Ingredient ingredient) {
        return "INSERT INTO " + IngredientsTable.TABLE_NAME + " (" +
                IngredientsTable.COLUMN_NAME + "," +
                IngredientsTable.COLUMN_LOCATION + "," +
                IngredientsTable.COLUMN_CARB + "," +
                IngredientsTable.COLUMN_PROTEIN + ") " +
                "VALUES (\"" +
                ingredient.toString() + "\",\"" +
                ingredient.location() + "\",\"" +
                ingredient.isCarb() + "\",\"" +
                ingredient.isProtein() + "\");";
    }

    public static String sqlAddMeal(Meal meal) {
        return "INSERT INTO " + MealsTable.TABLE_NAME + " (" +
                MealsTable.COLUMN_NAME + "," +
                MealsTable.COLUMN_PREP + "," +
                MealsTable.COLUMN_SLOW + "," +
                MealsTable.COLUMN_TYPE + ") " +
                "VALUES (\"" +
                meal.toString() + "\",\"" +
                meal.inAdvance() + "\",\"" +
                "false\",\"" +
                meal.getType() + "\");";
    }

    public static String sqlAddMealIngredient(Ingredient ingredient, Quantity quantity) {
        return "INSERT INTO " + MealIngredientsTable.TABLE_NAME + " (" +
                MealIngredientsTable.COLUMN_INGREDIENT + "," +
                MealIngredientsTable.COLUMN_AMOUNT + "," +
                MealIngredientsTable.COLUMN_UNIT + ") " +
                "VALUES (\"" +
                ingredient.toString() + "\"," +
                quantity.getAmount() + ",\"" +
                quantity.getUnitOnly() + "\");";
    }

    public static String deleteMeal (Meal meal) {
        return "DELETE FROM "+ MealsTable.TABLE_NAME + " " +
                "WHERE " + MealsTable.COLUMN_NAME +
                " = \"" + meal.toString() + "\";";
    }

    public static String deleteMealIngredients (Meal meal) {
        return "DELETE FROM "+ MealIngredientsTable.TABLE_NAME + " " +
                "WHERE " + MealIngredientsTable.COLUMN_MEAL_NAME +
                " = \"" + meal.toString() + "\";";
    }

    public static String deleteIngredient (Ingredient ingredient) {
        return "DELETE FROM " + IngredientsTable.TABLE_NAME + " " +
                "WHERE " + IngredientsTable.COLUMN_NAME +
                " = \"" + ingredient.toString() + "\";";
    }

    public static String getIngredients() {
        return "SELECT " +  IngredientsTable.COLUMN_NAME + "," +
                IngredientsTable.COLUMN_LOCATION + "," +
                IngredientsTable.COLUMN_CARB + "," +
                IngredientsTable.COLUMN_PROTEIN + " " +
                "FROM " + IngredientsTable.TABLE_NAME + ";";
    }

    public static String getMeals() {
        return "SELECT " +  MealsTable.COLUMN_NAME + "," +
                MealsTable.COLUMN_PREP + "," +
                MealsTable.COLUMN_SLOW + "," +
                MealsTable.COLUMN_TYPE + " " +
                "FROM " + MealsTable.TABLE_NAME + ";";
    }

    public static String getMealIngredients(String meal) {
        return "SELECT " + MealIngredientsTable.COLUMN_INGREDIENT + "," +
                MealIngredientsTable.COLUMN_AMOUNT + "," +
                MealIngredientsTable.COLUMN_UNIT + " " +
                "FROM " + MealIngredientsTable.TABLE_NAME + " " +
                "WHERE " + MealIngredientsTable.COLUMN_MEAL_NAME +
                " = \"" + meal + "\";";
    }

    public static Ingredient parseIngredient(Cursor cursor) {
        Ingredient ingredient = new Ingredient(
                cursor.getString(0),
                Ingredient.Location.toValue(cursor.getString(1)),
                Boolean.parseBoolean(cursor.getString(2)),
                Boolean.parseBoolean(cursor.getString(3))
        );
        return ingredient;
    }

    public static Meal parseMeal(Cursor cursor) {
        Meal meal = new Meal(
                cursor.getString(0));
        meal.setType(Meal.MealType.toValue(cursor.getString(1)));
        meal.setInAdvance(Boolean.parseBoolean(cursor.getString(2)));
        // meal.setSlowCook(Boolean.parseBoolean(cursor.getString(3)));
        return meal;
    }

    public static IngredientMap parseMealIngredient(Cursor cursor) {
        IngredientMap tmp = new IngredientMap();
        tmp.put(ActivityMain.ingredientList.get(cursor.getString(0)),
                new Quantity(Integer.parseInt(cursor.getString(1)),
                        cursor.getString(2)));
        return tmp;
    }

    public static class IngredientsTable implements BaseColumns {
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_CARB = "carb";
        public static final String COLUMN_PROTEIN = "protein";
    }

    public static class MealsTable implements BaseColumns {
        public static final String TABLE_NAME = "meals";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PREP = "inadvance";
        public static final String COLUMN_SLOW = "slowcook";
        public static final String COLUMN_TYPE = "mealtype";
    }

    public static class MealIngredientsTable implements BaseColumns {
        public static final String TABLE_NAME = "mealingredients";
        public static final String COLUMN_MEAL_NAME = "meal";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_UNIT = "unit";
    }
}

package com.beldin0.android.mealplanner;

import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import static com.beldin0.android.mealplanner.SqlSchema.MealIngredientsTable.COLUMN_INGREDIENT;

/**
 * Created by Jim on 05/07/2017.
 *
 * Contains static classes which define the database schema.
 *
 * Also contains static methods each of which returns a string relating to a SQL query which is needed.
 */

public final class SqlSchema {

    private SqlSchema() {
    }

    /**
     * @return - SQL command to create the Ingredients table
     */
    public static String SQL_CREATE_INGREDIENTS_TABLE() {
        return "CREATE TABLE " + IngredientsTable.TABLE_NAME + " (" +
                IngredientsTable._ID + " INTEGER PRIMARY KEY," +
                IngredientsTable.COLUMN_NAME + " TEXT," +
                IngredientsTable.COLUMN_LOCATION + " TEXT," +
                IngredientsTable.COLUMN_CARB + " BOOLEAN," +
                IngredientsTable.COLUMN_PROTEIN + " BOOLEAN)";
    }

    /**
     * @return - SQL command to delete the Ingredients table
     */
    public static String SQL_DELETE_INGREDIENTS_TABLE() {
        return "DROP TABLE IF EXISTS " + IngredientsTable.TABLE_NAME;
    }


    /**
     * @return - SQL command to create the Meals table
     */
    public static String SQL_CREATE_MEALS_TABLE() {
        return "CREATE TABLE " + MealsTable.TABLE_NAME + " (" +
                MealsTable._ID + " INTEGER PRIMARY KEY," +
                MealsTable.COLUMN_NAME + " TEXT," +
                MealsTable.COLUMN_PREP + " BOOLEAN," +
                MealsTable.COLUMN_SLOW + " BOOLEAN," +
                MealsTable.COLUMN_TYPE + " TEXT)";
    }

    /**
     * @return - SQL command to delete the Meal Ingredients table
     */
    public static String SQL_DELETE_MEAL_INGREDIENTS_TABLE() {
        return "DROP TABLE IF EXISTS " + MealIngredientsTable.TABLE_NAME;
    }

    /**
     * @return - SQL command to create the Meal Ingredients table
     */
    public static String SQL_CREATE_MEAL_INGREDIENTS_TABLE() {
        return "CREATE TABLE " + MealIngredientsTable.TABLE_NAME + " (" +
                MealIngredientsTable._ID + " INTEGER PRIMARY KEY," +
                MealIngredientsTable.COLUMN_MEAL_NAME + " TEXT," +
                COLUMN_INGREDIENT + " TEXT," +
                MealIngredientsTable.COLUMN_AMOUNT + " INTEGER," +
                MealIngredientsTable.COLUMN_UNIT + " TEXT)";
    }

    /**
     * @return - SQL command to delete the Meals table
     */
    public static String SQL_DELETE_MEALS_TABLE() {
        return "DROP TABLE IF EXISTS " + MealsTable.TABLE_NAME;
    }

    /**
     * @param - an Ingredient to be added
     * @return - SQL command to add the Ingredient to the Ingredients table
     */
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

    /**
     * @param - a Meal to be added
     * @return - SQL command to add the Meal to the Meals table
     */
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

    /**
     * @param - a Meal for the Ingredient to be added to
     * @param - an Ingredient to be added to the meal
     * @param - the Quantity of the ingredient to be added
     * @return - SQL command to add the information to the Meal Ingredients table
     */
    public static String sqlAddMealIngredient(Meal meal, Ingredient ingredient, Quantity quantity) {
        return "INSERT INTO " + MealIngredientsTable.TABLE_NAME + " (" +
                MealIngredientsTable.COLUMN_MEAL_NAME + "," +
                MealIngredientsTable.COLUMN_INGREDIENT + "," +
                MealIngredientsTable.COLUMN_AMOUNT + "," +
                MealIngredientsTable.COLUMN_UNIT + ") " +
                "VALUES (\"" +
                meal.toString() + "\",\"" +
                ingredient.toString() + "\"," +
                quantity.getAmount() + ",\"" +
                quantity.getUnitOnly() + "\");";
    }

    /**
     * @param - a Meal to be deleted
     * @return - SQL command to delete the Meal from the Meals table
     */
    public static String deleteMeal (Meal meal) {
        return "DELETE FROM "+ MealsTable.TABLE_NAME + " " +
                "WHERE " + MealsTable.COLUMN_NAME +
                " = \"" + meal.toString() + "\";";
    }

    /**
     * @param - a Meal to be deleted
     * @return - SQL command to delete all of the Ingredients relating to that meal from the Meal Ingredients table
     */
    public static String deleteMealIngredients (Meal meal) {
        return "DELETE FROM "+ MealIngredientsTable.TABLE_NAME + " " +
                "WHERE " + MealIngredientsTable.COLUMN_MEAL_NAME +
                " = \"" + meal.toString() + "\";";
    }

    /**
     * @param - an Ingredient to be deleted
     * @return - SQL command to delete the Ingredient from the Ingredients table
     */
    public static String deleteIngredient (Ingredient ingredient) {
        return "DELETE FROM " + IngredientsTable.TABLE_NAME + " " +
                "WHERE " + IngredientsTable.COLUMN_NAME +
                " = \"" + ingredient.toString() + "\";";
    }

    /**
     * @return - SQL command to get all of the Ingredients from the Ingredients table
     */
    public static String getIngredients() {
        return "SELECT " +  IngredientsTable.COLUMN_NAME + "," +
                IngredientsTable.COLUMN_LOCATION + "," +
                IngredientsTable.COLUMN_CARB + "," +
                IngredientsTable.COLUMN_PROTEIN + " " +
                "FROM " + IngredientsTable.TABLE_NAME + ";";
    }

    /**
     * @return - SQL command to get all of the Meals from the Meals table
     */
    public static String getMeals() {
        return "SELECT " +  MealsTable.COLUMN_NAME + "," +
                MealsTable.COLUMN_PREP + "," +
                MealsTable.COLUMN_SLOW + "," +
                MealsTable.COLUMN_TYPE + " " +
                "FROM " + MealsTable.TABLE_NAME + ";";
    }

    /**
     * @param - the name of a meal
     * @return - SQL command to get all of the Ingredients of that meal from the Meal Ingredients table
     */
    public static String getMealIngredients(String meal) {
        return "SELECT " +
                MealIngredientsTable.COLUMN_INGREDIENT + "," +
                MealIngredientsTable.COLUMN_AMOUNT + "," +
                MealIngredientsTable.COLUMN_UNIT + "," +
                MealIngredientsTable.COLUMN_MEAL_NAME + " " +
                "FROM " + MealIngredientsTable.TABLE_NAME + " " +
                "WHERE " + MealIngredientsTable.COLUMN_MEAL_NAME +
                " = \"" + meal + "\";";
    }

    /**
     * @param - a Cursor pointing at an entry in the Ingredients table
     * @return - an Ingredient
     */
    public static Ingredient parseIngredient(Cursor cursor) {
        Ingredient ingredient = new Ingredient(
                cursor.getString(0),
                Ingredient.Location.toValue(cursor.getString(1)),
                Boolean.parseBoolean(cursor.getString(2)),
                Boolean.parseBoolean(cursor.getString(3))
        );
        return ingredient;
    }

    /**
     * @param - a Cursor pointing at an entry in the Meals table
     * @return - a Meal
     */
    public static Meal parseMeal(Cursor cursor) {
        Meal meal = new Meal(
                cursor.getString(0));
        meal.setType(Meal.MealType.toValue(cursor.getString(1)));
        meal.inAdvance(Boolean.parseBoolean(cursor.getString(2)));
        // meal.setSlowCook(Boolean.parseBoolean(cursor.getString(3)));
        return meal;
    }

    /**
     * @param - a Cursor pointing at an entry in the MealIngredients table
     * @return - an IngredientMap
     */
    public static IngredientMap parseMealIngredient(Cursor cursor) {
        IngredientMap tmp = new IngredientMap();
        String tmpstring = "(";
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            tmpstring += cursor.getString(i);
        }
        Log.d("parseMealIngredient", tmpstring + ")");
        tmp.put(IngredientList.getMasterList().get(cursor.getString(0)),
                new Quantity(cursor.getInt(1),
                        cursor.getString(2)));
        return tmp;
    }


    /**
     * Helper class containing database layout for Ingredients Table
     */
    public static class IngredientsTable implements BaseColumns {
        public static final String TABLE_NAME = "ingredients";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_CARB = "carb";
        public static final String COLUMN_PROTEIN = "protein";
    }

    /**
     * Helper class containing database layout for Meals Table
     */
    public static class MealsTable implements BaseColumns {
        public static final String TABLE_NAME = "meals";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PREP = "inadvance";
        public static final String COLUMN_SLOW = "slowcook";
        public static final String COLUMN_TYPE = "mealtype";
    }

    /**
     * Helper class containing database layout for Meal Ingredients Table
     */
    public static class MealIngredientsTable implements BaseColumns {
        public static final String TABLE_NAME = "mealingredients";
        public static final String COLUMN_MEAL_NAME = "meal";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_UNIT = "unit";
    }
}

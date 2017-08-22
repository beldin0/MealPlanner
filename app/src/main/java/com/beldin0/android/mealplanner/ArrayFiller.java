package com.beldin0.android.mealplanner;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

import static com.beldin0.android.mealplanner.ActivityMain.dr;

public class ArrayFiller {

	public static IngredientList getIngredients(Context c) {
		/**
		 * Queries the database for ingredients.
		 * If none found, populates a basic list
		 *
		 * @return an IngredientList

		 */
		IngredientList tmp;

		tmp = dr.queryIngredients();

		if (tmp.isEmpty()) {
			fillFromJSON(tmp, JSONHelper.getAsJSON(c, "ingredients"));
		}
		return tmp;
	}

	public static void fillFromJSON(IngredientList tmp, String jsonString) {
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray m_jArry = json.getJSONArray("ingredients");

			for (int i = 0; i < m_jArry.length(); i++) {
				Ingredient ingredient = IngredientFromJSONObject(m_jArry.getJSONObject(i));
				tmp.put(ingredient);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static MealList getMeals(Context c) {
		/**
		 * Queries the database for meals.
		 * If none found, populates a basic list
		 *
		 * @return a MealList

		 */

		MealList tmp;

		tmp = dr.queryMeals();

		if (tmp.isEmpty()) {

			fillFromJSON(tmp, JSONHelper.getAsJSON(c, "meals"));

		}
		Collections.sort(tmp);
		return tmp;
	}

	public static void fillFromJSON(MealList tmp, String jsonString) {
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray m_jArry = json.getJSONArray("meals");

			for (int i = 0; i < m_jArry.length(); i++) {
				Meal tmpMeal = MealFromJSONObject(m_jArry.getJSONObject(i));
				tmp.add(tmpMeal, true);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private static Ingredient IngredientFromJSONObject(JSONObject jsonObject) {

		Ingredient tmpI = null;
		try {
			String name = jsonObject.getString("name");
			String location = jsonObject.getString("location");
			boolean carb = jsonObject.getBoolean("carb");
			boolean protein = jsonObject.getBoolean("protein");
			tmpI = new Ingredient(name, location, carb, protein);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return tmpI;
	}

	private static Meal MealFromJSONObject(JSONObject jsonObject) {

		Meal tmpMeal = null;

		try {
			String name = jsonObject.getString("name");
			tmpMeal = new Meal(name);

			try {
				tmpMeal.setCookTime(jsonObject.getInt("cooktime"));
			} catch (JSONException e) {
				Log.d("MealFromJSON: ", name + " has no cooktime");
			}
			try {
				tmpMeal.setType(Meal.MealType.toValue(jsonObject.getString("mealtype")));
			} catch (JSONException e) {
				Log.d("MealFromJSON: ", name + " has no mealtype");
			}
			try {
				tmpMeal.inAdvance(jsonObject.getBoolean("inadvance"));
			} catch (JSONException e) {
				Log.d("MealFromJSON: ", name + " has no inadvance");
			}

			JSONArray jArr = jsonObject.getJSONArray("ingredients");
			for (int i = 0; i < jArr.length(); i++) {
				JSONObject tmpMealIngredient = jArr.getJSONObject(i);
				tmpMeal.add(
						IngredientList.getMasterList().get(tmpMealIngredient.getString("ingredient")),
						new Quantity(tmpMealIngredient.getInt("amount"), tmpMealIngredient.getString("unit"))
				);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tmpMeal;
	}



}
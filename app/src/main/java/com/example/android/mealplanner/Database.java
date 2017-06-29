package com.example.android.mealplanner;

import java.util.ArrayList;

public class Database {

	public static void getIngredients() {
		final String name[] = {"Chicken", "Potatoes", "Stuffing", "Carrots"};
		final Ingredient.Location[] loc = {Ingredient.Location.CHILLED, Ingredient.Location.VEG,Ingredient.Location.CUPBOARD,Ingredient.Location.VEG};
		final boolean carb[] = {false, true, false, false};
		final boolean protein[] = {true, false, false, false};

		for (int i=0; i<name.length; i++) {
			ActivityMain.ingredientList.add(new Ingredient(i, name[i], loc[i], carb[i], protein[i]));
		}

	}

	public static ArrayList<Meal> getMeals() {
		final String name[] = {"Roast Chicken",
				"Butternut Squash Risotto",
				"Mackerel Pasta Bake",
				"Crab Linguine",
				"Jambalaya",
				"Quorn Lasagne",
				"Cacciatore",
				"Poached Salmon"
		};

		ArrayList<Meal> tmpMeals = new ArrayList<>();

		for (String s : name) {
			tmpMeals.add(new Meal(s));
		}

		return tmpMeals;
	}
	
}
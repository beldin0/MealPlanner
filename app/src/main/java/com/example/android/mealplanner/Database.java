package com.example.android.mealplanner;

public class Database {

	public static void getIngredients() {
		final String name[] = {"Chicken", "Potatoes", "Stuffing", "Carrots"};
		final Ingredient.Location[] loc = {Ingredient.Location.CHILLED, Ingredient.Location.VEG,Ingredient.Location.CUPBOARD,Ingredient.Location.VEG};
		final boolean carb[] = {false, true, false, false};
		final boolean protein[] = {true, false, false, false};

		for (int i=0; i<name.length; i++) {
			Main.ingredientList.add(new Ingredient(i, name[i], loc[i], carb[i], protein[i]));
		}

	}
	
}
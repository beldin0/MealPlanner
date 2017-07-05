package com.example.android.mealplanner;

import java.util.ArrayList;

public class ArrayFiller {

	public static ArrayList<Ingredient> getIngredients() {

		ArrayList<Ingredient> tmp;

		tmp = DataRetriever.queryIngredients();
		if (tmp.isEmpty()) {
			final String name[] = {"Chicken", "Potatoes", "Stuffing", "Carrots"};
			final Ingredient.Location[] loc = {Ingredient.Location.CHILLED, Ingredient.Location.VEG, Ingredient.Location.CUPBOARD, Ingredient.Location.VEG};
			final boolean carb[] = {false, true, false, false};
			final boolean protein[] = {true, false, false, false};

			for (int i = 0; i < name.length; i++) {
				tmp.add(new Ingredient(name[i], loc[i], carb[i], protein[i]));
			}
		}
		return tmp;
	}

	public static ArrayList<Meal> getMeals() {

		ArrayList<Meal> tmp;

		tmp = DataRetriever.queryMeals();
		if (tmp.isEmpty()) {

			final String[] name = {"Roast Chicken",
					"Butternut Squash Risotto",
					"Mackerel Pasta Bake",
					"Crab Linguine",
					"Jambalaya",
					"Quorn Lasagne",
					"Cacciatore",
					"Poached Salmon"
			};

			final String[][] tmpIngredients = {
					{"Chicken:1:whole", "Potatoes:200:g", "Stuffing:50:g", "Carrots:200:g"},
					{"Lasagne:200:g"},
					{"Smoked Mackerel:100:g"},
					{"Tinned Crabmeat:200:g"},
					{"King Prawns:150:g"},
					{"Quorn mince:250:g"},
					{"Chicken thighs:4:"},
					{"Salmon:2:pieces"}
			};

			for (int s=0; s<name.length; s++) {
				Meal tmpMeal = new Meal(name[s]);
					for (String iArray : tmpIngredients[s]) {
						String[] i = iArray.split(":",-1);
						tmpMeal.add(new Ingredient(i[0]),new Quantity(Integer.parseInt(i[1]), "" + i[2]));
					}
				tmp.add(tmpMeal);
			}
		}
		return tmp;
	}
	
}
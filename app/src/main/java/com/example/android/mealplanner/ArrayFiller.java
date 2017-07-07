package com.example.android.mealplanner;

import java.util.ArrayList;

public class ArrayFiller {

	public static IngredientList getIngredients() {

		IngredientList tmp;

		tmp = DataRetriever.queryIngredients();
		if (tmp.isEmpty()) {
			tmp.put("Chicken", new Ingredient("Chicken", Ingredient.Location.CHILLED, false, true));
			tmp.put("Potatoes", new Ingredient("Potatoes", Ingredient.Location.VEG, true, false));
			tmp.put("Stuffing", new Ingredient("Stuffing", Ingredient.Location.CUPBOARD, false, false));
			tmp.put("Carrots", new Ingredient("Carrots", Ingredient.Location.VEG, false, false));
			tmp.put("Lasagne sheets", new Ingredient("Lasagne sheets", Ingredient.Location.CUPBOARD, true, false));
			tmp.put("Smoked Mackerel", new Ingredient("Smoked Mackerel", Ingredient.Location.CHILLED, false, true));
			tmp.put("Tinned Crabmeat", new Ingredient("Tinned Crabmeat", Ingredient.Location.CUPBOARD, false, true));
			tmp.put("King Prawns", new Ingredient("King Prawns", Ingredient.Location.CHILLED, false, true));
			tmp.put("Quorn mince", new Ingredient("Quorn mince", Ingredient.Location.FROZEN, false, true));
			tmp.put("Salmon", new Ingredient("Salmon", Ingredient.Location.CHILLED, false, true));
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
					{"Lasagne sheets:200:g"},
					{"Smoked Mackerel:100:g"},
					{"Tinned Crabmeat:200:g"},
					{"King Prawns:150:g"},
					{"Quorn mince:250:g"},
					{"Chicken:4:thighs"},
					{"Salmon:2:pieces"}
			};

			for (int s=0; s<name.length; s++) {
				Meal tmpMeal = new Meal(name[s]);
					for (String iArray : tmpIngredients[s]) {
						String[] i = iArray.split(":",-1);
						tmpMeal.add(ActivityMain.ingredientList.get(i[0]),new Quantity(Integer.parseInt(i[1]), "" + i[2]));
					}
				tmp.add(tmpMeal);
			}
		}
		return tmp;
	}
	
}
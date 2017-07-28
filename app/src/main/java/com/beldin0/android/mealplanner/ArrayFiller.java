package com.beldin0.android.mealplanner;

import java.util.Collections;

import static com.beldin0.android.mealplanner.ActivityMain.dr;

public class ArrayFiller {

	public static IngredientList getIngredients() {
		/**
		 * Queries the database for ingredients.
		 * If none found, populates a basic list
		 *
		 * @return an IngredientList

		 */
		IngredientList tmp;

		tmp = dr.queryIngredients();
		if (tmp.isEmpty()) {
			tmp.put(new Ingredient("Chicken", Ingredient.Location.CHILLED, false, true));
			tmp.put(new Ingredient("Potatoes", Ingredient.Location.VEG, true, false));
			tmp.put(new Ingredient("Stuffing", Ingredient.Location.CUPBOARD, false, false));
			tmp.put(new Ingredient("Carrots", Ingredient.Location.VEG, false, false));
			tmp.put(new Ingredient("Lasagne sheets", Ingredient.Location.CUPBOARD, true, false));
			tmp.put(new Ingredient("Smoked Mackerel", Ingredient.Location.CHILLED, false, true));
			tmp.put(new Ingredient("Tinned Crabmeat", Ingredient.Location.CUPBOARD, false, true));
			tmp.put(new Ingredient("King Prawns", Ingredient.Location.CHILLED, false, true));
			tmp.put(new Ingredient("Quorn mince", Ingredient.Location.FROZEN, false, true));
			tmp.put(new Ingredient("Salmon", Ingredient.Location.CHILLED, false, true));
		}
		return tmp;
	}

	public static MealList getMeals() {
		/**
		 * Queries the database for meals.
		 * If none found, populates a basic list
		 *
		 * @return a MealList

		 */

		MealList tmp;

		tmp = dr.queryMeals();
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
					{"Chicken:1:", "Potatoes:200:g", "Stuffing:50:g", "Carrots:200:g"},
					{"Lasagne sheets:200:g"},
					{"Smoked Mackerel:100:g"},
					{"Tinned Crabmeat:200:g"},
					{"King Prawns:150:g"},
					{"Quorn mince:250:g"},
					{"Chicken Pieces:4:"},
					{"Salmon:2:pieces"}
			};

			for (int s=0; s<name.length; s++) {
				Meal tmpMeal = new Meal(name[s]);
					for (String iArray : tmpIngredients[s]) {
						String[] i = iArray.split(":",-1);
                        tmpMeal.add(IngredientList.getMasterList().get(i[0]), new Quantity(Integer.parseInt(i[1]), "" + i[2]));
                    }
				tmp.add(tmpMeal, true);
			}
		}
		Collections.sort(tmp);
		return tmp;
	}
	
}
package com.example.android.mealplanner;

import java.util.ArrayList;
import java.util.Arrays;

public class Meal {
	
	private String name;
	private ArrayList<MealIngredient> ingredients;
	private MealType type;
	private int cookTime;
	private boolean inAdvance = false;

	public Meal(String name) {
		ingredients = new ArrayList<>();
		this.name = name;
	}
	
	public ArrayList<MealIngredient> getIngredients() {
		return ingredients;
	}

	public void add(Ingredient ingredient, int amount, String unit) {
		this.ingredients.add(new MealIngredient(ingredient, amount, unit));
	}
	
	public void remove(Ingredient ingredient) {
		this.ingredients.remove(ingredient);
	}
	
	public boolean hasCarb() {
		for (MealIngredient i : ingredients) {
			if (i.isCarb()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasProtein() {
		for (MealIngredient i : ingredients) {
			if (i.isProtein()) {
				return true;
			}
		}
		return false;
	}
	public String carb() {
		for (MealIngredient i : ingredients) {
			if (i.isCarb()) {
				return i.getName();
			}
		}
		return null;
	}
	
	public String protein() {
		for (MealIngredient i : ingredients) {
			if (i.isProtein()) {
				return i.getName();
			}
		}
		return null;
	}

	public MealType getType() {
		return type;
	}

	public void setType(MealType type) {
		this.type = type;
	}

	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	public boolean inAdvance() {
		return inAdvance;
	}

	public void setInAdvance(boolean inAdvance) {
		this.inAdvance = inAdvance;
	}
	
	public enum MealType {
		CURRY("Curry"),
		RISOTTO("Risotto"),
		ROAST("Roast Dinner"),
		SOUP("Soup");
		
		private String mt;
		
		private MealType(String mt) {
			this.mt = mt;
		}
		
		public static String[] list() {
			String[] rtn = new String[MealType.values().length];
			int i = 0;
			for (MealType m : MealType.values()) {
				rtn[i++]=m.toString();
			}
			Arrays.sort(rtn);
			return rtn;
		}
		
		public String toString() {
			return mt;
		}
		
	}
}

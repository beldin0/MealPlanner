package com.example.android.mealplanner;

import com.example.android.mealplanner.Ingredient.Location;

public class MealIngredient {

	private Ingredient ingredient;
	private int amount;
	private String unit;
	
	public MealIngredient(Ingredient ingredient, int amount, String unit) {
		this.ingredient = ingredient;
		this.amount = amount;
		this.unit = unit;
	}
	
	public String toString() {
		return String.format("%s, %d %s", ingredient.toString(), amount, unit);
	}
	
	public String getName() {
		return ingredient.toString();
	}
	
	public int getId() {
		return ingredient.id();
	}
	
	public int getAmount() {
		return amount;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public boolean isCarb() {
		return ingredient.isCarb();
	}
	
	public boolean isProtein() {
		return ingredient.isProtein();
	}
	
	public Location location() {
		return ingredient.location();
	}
	
}
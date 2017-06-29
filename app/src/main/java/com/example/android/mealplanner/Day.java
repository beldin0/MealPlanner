package com.example.android.mealplanner;

class Day {
	
	private String name;
	private Meal meal;	
	
	public Day(String dayName) {
		this.name = dayName;
	}
	
	public String toString() {
		return this.name;
	}

	public void setMeal (Meal meal) {
		this.meal = meal;
	}

	public Meal getMeal () {return this.meal;}
	
}

package com.example.android.mealplanner;

class Day {
	
	private static final String[] weekDays = {
			"Monday",
			"Tuesday",
			"Wednesday",
			"Thursday",
			"Friday",
			"Saturday",
			"Sunday",
	};
	private String name;
	private Meal meal;

	private Day(String dayName) {
		this.name = dayName;
	}

	public static Day[] makeWeek() {
		return makeWeek(1);
	}

	public static Day[] makeWeek(int startDay) {
		Day[] week = new Day[7];
		int day = Math.max(0, startDay - 1);

		for (int i = 0; i < 7; i++) {
			week[i] = new Day(weekDays[day]);
			if (++day > 6) {
				day = 0;
			}
		}
		return week;
	}
	
	public String toString() {
		return this.name;
	}

	public Meal getMeal () {return this.meal;}

	public void setMeal (Meal meal) {
		this.meal = meal;
	}
	
}

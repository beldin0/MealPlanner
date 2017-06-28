package com.example.android.mealplanner;

public final class Week {
	private static final String[] weekDays = {
			"Monday",
			"Tuesday",
			"Wednesday",
			"Thursday",
			"Friday",
			"Saturday",
			"Sunday",
			};
	private static Day[] day;
	private static boolean exists = false;

	public static Day[] get() {
		return get(1);
	}

	public static Day[] get(int startDay) {
		if (!exists) {
			day = new Day[7];
			makeWeek(startDay);
			exists = true;
		}
		return Week.access();
	}

	private static void makeWeek(int startDay) {
		int day = Math.max(0, startDay - 1);

		for (int i = 0; i < 7; i++) {
			Week.day[i] = new Day(weekDays[day]);
			if (++day > 6) {
				day = 0;
			}
		}
	}

	private static Day[] access() {
		return Week.day;
	}
	
	

}

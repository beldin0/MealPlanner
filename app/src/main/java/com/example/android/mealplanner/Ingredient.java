package com.example.android.mealplanner;

import android.support.annotation.NonNull;

import java.util.Arrays;

import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

public class Ingredient implements Comparable {

	private String name;
	private Location loc;
	private boolean carb = false;
	private boolean protein = false;

	public Ingredient(String name) {
		this(capitalizeFully(name), false, false);
	}
	
	public Ingredient(String name, boolean carb, boolean protein) {
		this(capitalizeFully(name), Location.UNKNOWN, carb, protein);
	}
	
	public Ingredient(String name, Location loc, boolean carb, boolean protein) {
		this.name = capitalizeFully(name);
		this.carb = carb;
		this.protein = protein;
		this.loc = loc;
	}

	public static void add(IngredientList list, String name, Location loc, boolean carb, boolean protein) {
        list.put(capitalizeFully(name), new Ingredient(capitalizeFully(name), loc, carb, protein));
    }

	public String getInfo() {
        return name + (this.carb? " (carb)" : (this.protein? " (protein)" : "")) + "\nFound in: " + loc.toString();
	}

	public Location location() {
		return loc;
	}

	public String toString() {
		return name;
	}

	public boolean isCarb() {
		return carb;
	}

	public boolean isProtein() {
		return protein;
	}

	public boolean isUsed() {
		for (Meal m : ActivityMain.mealList) {
			if (m.getIngredientsAsArray().contains(this)) {
				return true;
			}
		}
		return false;
	}

    @Override
    public int compareTo(@NonNull Object o) {
        Ingredient c = (Ingredient)o;
        return this.toString().compareTo(c.toString());
    }

    public enum Location {
		UNKNOWN("_Not set"),
		BREAD("Bread & Cakes"),
		CHILLED("Chilled Meat"),
		DAIRY("Dairy"),
		DRINKS("Soft Drinks"),
		FROZEN("Frozen"),
		VEG("Fruit & Veg"),
		HERBS("Herbs & Condiments"),
		MILK("Milk & Cream"),
		TINNED("Tinned"),
		CUPBOARD("Store Cupboard");

		private String locString;

		Location(String locString) {
			this.locString = locString;
		}

		public static Location toValue(String loc) {
            for (Location l : Location.values()) {
                if (l.toString().equals(loc)) return l;
            }
            return Location.UNKNOWN;
        }

		public static String[] list() {
			String[] rtn = new String[Location.values().length];
			int i = 0;
			for (Location l : Location.values()) {
				rtn[i++]=l.toString();
			}
			Arrays.sort(rtn);
			return rtn;
		}

				public String toString() {
			return locString;
		}

    }

}

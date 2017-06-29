package com.example.android.mealplanner;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

public class Ingredient implements Comparable {

	private int id;
	private String name;
	private Location loc;
	private boolean carb = false;
	private boolean protein = false;

	public Ingredient(int id, String name) {
		this(id, name, false, false);
	}
	
	public Ingredient(int id, String name, boolean carb, boolean protein) {
		this(id, proper(name), Location.UNKNOWN, carb, protein);
	}
	
	public Ingredient(int id, String name, Location loc, boolean carb, boolean protein) {
		this.id = id;
		this.name = proper(name);
		this.carb = carb;
		this.protein = protein;
		this.loc = loc;
	}

	public static void add(List<Ingredient> list, String name, Location loc, boolean carb, boolean protein) {
        list.add(new Ingredient(list.size(), proper(name), loc, carb, protein));
        Collections.sort(list);
    }

	private static String proper (String name) {
        return capitalizeFully(name);
    }
	
	public int id() {
		return id;
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

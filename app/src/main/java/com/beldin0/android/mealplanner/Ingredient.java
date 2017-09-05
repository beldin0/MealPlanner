package com.beldin0.android.mealplanner;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

public class Ingredient implements Comparable {

	private String name;
    private Location loc;
    private String defaultUnit;
    private boolean carb;
    private boolean protein;

    private Ingredient(IngredientBuilder b) {
        this.name = capitalizeFully(b.name);
        this.defaultUnit = b.defaultUnit;
        this.carb = b.carb;
        this.protein = b.protein;
        this.loc = b.loc;
    }

    public String getDefaultUnit() {
        return defaultUnit;
    }

	public Location location() {
		return loc;
	}

	public String toString() {
		if (this==null) return "";
		return name;
	}

	public boolean isCarb() {
		return carb;
	}

	public boolean isProtein() {
		return protein;
	}

	public boolean isUsed() {
        for (Meal m : MealList.getMasterList()) {
            if (m.getIngredients().contains(this)) {
                return true;
            }
		}
		return false;
	}

	public String toJSON() {
        return "{\"name\":\"" + name
                + "\",\"location\":\"" + loc.toString() + "\""
                + ",\"carb\":" + this.carb
                + ",\"protein\":" + this.protein
                + ",\"unit\":\"" + (this.defaultUnit == null ? "" : this.defaultUnit) + "\""
                + "}";
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

    public static class IngredientBuilder {
        private String name;
        private Location loc = Location.UNKNOWN;
        private String defaultUnit;
        private boolean carb = false;
        private boolean protein = false;

        public IngredientBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public IngredientBuilder setLocation(String location) {
            this.loc = Location.toValue(location);
            return this;
        }

        public IngredientBuilder setDefaultUnit(String unit) {
            this.defaultUnit = unit;
            return this;
        }

        public IngredientBuilder setCarb(boolean carb) {
            this.carb = carb;
            return this;
        }

        public IngredientBuilder setProtein(boolean protein) {
            this.protein = protein;
            return this;
        }

        public Ingredient create() {
            return new Ingredient(this);
        }

        public IngredientBuilder fromJSONObject(JSONObject jsonObject) {

            try {
                this.name = jsonObject.getString("name");
                try {
                    this.loc = Location.toValue(jsonObject.getString("location"));
                } catch (JSONException e) {
                    if (ActivityMain.LOGGING)
                        Log.v("IngredientFromJSON: ", name + " has no location");
                }
                try {
                    this.carb = jsonObject.getBoolean("carb");
                } catch (JSONException e) {
                    if (ActivityMain.LOGGING) Log.v("IngredientFromJSON: ", name + " has no carb");
                }
                try {
                    this.protein = jsonObject.getBoolean("protein");
                } catch (JSONException e) {
                    if (ActivityMain.LOGGING)
                        Log.v("IngredientFromJSON: ", name + " has no protein");
                }
                try {
                    this.defaultUnit = jsonObject.getString("unit");
                } catch (JSONException e) {
                    if (ActivityMain.LOGGING) Log.v("IngredientFromJSON: ", name + " has no unit");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return this;
        }

    }

}

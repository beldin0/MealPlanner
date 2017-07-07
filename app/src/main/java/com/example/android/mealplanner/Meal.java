package com.example.android.mealplanner;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

public class Meal implements Comparable{
	
	private String name;
	private IngredientMap ingredients;
	private MealType type;
	private int cookTime;
	private boolean inAdvance = false;

	public Meal(String name) {
		// ingredients = new Map<Ingredient, Quantity>();
		this.name = name;
		ingredients = new IngredientMap();
	}

	public IngredientMap getIngredients() {return ingredients;}

	public IngredientList getPossibleIngredients() {
		IngredientList tmpList = ActivityMain.ingredientList;
		for (Ingredient i : ingredients.keySet()) {
			tmpList.remove(i);
		}
		return tmpList;
	}

	public Quantity getQuantity(Ingredient i) {
		return ingredients.get(i);
	}

	public ArrayList<Ingredient> getIngredientsAsArray()
	{
		return new ArrayList<Ingredient>(ingredients.keySet());
	}

	public void add(Ingredient ingredient, Quantity amount) {
		this.ingredients.put(ingredient, amount);
	}

	public void add(IngredientMap iMap) {
		ingredients.putAll(iMap);
	}
	
	public void remove(Ingredient ingredient) {
		this.ingredients.remove(ingredient);
	}
	
	public boolean hasCarb() {
		return ingredients.hasCarb();
	}
	
	public boolean hasProtein() {
		return ingredients.hasProtein();
	}
	public String getCarbName() {
		return ingredients.getCarbName();
	}
	
	public String getProteinName() {
		return ingredients.getProteinName();
	}

    public String getInfo() {
        return name;
    }

	public MealType getType() {
		return type;
	}

	public void setType(MealType type) {
		this.type = type;
	}

	public String toString() {return name;}

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

	@Override
    public int compareTo(@NonNull Object o) {
        Meal c = (Meal)o;
        return this.toString().compareTo(c.toString());
    }


	public enum MealType {
		CURRY("Curry"),
		RISOTTO("Risotto"),
		ROAST("Roast Dinner"),
		SOUP("Soup");
		
		private String mt;
		
		MealType(String mt) {
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

		public static MealType toValue(String mealType) {
			for (MealType l : MealType.values()) {
				if (l.toString().equals(mealType)) return l;
			}
			return null;
		}
		
		public String toString() {
			return mt;
		}
		
	}
}

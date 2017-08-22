package com.beldin0.android.mealplanner;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;

import static org.apache.commons.lang.WordUtils.capitalizeFully;

public class Meal implements Comparable{
	
	private String name;
	private IngredientMap ingredients;
	private MealType type = MealType.NONE;
	private int cookTime = 0;
	private boolean inAdvance = false;

	public Meal(String name) {
		// ingredients = new Map<Ingredient, Quantity>();
        this.name = capitalizeFully(name);
        ingredients = new IngredientMap();
	}

	public IngredientMap getIngredients() {return ingredients;}

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
		return String.format("Ingredients:%d", ingredients.size());
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

    public void inAdvance(boolean inAdvance) {
        this.inAdvance = inAdvance;
	}

	public String toJSON() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"name\":\"" + name + "\",");
		if (type != MealType.NONE) {
			sb.append("\"mealtype\":\"" + type.toString() + "\",");
		}
		if (cookTime > 0) {
			sb.append("\"cooktime\":\"" + cookTime + "\",");
		}
		if (inAdvance) {
			sb.append("\"inadvance\":\"" + inAdvance + "\",");
		}
		sb.append("\"ingredients\":[");
		int c = 0;
		for (Ingredient i : ingredients.keySet()) {
			sb.append("{\"ingredient\":\"" + i.toString() + "\", \"amount\":" + ingredients.get(i).getAmount() + ", \"unit\":\"" + ingredients.get(i).getUnitOnly() + "\"}");
			if (++c < ingredients.size()) {
				sb.append(",");
			}
		}
		sb.append("]}");
		return sb.toString();
	}

	@Override
    public int compareTo(@NonNull Object o) {
        Meal c = (Meal)o;
        return this.toString().compareTo(c.toString());
    }


	public enum MealType {
		NONE(""),
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

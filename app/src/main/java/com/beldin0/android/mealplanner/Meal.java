package com.beldin0.android.mealplanner;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static org.apache.commons.lang.WordUtils.capitalizeFully;

public class Meal implements Comparable{
	
	private String name;
    private ShoppingList ingredients;
    private MealType type;
    private int cookTime;
    private boolean inAdvance;

    private Meal(MealBuilder b) {
        this.name = capitalizeFully(b.name);
        this.type = b.type;
        this.cookTime = b.cookTime;
        this.inAdvance = b.inAdvance;
        ingredients = b.ingredients;
    }

    public ShoppingList getIngredients() {
        return ingredients;
    }

    public void add(Ingredient i, Quantity q) {
        this.ingredients.put(String.format("%s (%s)", i.toString(), q.getUnitOnly()), new Pair<>(i, q));
    }

    public void add(ShoppingList iMap) {
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
        sb.append("\"name\":\"")
                .append(name)
                .append("\",");
        if (type != MealType.NONE && type != null) {
            sb.append("\"mealtype\":\"")
                    .append(type.toString())
                    .append("\",");
        } else if (type == null) {
            if (ActivityMain.LOGGING) Log.v("Meal.toJSON", this.toString() + " has null type.");
        }
        if (cookTime > 0) {
            sb.append("\"cooktime\":\"")
                    .append(cookTime)
                    .append("\",");
        }
        if (inAdvance) {
            sb.append("\"inadvance\":\"")
                    .append(inAdvance)
                    .append("\",");
        }
        sb.append("\"ingredients\":[");
		int c = 0;
        for (Pair<Ingredient, Quantity> p : ingredients.values()) {
            sb.append("{")
                    .append("\"ingredient\":\"")
                    .append(p.first.toString())
                    .append("\", \"amount\":")
                    .append(p.second.getAmount())
                    .append(", \"unit\":\"")
                    .append(p.second.getUnitOnly())
                    .append("\"}");
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

    public static class MealBuilder {
        private String name;
        private MealType type = MealType.NONE;
        private int cookTime = 0;
        private boolean inAdvance = false;
        private ShoppingList ingredients;

        public MealBuilder() {
            ingredients = new ShoppingList();
        }

        public MealBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public MealBuilder setType(MealType type) {
            this.type = type;
            return this;
        }

        public MealBuilder setCookTime(int cookTime) {
            this.cookTime = cookTime;
            return this;
        }

        public MealBuilder setInAdvance(boolean inAdvance) {
            this.inAdvance = inAdvance;
            return this;
        }

        public Meal create() {
            return new Meal(this);
        }

        public MealBuilder fromJSONObject(JSONObject jsonObject) {

            try {
                this.name = jsonObject.getString("name");
                if (ActivityMain.LOGGING) Log.v("Meal.FromJSON", "Loading " + name);
                try {
                    this.cookTime = jsonObject.getInt("cooktime");
                } catch (JSONException e) {
                    if (ActivityMain.LOGGING) Log.v("MealFromJSON: ", name + " has no cooktime");
                }
                try {
                    this.type = (MealType.toValue(jsonObject.getString("mealtype")));
                } catch (JSONException e) {
                    if (ActivityMain.LOGGING) Log.v("MealFromJSON: ", name + " has no mealtype");
                }
                try {
                    this.inAdvance = jsonObject.getBoolean("inadvance");
                } catch (JSONException e) {
                    if (ActivityMain.LOGGING) Log.v("MealFromJSON: ", name + " has no inadvance");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray jArr = null;
            try {
                jArr = jsonObject.getJSONArray("ingredients");
            } catch (JSONException e) {
                if (ActivityMain.LOGGING) Log.d("MealFromJSON: ", name + " has no ingredients");
            }
            for (int i = 0; i < jArr.length(); i++) {
                try {
                    JSONObject tmpMealIngredient = jArr.getJSONObject(i);
                    this.ingredients.add(
                            IngredientList.getMasterList().get(tmpMealIngredient.getString("ingredient")),
                            new Quantity(tmpMealIngredient.getInt("amount"), tmpMealIngredient.getString("unit")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return this;
        }
    }

    public static class Options {

        private MealType type = null;
        private int cookTime;
        private boolean inAdvance = false;

        public void setTime(int cookTime) {
            this.cookTime = cookTime;
        }

        public MealType getType() {
            return type;
        }

        public void setType(String type) {
            this.type = MealType.toValue(type);
        }

        public int getCookTime() {
            return cookTime;
        }

        public boolean getInAdvance() {
            return inAdvance;
        }

        public void setInAdvance(boolean inAdvance) {
            this.inAdvance = inAdvance;
        }

        public String toString() {
            return "Type: " + type + " In Advance: " + inAdvance + " cookTime: " + cookTime;
        }

    }
}

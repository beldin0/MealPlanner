package com.example.android.mealplanner;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class ActivityAddMeal extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private final IngredientMap tmpMealIngredients = new IngredientMap();
    private final IngredientList tmpSpinner = new IngredientList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        ((Spinner) findViewById(R.id.mealtype_spinner)).setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, Meal.MealType.list()));

        tmpSpinner.putAll(ActivityMain.ingredientList);
        prepare();
    }

    public void prepare() {
        tmpSpinner.removeAll(tmpMealIngredients);
        ((Spinner) findViewById(R.id.ingredient_spinner)).setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, tmpSpinner.list()));

        Button addIngredientButton = (Button) findViewById(R.id.add_new_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient ingredient = ActivityMain.ingredientList.get(((Spinner) findViewById(R.id.ingredient_spinner)).getSelectedItem().toString());

                final Dialog d = ingredientQuantityDialog(ingredient);
                d.show();
            }
        });
    }

    private Dialog ingredientQuantityDialog(final Ingredient ingredient) {
        final Dialog tmpDialog = new Dialog(ActivityAddMeal.this);
        tmpDialog.setTitle("Add " + ingredient.toString());
        tmpDialog.setContentView(R.layout.add_meal_ingredient_dialog);
        Button b1 = (Button) tmpDialog.findViewById(R.id.button1);
        Button b2 = (Button) tmpDialog.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) tmpDialog.findViewById(R.id.numberPicker1);
        np.setMaxValue(3000);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(ActivityAddMeal.this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                EditText eTxt = (EditText) findViewById(R.id.quantity_units);
                String units = eTxt.toString();
                Log.d("Ingredient units:", units);
                tmpMealIngredients.put(ingredient, new Quantity(np.getValue(), units));
                refresh();
                tmpDialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpDialog.dismiss();
            }
        });
        return tmpDialog;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        //Log.i("value is",""+newVal);
    }

    public void refresh() {
        super.onRestart();
        prepare();
    }

}

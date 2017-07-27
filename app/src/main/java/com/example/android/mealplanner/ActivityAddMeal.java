package com.example.android.mealplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityAddMeal extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private final IngredientMap tmpMealIngredients = new IngredientMap();
    private final IngredientList tmpSpinner = new IngredientList();
    private Spinner mealTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        mealTypeSpinner = ((Spinner) findViewById(R.id.mealtype_spinner));
        mealTypeSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, Meal.MealType.list()));
        tmpSpinner.putAll(IngredientList.getMasterList());
        prepare();
    }

    public void prepare() {
        tmpSpinner.removeAll(tmpMealIngredients);
        ((Spinner) findViewById(R.id.ingredient_spinner)).setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tmpSpinner.list()));

        final ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, tmpMealIngredients.list()));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ingredient item = IngredientList.getMasterList().get((String) parent.getItemAtPosition(position));
                Toast.makeText(ActivityAddMeal.this, tmpMealIngredients.get(item).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Ingredient clickedIngredient = IngredientList.getMasterList().get((String) parent.getItemAtPosition(position));
                new AlertDialog.Builder(view.getContext())
                        .setMessage(String.format("Delete %s?", clickedIngredient.toString()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tmpMealIngredients.remove(clickedIngredient);
                                refresh();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).show();
                return false;
            }
        });

        Button addIngredientButton = (Button) findViewById(R.id.add_new_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient ingredient = IngredientList.getMasterList().get(((Spinner) findViewById(R.id.ingredient_spinner)).getSelectedItem().toString());
                final Dialog d = ingredientQuantityDialog(ingredient);
                d.show();
            }
        });

        Button addMealButton = (Button) findViewById(R.id.addbutton);
        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = ((EditText) findViewById(R.id.editText)).getText().toString();
                if (mealName == null) {

                } else {
                    Meal m = new Meal(mealName);
                    m.add(tmpMealIngredients);
                    m.setType(Meal.MealType.toValue((String) mealTypeSpinner.getSelectedItem()));
                    if (((CheckBox) findViewById(R.id.checkbox_advance)).isChecked()) {
                        m.inAdvance(true);
                    }
                    MealList.getMasterList().add(m);
                }
                finish();
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
        final EditText eTxt = (EditText) tmpDialog.findViewById(R.id.quantity_units);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tmpMealIngredients.put(ingredient, new Quantity(np.getValue(), eTxt.getText().toString()));
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

package com.beldin0.android.mealplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.example.android.mealplanner.R;

public class ActivityAddMeal extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private final IngredientMap tmpMealIngredients = new IngredientMap();
    private final IngredientList tmpUnusedIngredients = new IngredientList();
    private Spinner mealTypeSpinner;
    private Meal incomingMeal = null;
    private boolean changes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Add Ingredient");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mealTypeSpinner = ((Spinner) findViewById(R.id.mealtype_spinner));
        ArrayAdapter mtSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Meal.MealType.list());
        mealTypeSpinner.setAdapter(mtSpinnerAdapter);
        tmpUnusedIngredients.putAll(IngredientList.getMasterList());
        if (ObjectBinder.hasObj()) {
            incomingMeal = (Meal) ObjectBinder.getObj();
            mealTypeSpinner.setSelection(mtSpinnerAdapter.getPosition(incomingMeal.getType()));
            tmpMealIngredients.putAll(incomingMeal.getIngredients());
            ((CheckBox) findViewById(R.id.checkbox_advance)).setChecked(incomingMeal.inAdvance());
            ((EditText) findViewById(R.id.editText)).setText(incomingMeal.toString());
            ObjectBinder.clear();
        }
        prepare();
    }

    public void prepare() {
        tmpUnusedIngredients.removeAll(tmpMealIngredients);
        ArrayAdapter ingredientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tmpUnusedIngredients.list());
        ingredientAdapter.add("Add New...");
        Spinner ingredientSpinner = ((Spinner) findViewById(R.id.ingredient_spinner));
        ingredientSpinner.setAdapter(ingredientAdapter);
        if (ObjectBinder.hasObj()) {
            ingredientSpinner.setSelection(ingredientAdapter.getPosition(ObjectBinder.getObj()));
            ObjectBinder.clear();
            addingIngredient();
        }

        ingredientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItem().equals("Add New...")) {
                    Intent ingredientsIntent = new Intent(ActivityAddMeal.this, ActivityAddIngredient.class);
                    startActivityForResult(ingredientsIntent, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tmpMealIngredients.list()));
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
                                changes = true;
                                tmpUnusedIngredients.put(clickedIngredient);
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
                addingIngredient();
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
                    if (!(incomingMeal == null)) {
                        MealList.getMasterList().remove(incomingMeal);
                    }
                    MealList.getMasterList().add(m);
                }
                finish();
            }
        });
    }

    private void addingIngredient() {
        changes = true;
        Ingredient ingredient = IngredientList.getMasterList().get(((Spinner) findViewById(R.id.ingredient_spinner)).getSelectedItem().toString());
        final Dialog d = ingredientQuantityDialog(ingredient);
        d.show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ObjectBinder.hasObj()) {
            tmpUnusedIngredients.put((Ingredient) ObjectBinder.getObj());
            changes = true;
            refresh();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (changes) {
            changes = false;
            Toast.makeText(this, "Press again to discard changes", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            onBackPressed();
            return true;
        }
    }

}

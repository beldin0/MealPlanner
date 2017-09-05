package com.beldin0.android.mealplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
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

import java.util.Arrays;

public class ActivityAddMeal extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private final ShoppingList tmpMealIngredients = new ShoppingList();
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
        ArrayAdapter<String> mtSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(Meal.MealType.list()));
        mealTypeSpinner.setAdapter(mtSpinnerAdapter);
        tmpUnusedIngredients.putAll(IngredientList.getMasterList());
        if (ObjectBinder.hasObj()) {
            try {
                incomingMeal = (Meal) ObjectBinder.getObj();
                if (incomingMeal.getType() != null) {
                    mealTypeSpinner.setSelection(mtSpinnerAdapter.getPosition(incomingMeal.getType().toString()));
                }
                tmpMealIngredients.putAll(incomingMeal.getIngredients());
                ((EditText) findViewById(R.id.cooktime)).setText(String.valueOf(incomingMeal.getCookTime()));
                ((CheckBox) findViewById(R.id.checkbox_advance)).setChecked(incomingMeal.inAdvance());
                ((EditText) findViewById(R.id.editText)).setText(incomingMeal.toString());
            } catch (ClassCastException e) {

            } finally {
                ObjectBinder.clear();
            }
        }
        prepare();
    }

    public void prepare() {
        tmpUnusedIngredients.removeAll(tmpMealIngredients);
        final ArrayAdapter ingredientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tmpUnusedIngredients.list());
        ingredientAdapter.add("Add New...");
        Spinner ingredientSpinner = ((Spinner) findViewById(R.id.ingredient_spinner));
        ingredientSpinner.setAdapter(ingredientAdapter);
        if (ObjectBinder.hasObj()) {
            ingredientSpinner.setSelection(ingredientAdapter.getPosition(ObjectBinder.getObj()));
            ObjectBinder.clear();
            addingIngredient(IngredientList.getMasterList().get(((Spinner) findViewById(R.id.ingredient_spinner)).getSelectedItem().toString()));
        }

        ingredientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getSelectedItem().toString().equals("Add New...")) {
                    Intent ingredientsIntent = new Intent(ActivityAddMeal.this, ActivityAddIngredient.class);
                    startActivityForResult(ingredientsIntent, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ListView list = (ListView) findViewById(R.id.list);
        final ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(tmpMealIngredients);
        list.setAdapter(shoppingListAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Pair clickedIngredient = (Pair) parent.getItemAtPosition(position);
                Ingredient item = (Ingredient) clickedIngredient.first;
                addingIngredient(item);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                final Pair clickedIngredient = (Pair) parent.getItemAtPosition(position);
                new AlertDialog.Builder(view.getContext())
                        .setMessage(String.format("Delete %s?", clickedIngredient.first.toString()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                changes = true;
                                tmpUnusedIngredients.put((Ingredient) clickedIngredient.first);
                                String toBeRemoved = shoppingListAdapter.getKeyAt(position);
                                if (ActivityMain.LOGGING) Log.d("DeleteMI", toBeRemoved);
                                tmpMealIngredients.remove(toBeRemoved);
                                ingredientAdapter.notifyDataSetChanged();
                                shoppingListAdapter.notifyDataSetChanged();
                                refresh();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });

        Button addIngredientButton = (Button) findViewById(R.id.add_new_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingIngredient(IngredientList.getMasterList().get(((Spinner) findViewById(R.id.ingredient_spinner)).getSelectedItem().toString()));
            }
        });

        FloatingActionButton addMealButton = (FloatingActionButton) findViewById(R.id.addbutton);
        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = ((EditText) findViewById(R.id.editText)).getText().toString();
                if (!mealName.equals("")) {
                    Meal m = new Meal.MealBuilder()
                            .setName(mealName)
                            .setType(Meal.MealType.toValue((String) mealTypeSpinner.getSelectedItem()))
                            .setCookTime(Integer.parseInt(((EditText) findViewById(R.id.cooktime)).getText().toString()))
                            .setInAdvance(((CheckBox) findViewById(R.id.checkbox_advance)).isChecked())
                            .create();
                    m.add(tmpMealIngredients);

                    if (!(incomingMeal == null)) {
                        MealList.getMasterList().remove(incomingMeal);
                    }
                    MealList.getMasterList().add(m);
                }
                finish();
            }
        });
    }

    private void addingIngredient(Ingredient ingredient) {
        changes = true;
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
        eTxt.setText(ingredient.getDefaultUnit());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tmpMealIngredients.add(ingredient, new Quantity(np.getValue(), eTxt.getText().toString()));
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

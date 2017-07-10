package com.example.android.mealplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ActivityAddMeal extends AppCompatActivity {

    private final IngredientMap tmpMealIngredients = new IngredientMap();
    private final IngredientList tmpSpinner = new IngredientList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        ((Spinner) findViewById(R.id.mealtype_spinner)).setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, Meal.MealType.list()));

        tmpSpinner.putAll(ActivityMain.ingredientList);
        ((Spinner) findViewById(R.id.ingredient_spinner)).setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, tmpSpinner.list()));

        Button addIngredientButton = (Button) findViewById(R.id.add_new_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredient ingredient = ActivityMain.ingredientList.get(((Spinner) findViewById(R.id.location_spinner)).getSelectedItem().toString());

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Select Quantity")
                        .setMessage("")
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .show();
            }
        });

    }
}

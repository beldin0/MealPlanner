package com.example.android.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMain extends AppCompatActivity {

    static DataManager dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dr = new DataManager(this);

        IngredientList.getMasterList().putAll(ArrayFiller.getIngredients());

        MealList.getMasterList().addAll(ArrayFiller.getMeals());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView plan = (TextView)findViewById(R.id.plan);
        plan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent planIntent = new Intent(ActivityMain.this, ActivityGenerate.class);
                startActivity(planIntent);
            }});

        TextView ingredients = (TextView)findViewById(R.id.ingredients);
        ingredients.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent ingredientsIntent = new Intent(ActivityMain.this, ActivityIngredients.class);
                startActivity(ingredientsIntent);
            }});

        TextView meals = (TextView)findViewById(R.id.meals);
        meals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mealsIntent = new Intent(ActivityMain.this, ActivityMeals.class);
                startActivity(mealsIntent);
            }});

        Button resetBtn = (Button) findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.reset();
                IngredientList.getMasterList().putAll(ArrayFiller.getIngredients());
                MealList.getMasterList().addAll(ArrayFiller.getMeals());
            }
        });


    }
}

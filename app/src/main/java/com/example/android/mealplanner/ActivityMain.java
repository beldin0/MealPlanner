package com.example.android.mealplanner;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ActivityMain extends AppCompatActivity {

    public static IngredientList ingredientList;
    public static ArrayList<Meal> mealList;
    public static Day[] week;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DataRetriever dr = new DataRetriever(this);
        db = dr.getReadableDatabase();

        ingredientList = ArrayFiller.getIngredients();

        mealList = new ArrayList<>();
        ArrayFiller.getMeals();
        Collections.sort(mealList);

        week = Day.makeWeek();
        mealList = ArrayFiller.getMeals();

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


    }
}

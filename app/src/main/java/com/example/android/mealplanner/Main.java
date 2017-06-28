package com.example.android.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends AppCompatActivity {

    public static ArrayList<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ingredientList = new ArrayList<>();
        Database.getIngredients();
        Collections.sort(ingredientList);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView ingredients = (TextView)findViewById(R.id.ingredients);
        ingredients.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent ingredientsIntent = new Intent(Main.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
            }});

    }
}

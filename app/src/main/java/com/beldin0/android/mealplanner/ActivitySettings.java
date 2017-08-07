package com.beldin0.android.mealplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.android.mealplanner.R;

import static com.beldin0.android.mealplanner.ActivityMain.dr;

public class ActivitySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Button resetBtn = (Button) findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientList.getMasterList().clear();
                MealList.getMasterList().clear();
                dr.reset();
                IngredientList.getMasterList().putAll(ArrayFiller.getIngredients(ActivitySettings.this));
                MealList.getMasterList().addAll(ArrayFiller.getMeals(ActivitySettings.this));
            }
        });

        Button saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONHelper.saveJSON("ingredients", IngredientList.getMasterList().toJSON());
                JSONHelper.saveJSON("meals", MealList.getMasterList().toJSON());
            }
        });


        Button loadBtn = (Button) findViewById(R.id.load);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientList.getMasterList().clear();
                MealList.getMasterList().clear();
                dr.reset();
                ArrayFiller.fillFromJSON(IngredientList.getMasterList(), JSONHelper.loadJSON("ingredients"));
                ArrayFiller.fillFromJSON(MealList.getMasterList(), JSONHelper.loadJSON("meals"));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

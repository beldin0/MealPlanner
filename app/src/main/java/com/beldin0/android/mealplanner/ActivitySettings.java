package com.beldin0.android.mealplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.android.mealplanner.R;

import static com.beldin0.android.mealplanner.ActivityMain.PREFS_NAME;
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


        Spinner daySpinner = (Spinner) findViewById(R.id.day_spinner);
        daySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Day.WEEK_DAYS));
        do {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            int day = settings.getInt("startday", 0);
            daySpinner.setSelection(day);
        } while (false);


        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("startday", position);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button resetBtn = (Button) findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("meals");
                editor.apply();
                IngredientList.getMasterList().clear();
                MealList.getMasterList().clear();
                dr.reset();
                IngredientList.getMasterList().putAll(ArrayFiller.getIngredients(ActivitySettings.this));
                MealList.getMasterList().addAll(ArrayFiller.getMeals(ActivitySettings.this));
                ActivitySettings.this.finish();
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
                ActivitySettings.this.finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

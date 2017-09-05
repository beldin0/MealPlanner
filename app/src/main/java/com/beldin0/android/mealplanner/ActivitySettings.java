package com.beldin0.android.mealplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.android.mealplanner.R;

import org.json.JSONException;

import static com.beldin0.android.mealplanner.ActivityMain.PREFS_NAME;

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
        daySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Week.WEEK_DAYS));
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
                if (ActivityMain.LOGGING) Log.d("ActivitySettings:", "Resetting to defaults");
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("meals");
                editor.apply();

                DataManager.getInstance().deleteUserData();
                IngredientList.getMasterList().clear();
                IngredientList.getMasterList().putAll(DataManager.getInstance().getIngredients());
                MealList.getMasterList().clear();
                MealList.getMasterList().addAll(DataManager.getInstance().getMeals());

                ActivitySettings.this.finish();
            }
        });

        Button saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityMain.LOGGING) Log.d("ActivitySettings:", "Saving to file");
                DataManager.getInstance().exportJSON("ingredients", IngredientList.getMasterList().toJSON());
                DataManager.getInstance().exportJSON("meals", MealList.getMasterList().toJSON());
            }
        });


        Button loadBtn = (Button) findViewById(R.id.load);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityMain.LOGGING) Log.d("ActivitySettings:", "Loading from saved file");
                DataManager dm = DataManager.getInstance();
                try {
                    String jsonString = dm.importJSON("ingredients");

                    IngredientList.getMasterList().clear();
                    IngredientList.getMasterList().putAllFromJSON(jsonString);
                    dm.saveIngredients(jsonString);

                    jsonString = dm.importJSON("meals");
                    MealList.getMasterList().clear();
                    MealList.getMasterList().addAllFromJSON(jsonString);
                    dm.saveMeals(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

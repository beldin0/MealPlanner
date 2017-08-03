package com.beldin0.android.mealplanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.mealplanner.R;

public class ActivityMain extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    static DataManager dr;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dr = new DataManager(this);

        IngredientList.getMasterList().putAll(ArrayFiller.getIngredients(ActivityMain.this));

        MealList.getMasterList().addAll(ArrayFiller.getMeals(ActivityMain.this));

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
                IngredientList.getMasterList().clear();
                MealList.getMasterList().clear();
                dr.reset();
                IngredientList.getMasterList().putAll(ArrayFiller.getIngredients(ActivityMain.this));
                MealList.getMasterList().addAll(ArrayFiller.getMeals(ActivityMain.this));
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

}

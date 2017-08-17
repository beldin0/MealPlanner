package com.beldin0.android.mealplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.android.mealplanner.R;

import static com.beldin0.android.mealplanner.ActivityMain.PREFS_NAME;

public class ActivityViewPlans extends AppCompatActivity {
    private MealSelector ms = new MealSelector();
    private DayAdapter adapter;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Day.setWeekStart(settings.getInt("startday", 0));

        generate();

        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Meal Plan");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fbtn = (FloatingActionButton) findViewById(R.id.fab);
        fbtn.setVisibility(View.GONE);

        adapter = new DayAdapter(this, Day.getMasterWeek());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void generate() {

        String mealnames = (settings.getString("meals", ""));
        String[] mealNames = mealnames.split("%");
        int c = 0;
        for (Day d : Day.getMasterWeek()) {
            d.setMeal(MealList.getMasterList().get(mealNames[c++]));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

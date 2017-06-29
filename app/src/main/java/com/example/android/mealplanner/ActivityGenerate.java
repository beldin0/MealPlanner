package com.example.android.mealplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityGenerate extends AppCompatActivity {
    private MealSelector ms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ms = new MealSelector();
        for (Day d : ActivityMain.week) {
            d.setMeal(ms.get());
        }
        prepare();
    }

    private void prepare() {
        setContentView(R.layout.list_main);
        DayAdapter adapter = new DayAdapter(this, ActivityMain.week);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        prepare();
    }
}

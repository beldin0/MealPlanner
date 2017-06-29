package com.example.android.mealplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityMeals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepare();
    }

    private void prepare() {
        setContentView(R.layout.list_main);
        MealAdapter adapter = new MealAdapter(this, ActivityMain.mealList);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Meal clickedMeal = ActivityMain.mealList.get(position);
                Toast.makeText(ActivityMeals.this, clickedMeal.getInfo(), Toast.LENGTH_SHORT).show();
            }
        });

        Button btn = (Button) findViewById(R.id.button_new);
        btn.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityMeals.this, "NOT YET IMPLEMENTED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        prepare();
    }
}

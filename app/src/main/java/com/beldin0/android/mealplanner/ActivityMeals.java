package com.beldin0.android.mealplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.mealplanner.R;

public class ActivityMeals extends AppCompatActivity {

    private ArrayAdapter<Meal> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the view
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Meals");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set up the list
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MealList.getMasterList());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);


        // Set up listeners on list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Meal clickedMeal = (Meal) adapterView.getItemAtPosition(position);
                ObjectBinder.setObj(clickedMeal);
                Intent mealsIntent = new Intent(ActivityMeals.this, ActivityAddMeal.class);
                startActivity(mealsIntent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Meal clickedMeal = (Meal) parent.getItemAtPosition(position);

                new AlertDialog.Builder(view.getContext())
                        .setMessage(String.format("Delete %s?", clickedMeal.toString()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MealList.getMasterList().remove(clickedMeal);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });


        // Set up listener on button
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        btn.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent planIntent = new Intent(ActivityMeals.this, ActivityAddMeal.class);
                startActivity(planIntent);
            }
        });
    }

    public void refresh() {
        onRestart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        DataManager.getInstance().saveMeals(MealList.getMasterList().toJSON());
        onBackPressed();
        return true;
    }
}

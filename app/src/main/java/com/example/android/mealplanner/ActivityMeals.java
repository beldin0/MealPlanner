package com.example.android.mealplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityMeals extends AppCompatActivity {

    public static Meal clickedMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepare();
    }

    private void prepare() {
        setContentView(R.layout.list_main);
        final MealAdapter adapter = new MealAdapter(this, ActivityMain.mealList);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                clickedMeal = ActivityMain.mealList.get(position);
                Toast.makeText(ActivityMeals.this, clickedMeal.getInfo(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clickedMeal = adapter.getItem(position);

                new AlertDialog.Builder(view.getContext())
                        .setMessage(String.format("Delete %s?", clickedMeal.toString()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    ActivityMain.mealList.remove(clickedMeal);
                                    refresh();
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

        Button btn = (Button) findViewById(R.id.button_new);
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
        prepare();
    }
}

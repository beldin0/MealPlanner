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
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.mealplanner.R;

public class ActivityIngredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Ingredients");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prepare();
    }

    private void prepare() {

        final IngredientAdapter adapter = new IngredientAdapter(ActivityIngredients.this, IngredientList.getMasterList().list());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Ingredient clickedIngredient = adapter.getItem(position);
                ObjectBinder.setObj(clickedIngredient);
                Intent ingredientsIntent = new Intent(ActivityIngredients.this, ActivityAddIngredient.class);
                startActivity(ingredientsIntent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Ingredient clickedIngredient = adapter.getItem(position);

                new AlertDialog.Builder(view.getContext())
                        .setMessage(String.format("Delete %s?", clickedIngredient.toString()))
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (clickedIngredient.isUsed()) {
                                    Toast.makeText(ActivityIngredients.this, "You cannot delete an ingredient\nthat is part of a meal.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    IngredientList.getMasterList().remove(clickedIngredient);
                                    refresh();
                                }
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

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        btn.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIngredientIntent = new Intent(ActivityIngredients.this, ActivityAddIngredient.class);
                startActivity(addIngredientIntent);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
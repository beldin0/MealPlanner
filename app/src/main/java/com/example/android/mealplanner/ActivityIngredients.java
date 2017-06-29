package com.example.android.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityIngredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepare();
    }

    private void prepare() {
        setContentView(R.layout.list_main);
        IngredientAdapter adapter = new IngredientAdapter(this, ActivityMain.ingredientList);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Ingredient clickedIngredient = ActivityMain.ingredientList.get(position);
                Toast.makeText(ActivityIngredients.this, clickedIngredient.getInfo(), Toast.LENGTH_SHORT).show();
            }
        });

        Button btn = (Button) findViewById(R.id.button_new);
        btn.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIngredientIntent = new Intent(ActivityIngredients.this, ActivityAddIngredient.class);
                startActivity(addIngredientIntent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        prepare();
    }

}
package com.example.android.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

        Button btn = (Button) findViewById(R.id.button_new);
        btn.setText("View shopping for this plan");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent planIntent = new Intent(ActivityGenerate.this, ActivityShoppingList.class);
                startActivity(planIntent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        prepare();
    }
}

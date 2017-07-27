package com.example.android.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ActivityGenerate extends AppCompatActivity {
    private MealSelector ms;
    private boolean already = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generate();
        prepare();
    }

    private void prepare() {
        setContentView(R.layout.list_main);
        DayAdapter adapter = new DayAdapter(this, Day.getMasterWeek());
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

        Button btn1 = (Button) findViewById(R.id.button_retry);
        btn1.setVisibility(View.VISIBLE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                already = false;
                generate();
                onRestart();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        prepare();
    }

    private void generate() {
        if (Day.getMasterWeek()[0].getMeal() == null) {
            already = false;
        }
        if (!already) {
            ms = new MealSelector();
            for (Day d : Day.getMasterWeek()) {
                d.setMeal(ms.get());
            }
            already = true;
        }
    }
}

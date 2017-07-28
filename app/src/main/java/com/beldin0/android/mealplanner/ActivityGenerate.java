package com.beldin0.android.mealplanner;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.mealplanner.R;

public class ActivityGenerate extends AppCompatActivity {
    private MealSelector ms = new MealSelector();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ms.refresh();
                for (Day d : Day.getMasterWeek()) {
                    ms.remove(d.getMeal());
                }
                Day.getMasterWeek()[position].setMeal(ms.get());
                prepare();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog d = customMealDialog(position);
                d.show();
                return false;
            }
        });

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
            ms.refresh();
            for (Day d : Day.getMasterWeek()) {
                d.setMeal(ms.get());
            }
            already = true;
        }
    }

    private Dialog customMealDialog(final int i) {
        final Dialog tmpDialog = new Dialog(ActivityGenerate.this);
        tmpDialog.setTitle("Custom Meal");
        tmpDialog.setContentView(R.layout.custom_meal);
        Button b1 = (Button) tmpDialog.findViewById(R.id.button_ok);
        Button b2 = (Button) tmpDialog.findViewById(R.id.button_cancel);
        final EditText eTxt = (EditText) tmpDialog.findViewById(R.id.etxt_custom_meal);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Day.getMasterWeek()[i].setMeal(new Meal("[" + eTxt.getText().toString() + "]"));
                prepare();
                tmpDialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpDialog.dismiss();
            }
        });
        return tmpDialog;
    }
}

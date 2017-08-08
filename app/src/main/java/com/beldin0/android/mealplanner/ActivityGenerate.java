package com.beldin0.android.mealplanner;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.mealplanner.R;

import static com.beldin0.android.mealplanner.ActivityMain.PREFS_NAME;

public class ActivityGenerate extends AppCompatActivity {
    private MealSelector ms = new MealSelector();
    private DayAdapter adapter;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Day.setWeekStart(settings.getInt("startday", 0));

        generate(settings.contains("meals"));

        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Meal Plan");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fbtn = (FloatingActionButton) findViewById(R.id.fab);
        fbtn.setVisibility(View.GONE);

        Button btn1 = (Button) findViewById(R.id.button_retry);
        btn1.setVisibility(View.VISIBLE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate(false);
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new DayAdapter(this, Day.getMasterWeek());
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
                storeMeals();
                adapter.notifyDataSetChanged();
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


    }

    private void storeMeals() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("meals", Day.getMealsAsString());
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void generate(boolean useMealsFromPreferences) {

        if (useMealsFromPreferences) {
            String mealnames = (settings.getString("meals", ""));
            String[] mealNames = mealnames.split("%");
            int c = 0;
            for (Day d : Day.getMasterWeek()) {
                d.setMeal(MealList.getMasterList().get(mealNames[c++]));
            }
        } else {
            ms.refresh();
            for (Day d : Day.getMasterWeek()) {
                d.setMeal(ms.get());
            }
            storeMeals();
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

                if (MealList.getMasterList().contains(eTxt.getText().toString())) {
                    Day.getMasterWeek()[i].setMeal(MealList.getMasterList().get(eTxt.getText().toString()));
                } else {
                    Day.getMasterWeek()[i].setMeal(new Meal("[" + eTxt.getText().toString() + "]"));
                }
                storeMeals();
                adapter.notifyDataSetChanged();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

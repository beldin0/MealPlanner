package com.beldin0.android.mealplanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.mealplanner.R;

import java.util.HashMap;
import java.util.Map;

import static android.R.drawable.ic_media_play;
import static com.beldin0.android.mealplanner.ActivityMain.PREFS_NAME;
import static com.beldin0.android.mealplanner.Week.getMasterWeekStartDate;

public class ActivityGenerate extends AppCompatActivity {
    private MealSelector ms = new MealSelector();
    private WeekAdapter adapter;

    private HashMap<Integer, Meal.Options> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        options = (HashMap) ObjectBinder.getObj();

        generate();

        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Meal Plan " + getMasterWeekStartDate().replace("_", " "));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fbtn = (FloatingActionButton) findViewById(R.id.fab);
        fbtn.setImageResource(ic_media_play);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeMeals();
                ObjectBinder.clear();
                Intent intent = new Intent();
                intent.putExtra("GENERATED", true);
                setResult(2, intent);
                finish();
            }
        });

        Button btn1 = (Button) findViewById(R.id.button_retry);
        btn1.setVisibility(View.VISIBLE);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generate();
                adapter.notifyDataSetChanged();
            }
        });

        adapter = new WeekAdapter(this, Week.getMasterWeek());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ms.refresh();
                for (Week.Day d : Week.getMasterWeek()) {
                    ms.remove(d.getMeal());
                }
                if (options == null) {
                    Week.getMasterWeek()[position].setMeal(ms.get());
                } else {
                    Week.getMasterWeek()[position].setMeal(ms.get(options.get(position)));
                }
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
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        DataManager.getInstance().savePlan(getMasterWeekStartDate(), Week.masterWeekToJSON());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("meals", Week.getMealsAsString());
        editor.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void generate() {
        Week.clear();
        ms.refresh();

        if (!(options == null)) {
            for (Map.Entry<Integer, Meal.Options> entry : options.entrySet()) {
                Week.Day d = Week.getMasterWeek()[entry.getKey()];
                if (ActivityMain.LOGGING) Log.d("Setting ", d.toString());
                d.setMeal(ms.get(entry.getValue()));
            }
        }

        for (Week.Day d : Week.getMasterWeek()) {
            if (d.getMeal() == null) {
                d.setMeal(ms.get());
            }
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
                    Week.getMasterWeek()[i].setMeal(MealList.getMasterList().get(eTxt.getText().toString()));
                } else {
                    Week.getMasterWeek()[i].setMeal(new Meal.MealBuilder().setName("[" + eTxt.getText().toString() + "]").create());
                }
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
        ObjectBinder.clear();
        onBackPressed();
        return true;
    }
}

package com.beldin0.android.mealplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.mealplanner.R;

import java.util.HashMap;

import static com.beldin0.android.mealplanner.ActivityMain.PREFS_NAME;

public class ActivityMealSelection extends AppCompatActivity {

    private SharedPreferences settings;
    private HashMap<Integer, MealOptions> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_selection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Meal Plan Settings");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Week.setWeekStart(settings.getInt("startday", 0));

        final MealSettingsListAdapter selectionAdapter = new MealSettingsListAdapter(this, Week.getMasterWeek());

        final ExpandableListView eListView = (ExpandableListView) findViewById(R.id.expandable);
        eListView.setAdapter(selectionAdapter);

        FloatingActionButton fbtn = (FloatingActionButton) findViewById(R.id.fab);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options = new HashMap<>();
                for (int i = 0; i < selectionAdapter.getGroupCount(); i++) {
                    if (eListView.isGroupExpanded(i)) {
                        View view = (eListView.findViewWithTag(i));
                        CheckBox chkType = (CheckBox) (view.findViewById(R.id.chk_Type));
                        TextView spinType = (TextView) (view.findViewById(R.id.spin_Type));
                        CheckBox chkAdvance = (CheckBox) (view.findViewById(R.id.chk_Advance));
                        CheckBox chkMax = (CheckBox) (view.findViewById(R.id.chk_Max));
                        SeekBar seekMax = (SeekBar) (view.findViewById(R.id.seek_Max));

                        MealOptions m = new MealOptions();
                        if (chkType.isChecked()) {
                            m.setType(spinType.getText().toString());
                        }
                        m.setInAdvance(chkAdvance.isChecked());
                        if (chkMax.isChecked()) {
                            m.setTime(seekMax.getProgress());
                        }
                        options.put(i, m);
                    }
                }
                ObjectBinder.setObj(options);
                Intent intent = new Intent(ActivityMealSelection.this, ActivityGenerate.class);
                startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            boolean newmeal;
            try {
                newmeal = data.getBooleanExtra("GENERATED", true);
                if (newmeal) {
                    finish();
                }
            } catch (NullPointerException e) {
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        ObjectBinder.clear();
        onBackPressed();
        return true;
    }

}

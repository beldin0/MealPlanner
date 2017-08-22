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
import android.widget.Toast;

import com.example.android.mealplanner.R;

import java.util.Arrays;
import java.util.List;

public class ActivityListPlans extends AppCompatActivity {

    List<String> plans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Select a plan to view");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        btn.setVisibility(View.GONE);

        final ListView listView = (ListView) findViewById(R.id.list);

        getPlans();

        if (plans.size() == 0) {
            Toast.makeText(ActivityListPlans.this, "No saved plans!", Toast.LENGTH_SHORT).show();
        } else {
            listView.setAdapter(new ArrayAdapter<>(this, R.layout.simple_textview, plans));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String filename = (String) listView.getAdapter().getItem(position);
                    ObjectBinder.setObj(Week.buildWeekFromJSON(JSONHelper.loadJSON(ActivityListPlans.this, filename)));
                    Intent intent = new Intent(ActivityListPlans.this, ActivityViewPlan.class);
                    startActivity(intent);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    final String filename = (String) listView.getAdapter().getItem(position);
                    new AlertDialog.Builder(view.getContext())
                            .setMessage(String.format("Delete %s?", filename))
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ActivityListPlans.this.deleteFile(filename);
                                    getPlans();
                                    listView.setAdapter(new ArrayAdapter<>(ActivityListPlans.this, R.layout.simple_textview, plans));
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
        }
    }

    private void getPlans() {
        try {
            plans = Arrays.asList(ActivityListPlans.this.fileList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

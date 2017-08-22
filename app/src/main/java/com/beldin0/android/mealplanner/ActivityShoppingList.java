package com.beldin0.android.mealplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.android.mealplanner.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import static com.beldin0.android.mealplanner.ActivityMain.PREFS_NAME;

public class ActivityShoppingList extends AppCompatActivity {

    ShoppingList sl = new ShoppingList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Week.exists()) {
            fillEmptyWeek();
        }

        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Shopping List");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.fab);
        btn.setVisibility(View.GONE);

        sl.generate(Week.getMasterWeek());

        ShoppingListAdapter adapter = new ShoppingListAdapter(sl);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        save(Week.getWeekAsString() + sl.outputToString());
    }

    public void save(String out) {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
        File file = new File(path, "shoppinglist.txt");

        try {
            path.mkdirs();
            OutputStream os = new FileOutputStream(file);
            Writer w = new OutputStreamWriter(os);
            w.write(out);
            w.close();
            os.close();

        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);

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

    private void fillEmptyWeek() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String mealnames = (settings.getString("meals", ""));
        String[] mealNames = mealnames.split("%");
        int c = 0;
        for (Week.Day d : Week.getMasterWeek()) {
            d.setMeal(MealList.getMasterList().get(mealNames[c++]));
        }
    }
}

package com.beldin0.android.mealplanner;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.mealplanner.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ActivityShoppingList extends AppCompatActivity {

    ShoppingList sl = new ShoppingList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);
        sl.generate(Day.getMasterWeek());

        ShoppingListAdapter adapter = new ShoppingListAdapter(sl);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.button_new);
        btn.setVisibility(View.GONE);

        save(Day.getWeekAsString() + sl.outputToString());
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
}

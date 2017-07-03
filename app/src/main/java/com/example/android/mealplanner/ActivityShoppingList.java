package com.example.android.mealplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ActivityShoppingList extends AppCompatActivity {

    ShoppingList sl = new ShoppingList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);
        sl.generate(ActivityMain.week);

        ShoppingListAdapter adapter = new ShoppingListAdapter(sl);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        Button btn = (Button) findViewById(R.id.button_new);
        btn.setVisibility(View.GONE);

    }
}

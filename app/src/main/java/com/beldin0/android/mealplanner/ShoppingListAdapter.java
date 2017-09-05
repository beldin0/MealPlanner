package com.beldin0.android.mealplanner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.mealplanner.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingListAdapter extends BaseAdapter {

    private HashMap<String, Pair<Ingredient, Quantity>> map;
    private ArrayList<String> mKeys = new ArrayList<>();

    public ShoppingListAdapter(HashMap<String, Pair<Ingredient, Quantity>> map) {
        this.map = map;
        generateKeys();
    }

    private void generateKeys() {
        for (Pair p : map.values()) {
            mKeys.add(p.first.toString());
        }
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Pair getItem(int position) {
        return map.get(mKeys.get(position));
    }

    public String getKeyAt(int position) {
        return mKeys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        View listItemView;

        // if no view is passed in to reuse, inflate a new view from the XML layout file
        if (convertView == null) {
            listItemView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.list_item2, parent, false);
        } else {
            listItemView = convertView;
        }

        // Get the details of the current word from the words ArrayList
        final Pair currentIngredient = getItem(position);
        if (!(currentIngredient == null)) {
            // Set the text of TextView
            TextView textView = (TextView) listItemView.findViewById(R.id.day_name);
            textView.setText(currentIngredient.first.toString());

            TextView textView2 = (TextView) listItemView.findViewById(R.id.day_meal);
            textView2.setText(currentIngredient.second.toString());
        }
        return listItemView;
    }

}

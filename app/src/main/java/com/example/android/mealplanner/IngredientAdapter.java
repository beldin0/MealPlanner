package com.example.android.mealplanner;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class IngredientAdapter extends ArrayAdapter<String> {

    private String[] mKeys;

    public IngredientAdapter (Activity context, IngredientList ingredients)
    {
        super(context, 0, ingredients.keySet().toArray(new String[ingredients.size()]));
        mKeys = ingredients.keySet().toArray(new String[ingredients.size()]);
        Arrays.sort(mKeys);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        View listItemView;

        // if no view is passed in to reuse, inflate a new view from the XML layout file
        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        } else {
            listItemView = convertView;
        }


        // Get the details of the current word from the words ArrayList
        String currentIngredient = mKeys[position];

        // Set the text of TextView
        TextView textView = (TextView) listItemView.findViewById(R.id.text_view);
        textView.setText(currentIngredient);

        return listItemView;
    }

    public Ingredient getIngredient(int position) {
        return ActivityMain.ingredientList.get(mKeys[position]);
    }
}

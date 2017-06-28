package com.example.android.mealplanner;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    public IngredientAdapter (Activity context, ArrayList<Ingredient> ingredients)
    {
        super(context, 0, ingredients);
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

//        View layout = listItemView.findViewById(R.id.behind);
//        int color = ContextCompat.getColor(getContext(), bg_color);
//        layout.setBackgroundColor(color);

        // Get the details of the current word from the words ArrayList
        final Ingredient currentIngredient = getItem(position);

        // Set the text of TextView
        TextView textView = (TextView) listItemView.findViewById(R.id.text_view);
        textView.setText(currentIngredient.toString());

//        ImageView imageView = (ImageView) listItemView.findViewById(R.id.icon_image);
//        if (currentWord.hasImage()) {
//            imageView.setImageResource(currentWord.getImage_src());
//            imageView.setVisibility(View.VISIBLE);
//        } else {
//            imageView.setVisibility(View.GONE);
//        }

        return listItemView;
    }
}

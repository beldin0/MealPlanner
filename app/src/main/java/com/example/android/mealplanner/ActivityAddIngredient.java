package com.example.android.mealplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

public class ActivityAddIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        ((CheckBox)findViewById(R.id.checkbox_protein)).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((CheckBox)findViewById(R.id.checkbox_carb)).setChecked(false);
                }
            }
        });

        ((CheckBox)findViewById(R.id.checkbox_carb)).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((CheckBox)findViewById(R.id.checkbox_protein)).setChecked(false);
                }
            }
        });

        ((Spinner)findViewById(R.id.location_spinner)).setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, Ingredient.Location.list()));

        findViewById(R.id.submit_new_ingredient).setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient.add(
                        ActivityMain.ingredientList,
                        ((EditText)findViewById(R.id.editText)).getText().toString(),
                        Ingredient.Location.toValue(((Spinner)findViewById(R.id.location_spinner)).getSelectedItem().toString()),
                        ((CheckBox)findViewById(R.id.checkbox_carb)).isChecked(),
                        ((CheckBox)findViewById(R.id.checkbox_protein)).isChecked()
                        );
                finish();
            }
        });

    }


}

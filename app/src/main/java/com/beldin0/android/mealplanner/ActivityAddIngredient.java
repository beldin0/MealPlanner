package com.beldin0.android.mealplanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.mealplanner.R;

public class ActivityAddIngredient extends Activity {

    private Ingredient incomingIngredient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        ArrayAdapter<String> locSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Ingredient.Location.list());
        Spinner locationSpinner = ((Spinner) findViewById(R.id.location_spinner));
        locationSpinner.setAdapter(locSpinnerAdapter);

        if (ObjectBinder.hasObj()) {
            incomingIngredient = (Ingredient) ObjectBinder.getObj();
            locationSpinner.setSelection(locSpinnerAdapter.getPosition(incomingIngredient.location().toString()));
            ((CheckBox) findViewById(R.id.checkbox_protein)).setChecked(incomingIngredient.isProtein());
            ((CheckBox) findViewById(R.id.checkbox_carb)).setChecked(incomingIngredient.isCarb());
            ((EditText) findViewById(R.id.editText)).setText(incomingIngredient.toString());
            ObjectBinder.clear();
        }

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

        findViewById(R.id.submit_new_ingredient).setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(incomingIngredient == null)) {
                    IngredientList.getMasterList().remove(incomingIngredient);
                }

                Ingredient i = new Ingredient(
                        ((EditText)findViewById(R.id.editText)).getText().toString(),
                        Ingredient.Location.toValue(((Spinner)findViewById(R.id.location_spinner)).getSelectedItem().toString()),
                        ((CheckBox)findViewById(R.id.checkbox_carb)).isChecked(),
                        ((CheckBox) findViewById(R.id.checkbox_protein)).isChecked());
                IngredientList.getMasterList().put(i);
                ObjectBinder.setObj(i);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

    }


}

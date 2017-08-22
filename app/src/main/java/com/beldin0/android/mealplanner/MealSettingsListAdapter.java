package com.beldin0.android.mealplanner;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.mealplanner.R;

/**
 * Created by beldi on 16/08/2017.
 */

public class MealSettingsListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private Week.Day[] _listDataHeader;

    public MealSettingsListAdapter(Context context, Week.Day[] week) {
        this._context = context;
        this._listDataHeader = week;
    }

    @Override
    public int getGroupCount() {
        return _listDataHeader.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listDataHeader[groupPosition].toString();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getChildView(groupPosition, childPosition, true, null, null);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.text_view);
        lblListHeader.setText(_listDataHeader[groupPosition].toString());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.meal_selector_sub, null);
        convertView.setTag(groupPosition);

        final CheckBox chkType = (CheckBox) convertView.findViewById(R.id.chk_Type);
        final TextView textType = (TextView) convertView.findViewById(R.id.spin_Type);
        final CheckBox chkMax = (CheckBox) convertView.findViewById(R.id.chk_Max);
        final SeekBar seekMax = (SeekBar) convertView.findViewById(R.id.seek_Max);

        chkType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    System.out.println("checked");
                    Dialog mt = mealTypeDialog();
                    mt.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            textType.setText((String) ObjectBinder.getObj());
                            ObjectBinder.clear();
                        }
                    });
                    mt.show();

                } else {
                    textType.setText("");
                }
            }
        });

        chkMax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    buttonView.setText("Max Cook time: ");
                } else {
                    buttonView.setText("Max Cook time: " + seekMax.getProgress() + " min");
                }
            }
        });

        seekMax.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return !chkMax.isChecked();
            }
        });
        seekMax.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                chkMax.setText("Max Cook time: " + progress + " min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    private Dialog mealTypeDialog() {
        final Dialog tmpDialog = new Dialog(_context);
        tmpDialog.setTitle("Select the meal type");
        tmpDialog.setContentView(R.layout.meal_type_selector);
        RadioGroup radio = (RadioGroup) tmpDialog.findViewById(R.id.radio_group);

        for (int i = 0; i < Meal.MealType.list().length; i++) {
            RadioButton rb = new RadioButton(_context);
            rb.setText(Meal.MealType.list()[i]);
            radio.addView(rb);
        }
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        ObjectBinder.setObj(btn.getText().toString());
                    }
                }
                tmpDialog.dismiss();
            }
        });
        return tmpDialog;
    }
}

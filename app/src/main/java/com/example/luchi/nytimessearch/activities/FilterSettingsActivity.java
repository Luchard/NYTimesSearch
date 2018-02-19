package com.example.luchi.nytimessearch.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.luchi.nytimessearch.R;

public class FilterSettingsActivity extends AppCompatActivity {
String sport;
String fashion;
String art;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_settings);
    }
    Spinner spSort;
public void addListenerOnSpinnerItemSelection(){
    spSort  = (Spinner) findViewById(R.id.mySpinner);
    spSort.setOnItemSelectedListener(new CustomOnItemSelectedListener());
}
    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        EditText etDate = (EditText) findViewById(R.id.etDate);
        addListenerOnSpinnerItemSelection();
        setupCheckboxes();

        CheckBox checkCart = (CheckBox) findViewById(R.id.checkbox_art);
// Check if the checkbox is checked
        boolean isChecked = checkCart.isChecked();
        if (isChecked)
        {
            art = "art";
        }
        else
        {
            art = "";
        }

        CheckBox checkSport = (CheckBox) findViewById(R.id.checkbox_sport);
// Check if the checkbox is checked
        boolean isCheckedSport = checkSport.isChecked();
        if (isCheckedSport)
        {
            sport = "sports";
        }
        else
        {
            sport = "";
        }
        CheckBox checkfashion = (CheckBox) findViewById(R.id.checkbox_fashion);
// Check if the checkbox is checked
        boolean isCheckedfashion = checkfashion.isChecked();
        if (isChecked)
        {
            fashion = "fashion & style";
        }
        else
        {
            fashion = "";
        }
String sort =  String.valueOf(spSort.getSelectedItem());

        // Prepare data intent



        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("date", etDate.getText().toString());
     //   data.putExtra("sort", spSort.get);
        data.putExtra("sorted", sort); // ints work too
        data.putExtra("sport", sport);
        data.putExtra("art", art);

        data.putExtra("fashion", fashion); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

    CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton view, boolean checked) {
            // compoundButton is the checkbox
            // boolean is whether or not checkbox is checked
            // Check which checkbox was clicked
            switch(view.getId()) {
                case R.id.checkbox_art :
                    if (checked) {
                       art = "Arts";
                    } else {
                        art = "";
                    }
                    break;
                case R.id.checkbox_fashion:
                    if (checked) {
                        fashion = "Fashion & Style" ;
                    } else {
                        fashion = "";
                    }
                    break;
                case R.id.checkbox_sport:
                    if (checked) {
                        sport = "Sports";
                    } else {
                        sport = "";
                    }
                    break;
            }
        }
    };

    public void setupCheckboxes() {

        CheckBox checkArt = (CheckBox) findViewById(R.id.checkbox_art);
        CheckBox checkFashion = (CheckBox) findViewById(R.id.checkbox_fashion);
        CheckBox checkSport = (CheckBox) findViewById(R.id.checkbox_sport);
        checkArt.setOnCheckedChangeListener(checkListener);
        checkFashion.setOnCheckedChangeListener(checkListener);
        checkSport.setOnCheckedChangeListener(checkListener);
    }
}

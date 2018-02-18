package com.example.luchi.nytimessearch.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by luchi on 2/17/2018.
 */

class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext() , "OnItemSelectedListener : " + parent.getItemIdAtPosition(position) , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

package com.android.roshan.gpacalc.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.roshan.gpacalc.R;

public class AddResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);


        final EditText input_sem_no = (EditText)findViewById(R.id.semester_no_picker);
        final EditText input_sub_code = (EditText)findViewById(R.id.input_subject_code);
        final EditText input_sub_name = (EditText)findViewById(R.id.input_subject_name);
        final EditText input_sub_credit = (EditText)findViewById(R.id.input_subject_credit);
        final Spinner spinner = (Spinner)findViewById(R.id.result_spinner);
    }
}

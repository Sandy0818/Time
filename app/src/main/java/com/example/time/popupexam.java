package com.example.time;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class popupexam extends AppCompatActivity {
    TextView exam1tv1;
    EditText exam1et1;
    TextView exam1tv2;
    EditText exam1et2;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currenthour;
    int currentminute;
    String ampm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupexam);

        exam1et2 = findViewById(R.id.exam1et2);
        exam1et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                currenthour=calendar.get(Calendar.HOUR_OF_DAY);
                currentminute=calendar.get(Calendar.MINUTE);

                timePickerDialog=new TimePickerDialog(popupexam.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay>=12){
                            ampm="PM";
                        }
                        else
                        {
                            ampm="AM";
                        }
                        exam1et2.setText(String.format("%02d:%02d",hourOfDay,minutes)+ampm);
                    }
                },currenthour, currentminute, false );
              timePickerDialog.show();
            }
        });

    }}

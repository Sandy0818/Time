package com.example.time;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ass2 extends AppCompatActivity {
    EditText et1;
    EditText et2;
    EditText et3;
    DatePickerDialog.OnDateSetListener setListener;
    Button addbutton;
    String st1;
    String st2;
    String st3;
    String st4;
    String st5;
    String st6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass2);
        addbutton=findViewById(R.id.addbutton);
        et1=findViewById(R.id.editText1);
        et2=findViewById(R.id.editText2);
        et3=findViewById(R.id.editText3);

        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        et2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        ass2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        et2.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ass2.this,ass1.class);
                st1=et1.getText().toString();
                st2=et2.getText().toString();
                st3=et3.getText().toString();
                i.putExtra("sub",st1);
                i.putExtra("date",st2);
                i.putExtra("desc",st3);
                startActivity(i);
                finish();
            }
        });
    }
}

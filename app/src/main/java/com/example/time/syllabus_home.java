package com.example.time;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class syllabus_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_home);
        FloatingActionButton add_syll = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        //get list of all subjects from db n create unique button for each subject
        Button new_syll = (Button) findViewById(R.id.syll_button);

        add_syll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), syllabus.class);
                startActivity(intent);
            }
        });

        new_syll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send subject name as extra data to activity
                Intent intent = new Intent(getApplicationContext(), view_syllabus.class);
                startActivity(intent);
            }
        });
    }
}




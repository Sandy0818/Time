package com.example.time;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class view_syllabus extends AppCompatActivity {

    String sub = "Machine Learning";                    //read subject from db or get it from calling subject
    ArrayList<String> topics = new ArrayList<>();       //read lest of topics of given subject from db

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_syllabus);
        TextView subject = (TextView) findViewById(R.id.subject_view);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.list_topics);

        subject.setText(sub);

        //get list from db and store in array list
        topics.add("linear regression");
        topics.add("logistic regression");
        topics.add("classification problems");
        topics.add("decision tree");

        //for each item in list and its status - checked or unchecked - display as checkbox
        for(int i = 0; i < topics.size(); i++)
        {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(topics.get(i));

            //set the checked value based on what is stored in db
            if(i % 2 == 0)
                cb.setChecked(true);
            else
                cb.setChecked(false);

            cb.setTextColor(Color.DKGRAY);
            cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cb.setTextSize(15);

            linearLayout.addView(cb);
        }


    }
}

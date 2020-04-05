package com.example.time;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class proj2 extends AppCompatActivity {
    List<HashMap<String, Object>> myMap  = new ArrayList<>();
    //List<String> time_list = new ArrayList<>();
    // List<String> task_list = new ArrayList<>();
    // List<Boolean> task_state = new ArrayList<>();
    Boolean task_state = false;
    EditText exam2et3, date_disp;
    TimePickerDialog timePickerDialog;
    LinearLayout ll;
    Calendar calendar;
    int currenthour;
    int currentminute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj2);

        //public class syllabus extends AppCompatActivity {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button exam2but1 = (Button) findViewById(R.id.exam2but1);
        final EditText exam2et1 = (EditText) findViewById(R.id.exam2et1);
        final EditText exam2et2 = (EditText) findViewById(R.id.exam2et2);
        date_disp = findViewById(R.id.exam_date);
        ll = findViewById(R.id.linear_list);
        Button exam2but2 = findViewById(R.id.exam2but2);

        //time picker
        exam2et3 = findViewById(R.id.exam2et3);

        try {
            exam2et3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calendar = Calendar.getInstance();
                    currenthour = calendar.get(Calendar.HOUR_OF_DAY);
                    currentminute = calendar.get(Calendar.MINUTE);

                    timePickerDialog = new TimePickerDialog(proj2.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                            exam2et3.setText(String.format("%02d:%02d", hourOfDay, minutes));
                        }
                    }, currenthour, currentminute, false);
                    timePickerDialog.show();
                }

            });
        } catch (Exception e) {
        }

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                updateLabel(dayOfMonth, monthOfYear, year);
            }

        };

        date_disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(proj2.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Toast.makeText(this, topic, Toast.LENGTH_SHORT).show();


        //adding new topic
        exam2but1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                final String task = exam2et2.getText().toString();
                final String time = exam2et3.getText().toString();

                HashMap<String, Object> dexa = new HashMap<>();
                dexa.put("topic",task);
                dexa.put("Time",time);
                dexa.put("checkbox state", false);

                //myMap.add(dexa);
                //exams.put("details",myMap);
                //Toast.makeText(getApplicationContext(), task, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), time, Toast.LENGTH_SHORT).show();

                //task_list.add(task);
                //time_list.add(time);
                //Toast.makeText(getApplicationContext(), "added" + time, Toast.LENGTH_SHORT).show();
                //task_state.add(false);

                sortv(time, task, dexa);
                display_list();

                exam2et2.setText("");
                exam2et2.setHint("Enter New Task");

                exam2et3.setText("");
                exam2et3.setHint("Enter the time you wanna study the topic");

            }
        });

        exam2but2.setOnClickListener(new View.OnClickListener() {


            // @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {

                //display_list();

                final String exam_sub = exam2et1.getText().toString();
                //final String topic_time = exam2et3.getText().toString();
                final String exam_date = date_disp.getText().toString();
                HashMap<String, Object> exams = new HashMap<>();
                exams.put("exam subject", exam_sub);
                exams.put("exam date", exam_date);
                exams.put("details", myMap);

                //HashMap<String, Object> exams = new HashMap<>();
                //exams.put("exam subject", exam_sub);
                //exams.put("exam date", exam_date);
                //exams.put("tasks", task_list);
                //exams.put("time", time_list);
                //exams.put("checkbox state", task_state);

                Log.d("EXAMS", "added - " + exams.toString());

                db.collection("users").document("user1").collection("Projects list").document(exam_sub)
                        .set(exams)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "DocumentSnapshot successfully written ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error writing document", Toast.LENGTH_SHORT).show();
                            }
                        });

                db.collection("users").document("user1").collection("Title").document("Title_d")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                try{
                                    String exam_list = (String) documentSnapshot.get("Projects");
                                    //Toast.makeText(, aat_list.toString(), Toast.LENGTH_LONG).show();

                                    if(exam_list == null || exam_list.isEmpty())
                                        exam_list = exam_sub;
                                    else
                                        exam_list = exam_list.concat(", " + exam_sub);

                                    Map<String, Object> subj = new HashMap<>();
                                    subj.put("Exams", exam_list);

                                    final String finalExam_list = exam_list;
                                    db.collection("users").document("user1").collection("Title").document("Title_d")
                                            .update(subj)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {


                                                    //Toast.makeText(getContext(), "Updated aat title list" + finalAat_list, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                catch(Exception e){}
                            }
                        });

            }
        });


    }


    public void sortv(String value, String task,Object dexa) {
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
        Log.d("EXAMS", value + " " + task + " size = " + myMap.size());

        int i;
        for(i = 0; i < myMap.size(); i++) {
            //for (HashMap<String, Object> map : myMap) {
            Map<String, Object> moo1 = myMap.get(i);
            if (moo1.get("Time").toString().compareTo(value) > 0)
                break;
        }

        myMap.add(i,(HashMap<String, Object>) dexa);
            //time_list.add(i,value);
            //task_list.add(i, task);

            Toast.makeText(getApplicationContext(), "added" + value, Toast.LENGTH_SHORT).show();
            Log.d("EXAMS", "added - " + myMap.toString());
        }

    private void updateLabel( int date, int month, int yr)
    {
        String slc_date = date + "-" + month + "-" + yr;
        date_disp.setText(slc_date);
    }

    public void display_list()
    {

        if (ll.getChildCount() > 0)
            ll.removeAllViews();


        for (int i = 0; i < myMap.size(); i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setId(i);
            HashMap<String,Object> moo = myMap.get(i);

            Log.d("EXAMS", moo.toString());

            cb.setText(moo.get("Time") + " - " + moo.get("topic"));
            cb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            cb.setTextColor(Color.DKGRAY);
            cb.setChecked((Boolean) moo.get("checkbox state"));
            //cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cb.setTextSize(15);

            ll.addView(cb);
        }
    }
}
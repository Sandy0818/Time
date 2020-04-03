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

public class exam2 extends AppCompatActivity {

    List<String> time_list = new ArrayList<>();
    List<String> task_list = new ArrayList<>();
    List<Boolean> task_state = new ArrayList<>();
    EditText exam2et3, date_disp;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    int currenthour;
    int currentminute;
    String ampm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam2);

        //public class syllabus extends AppCompatActivity {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button exam2but1 = (Button) findViewById(R.id.exam2but1);
        final EditText exam2et1 = (EditText) findViewById(R.id.exam2et1);
        final EditText exam2et2 = (EditText) findViewById(R.id.exam2et2);
        date_disp = findViewById(R.id.exam_date);
        final LinearLayout ll = findViewById(R.id.linear_list);
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

                    timePickerDialog = new TimePickerDialog(exam2.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        /*if(hourOfDay>=12){
                            ampm="PM";
                        }
                        else
                        {
                            ampm="AM";
                        }*/
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
                new DatePickerDialog(exam2.this, date, myCalendar
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

                //Toast.makeText(getApplicationContext(), task, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), time, Toast.LENGTH_SHORT).show();

                //task_list.add(task);
                //time_list.add(time);
                sortv(time, task);
                //Toast.makeText(getApplicationContext(), "added" + time, Toast.LENGTH_SHORT).show();
                task_state.add(false);

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

                for (int i = 0; i < time_list.size(); i++)
                {
                    CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setId(i);
                    cb.setText(time_list.get(i) + "-" + task_list.get(i));
                    cb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    cb.setTextColor(Color.DKGRAY);
                    cb.setChecked(task_state.get(i));
                    //cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    cb.setTextSize(15);

                    /*if(cb.getParent() != null) {
                        ((ViewGroup)cb.getParent()).removeView(cb); // <- fix
                    }*/

                    ll.addView(cb);
                }

                final String exam_sub = exam2et1.getText().toString();
                final String topic_time = exam2et3.getText().toString();
                final String exam_date = date_disp.getText().toString();

                HashMap<String, Object> exams = new HashMap<>();
                exams.put("exam subject", exam_sub);
                exams.put("exam date", exam_date);
                exams.put("tasks", task_list);
                exams.put("time", time_list);
                exams.put("checkbox state", task_state);


                db.collection("users").document("user1").collection("exams list").document(exam_sub)
                        .set(exams)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "DocumentSnapshot successfully written " + task_list, Toast.LENGTH_SHORT).show();
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
                                    String exam_list = (String) documentSnapshot.get("Exams");
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


    public void sortv(String value, String task)
    {
        Toast.makeText(getApplicationContext(), time_list.toString(), Toast.LENGTH_SHORT).show();
        Log.d("EXAMS", time_list.toString());

        int i;
        for(i = 0; i < time_list.size(); i++)
            if (time_list.get(i).compareTo(value) > 0)
                break;
        time_list.add(i,value);
        task_list.add(i, task);

        Toast.makeText(getApplicationContext(), "added" + value, Toast.LENGTH_SHORT).show();
        Log.d("EXAMS", "added - " + time_list.toString());
        Log.d("EXAMS", "added - " + task_list.toString());

    }

    private void updateLabel(int date, int month, int yr)
    {
        String slc_date = date + "-" + month + "-" + yr;
        date_disp.setText(slc_date);
    }

}

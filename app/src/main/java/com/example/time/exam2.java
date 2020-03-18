package com.example.time;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class exam2 extends AppCompatActivity {
    List<String> time_list = new ArrayList<>();
    List<String> task_list = new ArrayList<>();
    List<Boolean> task_state = new ArrayList<>();
    TextView exam2tv3;
    EditText exam2et3;
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
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linear_list);
                Button exam2but2 = findViewById(R.id.exam2but2);

        exam2et3 = findViewById(R.id.exam2et3);
        try{ exam2et3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                currenthour=calendar.get(Calendar.HOUR_OF_DAY);
                currentminute=calendar.get(Calendar.MINUTE);

                timePickerDialog=new TimePickerDialog(exam2.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay>=12){
                            ampm="PM";
                        }
                        else
                        {
                            ampm="AM";
                        }
                        exam2et3.setText(String.format("%02d:%02d",hourOfDay,minutes)+ampm);
                    }
                },currenthour, currentminute, false );
                timePickerDialog.show();
            }
        });}catch(Exception e){}

                //Toast.makeText(this, topic, Toast.LENGTH_SHORT).show();

                exam2but1.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onClick(View view) {
                        final String task = exam2et2.getText().toString();
                        final String time=exam2et3.getText().toString();
                        Toast.makeText(getApplicationContext(), task, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), time, Toast.LENGTH_SHORT).show();

                        task_list.add(task);
                        time_list.add(time);
                        task_state.add(false);

                        CheckBox cb = new CheckBox(getApplicationContext());
                        cb.setText(time+"-"+task);
                        cb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        cb.setTextColor(Color.DKGRAY);
                        cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        cb.setTextSize(15);

                        exam2et2.setText("");
                        exam2et2.setHint("Enter New Task");

                        ll.addView(cb);
                    }
                });

                exam2but2.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        final String exam_sub = exam2et1.getText().toString();
                        final String topic_time=exam2et3.getText().toString();
                        HashMap<String, Object> exams = new HashMap<>();
                        exams.put("exam subject", exam_sub);
                        exams.put("tasks", task_list);
                        exams.put("time",time_list);
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

                        //final DocumentReference docref = db.collection("users").document("user1").collection("Title").document("Title_d");

                       /* final FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                        rootRef.collection("users").document("user1").collection("Title").document("Title_d")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {try{
                                        final String sub_list = (String) documentSnapshot.get("Exams");
                                        Toast.makeText(getApplicationContext(), sub_list.toString(), Toast.LENGTH_LONG).show();
                                        final String sub_list2 = sub_list.concat(", " + exam_sub);
                                        Map<String, Object> subj = new HashMap<>();
                                        subj.put("Exams", sub_list2);

                                        rootRef.collection("users").document("user1").collection("Title").document("Title_d")
                                                .update(subj)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(getApplicationContext(), "Updated subject title list" + sub_list2, Toast.LENGTH_SHORT).show();
                                                    }
                                                });}catch(Exception e){}
                                    }
                                });*/
                        db.collection("users").document("user1").collection("Title").document("Title_d")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        try{
                                        String exam_list = (String) documentSnapshot.get("Exams");
                                        //Toast.makeText(, aat_list.toString(), Toast.LENGTH_LONG).show();

                                        if (!exam_list.equals(null)) {
                                            exam_list = exam_list.concat(", " + exam_sub);
                                        } else
                                            exam_list = exam_sub;

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
                                                });}catch(Exception e){}
                                    }
                                });

                    }
                    //catch (NullPointerException e)
                    //{

                    //  }
                });
    }}










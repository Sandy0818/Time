package com.example.time;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class proj2 extends AppCompatActivity {
    List<HashMap<String, Object>> myMap1 = new ArrayList<>();
    HashMap<String, Object> dexa1 = new HashMap<>();
    String proj_name;
    //List<String> time_list = new ArrayList<>();
    // List<String> task_list = new ArrayList<>();
    // List<Boolean> task_state = new ArrayList<>();
    String la,le;
    Boolean phase_state = false;
    EditText p2et3, date_disp1;
    TimePickerDialog timePickerDialog1;
    DatePickerDialog.OnDateSetListener setListener;
    LinearLayout ll;
    Calendar calendar;
    int currenthour;
    int currentminute;
    String ampm;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj2);

        //public class syllabus extends AppCompatActivity {
        //final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button p2but1 = (Button) findViewById(R.id.p2but1);
        final EditText p2et1 = (EditText) findViewById(R.id.p2et1);
        final EditText p2et2 = (EditText) findViewById(R.id.p2et2);
        date_disp1 = findViewById(R.id.p_date);
        ll = findViewById(R.id.linear_list);
        Button p2but2 = findViewById(R.id.p2but2);

        //date picker
        p2et3 = findViewById(R.id.p2et3);

       /* try {
            p2et3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calendar = Calendar.getInstance();
                    currenthour = calendar.get(Calendar.HOUR_OF_DAY);
                    currentminute = calendar.get(Calendar.MINUTE);

                    timePickerDialog = new TimePickerDialog(exam2.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                            exam2et3.setText(String.format("%02d:%02d", hourOfDay, minutes));
                        }
                    }, currenthour, currentminute, false);
                    timePickerDialog.show();
                }

            });
        } catch (Exception e) {
        }*/

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                updateLabel(dayOfMonth, monthOfYear + 1, year);
            }

        };

        date_disp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(proj2.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final Calendar myCalendar1 = Calendar.getInstance();

        //EditText edittext= (EditText) findViewById(R.id.Birthday);
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view1, int year1, int monthOfYear1,
                                  int dayOfMonth1) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year1);
                myCalendar1.set(Calendar.MONTH, monthOfYear1+1);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth1);

                updateLabel1(dayOfMonth1, monthOfYear1 + 1, year1);
            }

        };

        p2et3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(proj2.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Toast.makeText(this, topic, Toast.LENGTH_SHORT).show();


        //adding new topic
        p2but1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                final String phase = p2et2.getText().toString();
                final String date = p2et3.getText().toString();

                dexa1 = new HashMap<>();
                dexa1.put("phase",phase);
                dexa1.put("date",date);
                dexa1.put("checkbox state", false);

                try {
                    sortv(date, phase, dexa1);
                }
                catch (Exception e){}

                display_list();

                p2et2.setText("");
                //exam2et2.setHint("Enter New Task");

                p2et3.setText("");
                //exam2et3.setHint("Enter the time you wanna study the topic");

            }
        });

        p2but2.setOnClickListener(new View.OnClickListener() {


            // @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {

                //display_list();

                proj_name = p2et1.getText().toString();
                //final String topic_time = exam2et3.getText().toString();
                final String proj_date = date_disp1.getText().toString();
                HashMap<String, Object> projs = new HashMap<>();
                projs.put("project name", proj_name);
                projs.put("project date", proj_date);
                projs.put("details", myMap1);

                //HashMap<String, Object> exams = new HashMap<>();
                //exams.put("exam subject", exam_sub);
                //exams.put("exam date", exam_date);
                //exams.put("tasks", task_list);
                //exams.put("time", time_list);
                //exams.put("checkbox state", task_state);

                Log.d("PROJECTS", "added - " + projs.toString());

                db.collection("users").document("user3").collection("Projects list").document(proj_name)
                        .set(projs)
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

                db.collection("users").document("user3").collection("Title").document("Title_d")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                try{
                                    String proj_list = (String) documentSnapshot.get("Projects");
                                    //Toast.makeText(, aat_list.toString(), Toast.LENGTH_LONG).show();

                                    if(proj_list == null || proj_list.isEmpty())
                                        proj_list = proj_name;
                                    else
                                        proj_list = proj_list.concat(", " + proj_name);

                                    Map<String, Object> name = new HashMap<>();
                                    name.put("Projects", proj_list);

                                    final String finalproj_list = proj_list;
                                    db.collection("users").document("user3").collection("Title").document("Title_d")
                                            .update(name)
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


    public void sortv(String value1, String task1, Object dexa1) throws ParseException{
        int i = 0;
        Toast.makeText(getApplicationContext(), value1, Toast.LENGTH_SHORT).show();
        Log.d("proj", value1 + " " + task1 + " size = " + myMap1.size());

            SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");// format specified in double quotes
            Date d1, d2 = sobj.parse(value1);

            for(i = 0; i < myMap1.size(); i++)
            {

                Map<String, Object> p_obj = myMap1.get(i);

                Log.d("proj", p_obj.get("date").toString());

                d1 = sobj.parse( p_obj.get("date").toString());
                Log.d("proj", d1 + "\n" + d2);

                if(d2.before(d1))
                    break;
            }

        myMap1.add(i, (HashMap<String, Object>) dexa1);
        //time_list.add(i,value);
        //task_list.add(i, task);

        Toast.makeText(getApplicationContext(), "added" + value1, Toast.LENGTH_SHORT).show();
        Log.d("proj", "added - " + myMap1.toString());
    }

    private void updateLabel( int date, int month, int yr)
    {
        date_disp1.setText(String.format("%02d-%02d-%04d", date, month, yr));
    }
    private void updateLabel1(int date, int month, int yr)
    {
        p2et3.setText(String.format("%02d-%02d-%04d", date, month, yr));
    }

    public void display_list()
    {

        if (ll.getChildCount() > 0)
            ll.removeAllViews();


        for (int i = 0; i < myMap1.size(); i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setId(i);
            HashMap<String,Object> moo111 = myMap1.get(i);

            Log.d("proj", moo111.toString());

            cb.setText(moo111.get("date") + " - " + moo111.get("phase"));
            cb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            cb.setTextColor(Color.DKGRAY);
            cb.setChecked((Boolean) moo111.get("checkbox state"));
            //cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cb.setTextSize(15);
            cb.setOnLongClickListener(add_desc);
            ll.addView(cb);
        }
    }

    View.OnLongClickListener add_desc = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            final int cb_no = v.getId();
            Log.d("projects", myMap1.get(cb_no).toString());
            //Log.d("exam", time_list.get(cb_no));
            final HashMap<String,Object> moo = myMap1.get(cb_no);

            final String p_phase = (String) moo.get("phase");
            String p_date = (String) moo.get("date");

            AlertDialog.Builder builder1 = new AlertDialog.Builder(proj2.this);
            builder1.setTitle(p_phase + " DESCRIPTION");
            builder1.setCancelable(true);
            final EditText input = new EditText(proj2.this);
            input.setHint("Enter description for given phase");
            builder1.setView(input);

            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    String p_desc = input.getText().toString();
                    Log.d("projects", p_desc);

                    moo.put("desc", p_desc);
                    myMap1.set(cb_no, moo);
                    Log.d("projects", myMap1.toString());
                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alert11 = builder1.create();
            alert11.show();

            return true;
        }
    };
}
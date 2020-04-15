package com.example.time;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

@TargetApi(24)
public class exam3 extends AppCompatActivity {

    String sub = "";                    //read subject from db or get it from calling subject
    ArrayList<String> topic_list = new ArrayList<>();
    ArrayList<String> time_list = new ArrayList<>(); //read lest of topics of given subject from db
    ArrayList<Boolean> status = new ArrayList<>();
    List<HashMap<String, Object>> exam_map  = new ArrayList<>();
    Bundle extras;
    LinearLayout linearLayout;
    FloatingActionButton new_topic;

    Calendar calendar;
    int currenthour;
    int currentminute;
    TimePickerDialog timePickerDialog;

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam3);

        TextView subject = findViewById(R.id.subject_view);
        Button update_syll = findViewById(R.id.save_top);
        linearLayout = findViewById(R.id.list_topics);
        new_topic = findViewById(R.id.add_topic);
        final TextView e_date = findViewById(R.id.e_date);

        extras = getIntent().getExtras();
        if(extras == null)
            sub = null;
        else
            sub = (String) extras.get("Exam Subject");

            Log.d("EXAMS", sub);

            subject.setText(sub);

            final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document("user3").collection("exams list").document(sub)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Toast.makeText(getApplicationContext(), "Read document", Toast.LENGTH_SHORT).show();

                //topic_list = (ArrayList<String>) documentSnapshot.get("tasks");
                //time_list = (ArrayList<String>) documentSnapshot.get("time");
                //status = (ArrayList<Boolean>) documentSnapshot.get("checkbox state");
                exam_map = (List<HashMap<String, Object>>) documentSnapshot.get("details");
                String date = (String) documentSnapshot.get("exam date");

                e_date.setText("Exam Date: " + date);

                display_checklist();


                //Toast.makeText(getApplicationContext(), exam_map.toString(), Toast.LENGTH_SHORT).show();
            }
        });

            update_syll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("EXAMS", String.valueOf(linearLayout.getChildCount()));

                    int cb_count = linearLayout.getChildCount();

                    for(int i = 0; i < cb_count; i++)
                    {
                        CheckBox cb = (CheckBox) linearLayout.getChildAt(i);

                        if(cb.isChecked())
                            exam_map.get(i).replace("checkbox state", true);
                            //status.set(i, true);
                        else
                            exam_map.get(i).replace("checkbox state", false);
                            //status.set(i, false);
                    }

                    Log.d("EXAMS", exam_map.toString());

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("details", exam_map);

                    db.collection("users").document("user3").collection("exams list").document(sub)
                            .update(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Exam updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

            new_topic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(exam3.this);

                    alert.setTitle("Add New Checkbox");
                    alert.setMessage("Enter following details - ");

                    LinearLayout alert_ll = new LinearLayout(exam3.this);
                    alert_ll.setOrientation(LinearLayout.VERTICAL);
                    alert_ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    // Set an EditText view to get user input
                    final EditText et_topic = new EditText(exam3.this);
                    et_topic.setHint("Enter Topic name");
                    alert_ll.addView(et_topic);

                    final EditText et_time = new EditText(exam3.this);
                    et_time.setHint("Enter Time");
                    et_time.setFocusable(false);

                    try
                    {
                        et_time.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                calendar = Calendar.getInstance();
                                currenthour = calendar.get(Calendar.HOUR_OF_DAY);
                                currentminute = calendar.get(Calendar.MINUTE);

                                timePickerDialog = new TimePickerDialog(exam3.this, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                        et_time.setText(String.format("%02d:%02d", hourOfDay, minutes));
                                    }
                                }, currenthour, currentminute, false);
                                timePickerDialog.show();
                            }

                        });
                    }

                    catch (Exception e)
                    {
                    }

                    alert_ll.addView(et_time);

                    alert.setView(alert_ll);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String topic_name = et_topic.getText().toString();
                            String topic_time = et_time.getText().toString();
                            // Do something with value!
                            Log.d("exam", topic_name + " : " + topic_time);

                            HashMap<String, Object> temp = new HashMap<>();
                            temp.put("topic", topic_name);
                            temp.put("time", topic_time);
                            temp.put("checkbox state", false);

                            sortv(topic_time, temp);
                            display_checklist();
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });

                    alert.show();
                }
            });

    }

    View.OnLongClickListener deleteCB = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            final int cb_no = v.getId();
            //Log.d("exam", topic_list.get(cb_no));
            Log.d("exam", exam_map.get(cb_no).toString());

            AlertDialog.Builder builder1 = new AlertDialog.Builder(exam3.this);
            builder1.setMessage("Are you sure you want to delete - \n" + exam_map.get(cb_no).get("topic") + " : " + exam_map.get(cb_no).get("time"));
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            //remove from arraylists and display checklists again

                            //topic_list.remove(cb_no);
                            exam_map.remove(cb_no);
                            Toast.makeText(exam3.this, "Item successfully deleted", Toast.LENGTH_SHORT).show();
                            display_checklist();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            //do nothing
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

            return true;
        }
    };

    public void display_checklist()
    {
        if(linearLayout.getChildCount() > 0)
            linearLayout.removeAllViews();

        for(int i = 0; i < exam_map.size(); i++)
        {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setId(i);

            HashMap<String,Object> temp = exam_map.get(i);

            Log.d("EXAMS", temp.toString());

            cb.setText(temp.get("time") + " - " + temp.get("topic"));

            //set the checked value based on what is stored in db
            if((Boolean) temp.get("checkbox state"))
                cb.setChecked(true);
            else
                cb.setChecked(false);

            cb.setTextColor(Color.DKGRAY);
            //cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cb.setTextSize(18);
            cb.setFontFeatureSettings("@font/lemonada");

            cb.setOnLongClickListener(deleteCB);

            linearLayout.addView(cb);
        }
    }

    public void sortv(String value, HashMap<String, Object> new_ex)
    {
       //Toast.makeText(getApplicationContext(), time_list.toString(), Toast.LENGTH_SHORT).show();
        Log.d("EXAMS", exam_map.toString());

        int i;
        for(i = 0; i < exam_map.size(); i++)
        {
            HashMap<String, Object> temp = exam_map.get(i);
            if (temp.get("time").toString().compareTo(value) > 0)
                break;
        }

        exam_map.add(i, new_ex);

        Toast.makeText(getApplicationContext(), "added" + value, Toast.LENGTH_SHORT).show();
        //Log.d("EXAMS", "added - " + time_list.toString());
        //Log.d("EXAMS", "added - " + topic_list.toString());

    }
}

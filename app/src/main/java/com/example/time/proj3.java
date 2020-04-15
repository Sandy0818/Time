package com.example.time;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import androidx.appcompat.app.AppCompatActivity;

@TargetApi(24)
public class proj3 extends AppCompatActivity {

    String sub = "";                    //read subject from db or get it from calling subject

    List<HashMap<String, Object>> proj_map  = new ArrayList<>();
    Bundle extras;
    LinearLayout linearLayout;
    FloatingActionButton new_topic;

    EditText et_time, et_topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj3);

        TextView subject = findViewById(R.id.psubject_view);
        Button update_syll = findViewById(R.id.psave_top);
        linearLayout = findViewById(R.id.plist_topics);
        new_topic = findViewById(R.id.padd_topic);
        final TextView e_date = findViewById(R.id.proj_date);

        extras = getIntent().getExtras();
        if(extras == null)
            sub = null;
        else
            sub = (String) extras.get("Project_name");

        Log.d("proj", sub);

        subject.setText(sub);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document("user3").collection("Projects list").document(sub)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("proj", documentSnapshot.toString());

                        proj_map = (List<HashMap<String, Object>>) documentSnapshot.get("details");
                        String date = (String) documentSnapshot.get("project date");
                        Log.d("proj", proj_map.toString());

                        e_date.setText("Project Date: " + date);

                        display_checklist();

                        //Toast.makeText(getApplicationContext(), exam_map.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        update_syll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("proj", String.valueOf(linearLayout.getChildCount()));

                int cb_count = linearLayout.getChildCount();

                for(int i = 0; i < cb_count; i++)
                {
                    CheckBox cb = (CheckBox) linearLayout.getChildAt(i);

                    if(cb.isChecked())
                        proj_map.get(i).replace("checkbox state", true);
                        //status.set(i, true);
                    else
                        proj_map.get(i).replace("checkbox state", false);
                    //status.set(i, false);
                }

                Log.d("proj", proj_map.toString());

                HashMap<String, Object> data = new HashMap<>();
                data.put("details", proj_map);

                db.collection("users").document("user3").collection("Projects list").document(sub)
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Project updated", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        new_topic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(proj3.this);

                alert.setTitle("Add New Checkbox");
                alert.setMessage("Enter following details - ");

                LinearLayout alert_ll = new LinearLayout(proj3.this);
                alert_ll.setOrientation(LinearLayout.VERTICAL);
                alert_ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                // Set an EditText view to get user input
                et_topic = new EditText(proj3.this);
                et_topic.setHint("Enter Phase name");
                alert_ll.addView(et_topic);

                et_time = new EditText(proj3.this);
                et_time.setHint("Select Date");
                et_time.setFocusable(false);

                try
                {

                    final Calendar myCalendar1 = Calendar.getInstance();

                    //EditText edittext= (EditText) findViewById(R.id.Birthday);
                    final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view1, int year1, int monthOfYear1,
                                              int dayOfMonth1) {
                            // TODO Auto-generated method stub
                            myCalendar1.set(Calendar.YEAR, year1);
                            myCalendar1.set(Calendar.MONTH, monthOfYear1);
                            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth1);
                            updateLabel(dayOfMonth1, monthOfYear1 + 1, year1);
                        }

                    };
                    et_time.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            new DatePickerDialog(proj3.this, date1, myCalendar1
                                    .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                                    myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
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
                        Log.d("proj", topic_name + " - " + topic_time);

                        HashMap<String, Object> temp = new HashMap<>();
                        temp.put("phase", topic_name);
                        temp.put("date", topic_time);
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
            Log.d("exam", proj_map.get(cb_no).toString());

            AlertDialog.Builder builder1 = new AlertDialog.Builder(proj3.this);
            builder1.setMessage("Are you sure you want to delete - \n" + proj_map.get(cb_no).get("phase") + " : " + proj_map.get(cb_no).get("date"));
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            //remove from arraylists and display checklists again

                            //topic_list.remove(cb_no);
                            proj_map.remove(cb_no);
                            Toast.makeText(proj3.this, "Item successfully deleted", Toast.LENGTH_SHORT).show();
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

        for(int i = 0; i < proj_map.size(); i++)
        {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setId(i);

            HashMap<String,Object> temp = proj_map.get(i);

            Log.d("proj", temp.toString());

            cb.setText(temp.get("date") + " - " + temp.get("phase"));

            //set the checked value based on what is stored in db
            if((Boolean) temp.get("checkbox state"))
                cb.setChecked(true);
            else
                cb.setChecked(false);

            cb.setTextColor(Color.DKGRAY);
            //cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cb.setTextSize(18);
            //cb.setFontFeatureSettings("@font/lemonada");

            cb.setOnLongClickListener(deleteCB);

            linearLayout.addView(cb);
        }
    }

    public void sortv(String value, HashMap<String, Object> new_ex)
    {
        int i = 0;
        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
        Log.d("proj", value + " " + " size = " + proj_map.size());

        try {
            SimpleDateFormat sobj = new SimpleDateFormat("dd-MM-yyyy");// format specified in double quotes
            Date d1, d2 = sobj.parse(value);

            for(i = 0; i < proj_map.size(); i++)
            {

                Map<String, Object> p_obj = proj_map.get(i);

                Log.d("proj", p_obj.get("date").toString());

                d1 = sobj.parse( p_obj.get("date").toString());
                Log.d("proj", d1 + "\n" + d2);

                if(d2.before(d1))
                    break;
            }
        }
        catch (ParseException e){}


        proj_map.add(i, new_ex);

        Toast.makeText(getApplicationContext(), "added" + value, Toast.LENGTH_SHORT).show();
        Log.d("proj", "added - " + proj_map.toString());

    }

    private void updateLabel(int date, int month, int yr)
    {
        String slc_date1 = date + "-" + month + "-" + yr;
        et_time.setText(String.format("%02d-%02d-%04d", date, month, yr));
    }
}


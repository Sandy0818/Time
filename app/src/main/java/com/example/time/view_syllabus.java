package com.example.time;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class view_syllabus extends AppCompatActivity {

    String sub = "";                    //read subject from db or get it from calling subject
    ArrayList<String> topic_list = new ArrayList<>();       //read lest of topics of given subject from db
    ArrayList<Boolean> status = new ArrayList<>();
    Bundle extras;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_syllabus);
        TextView subject = (TextView) findViewById(R.id.subject_view);
        Button update_syll = (Button) findViewById(R.id.save_syll);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.list_topics);

        extras = getIntent().getExtras();
        if(extras == null)
            sub = null;
        else
            sub = (String) extras.get("Subject_name");

        Log.d("TAG", sub);
        subject.setText(sub);

        //get list from db and store in array list
        /*topics.add("linear regression");
        topics.add("logistic regression");
        topics.add("classification problems");
        topics.add("decision tree");*/

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document("user1").collection("syllabus list").document(sub)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Toast.makeText(getApplicationContext(), "Read document", Toast.LENGTH_SHORT).show();
                topic_list = (ArrayList<String>) documentSnapshot.get("topics");
                status = (ArrayList<Boolean>) documentSnapshot.get("checkbox state");
                Log.d("SYLLABUS", topic_list.toString());

                for(int i = 0; i < topic_list.size(); i++)
                {
                    CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setText(topic_list.get(i));
                    cb.setId(i);

                    //set the checked value based on what is stored in db
                    if(status.get(i) == true)
                        cb.setChecked(true);
                    else
                        cb.setChecked(false);

                    cb.setTextColor(Color.DKGRAY);
                    cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    cb.setTextSize(15);

                    linearLayout.addView(cb);
                }

            }
        });

        //for each item in list and its status - checked or unchecked - display as checkbox
        update_syll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SYLLABUS", String.valueOf(linearLayout.getChildCount()));

                int cb_count = linearLayout.getChildCount();

                for(int i = 0; i < cb_count; i++)
                {
                    CheckBox cb = (CheckBox) linearLayout.getChildAt(i);

                    if(cb.isChecked())
                        status.set(i, true);
                    else
                        status.set(i, false);
                }

                Log.d("SYLLABUS", status.toString());
                Log.d("SYLLABUS", topic_list.toString());

                HashMap<String, Object> data = new HashMap<>();
                data.put("checkbox state", status);
                data.put("topics", topic_list);

                db.collection("users").document("user1").collection("syllabus list").document(sub)
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Syllabus updated", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}

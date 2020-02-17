package com.example.time;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class syllabus extends AppCompatActivity {

    List<String> topic_list = new ArrayList<>();
    List<Boolean> topic_state = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button add_topic = (Button) findViewById(R.id.add_topic);
        final EditText sub_title = (EditText) findViewById(R.id.edit_subject);
        final EditText edit_topic = (EditText) findViewById(R.id.edit_topic);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linear_list);
        Button upload_syllabus = findViewById(R.id.submit_syllabus);

        //Toast.makeText(this, topic, Toast.LENGTH_SHORT).show();

        add_topic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                final String topic = edit_topic.getText().toString();
                Toast.makeText(getApplicationContext(), topic, Toast.LENGTH_SHORT).show();

                topic_list.add(topic);
                topic_state.add(false);

                CheckBox cb = new CheckBox(getApplicationContext());
                cb.setText(topic);
                cb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                cb.setTextColor(Color.DKGRAY);
                cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                cb.setTextSize(15);

                edit_topic.setText("");
                edit_topic.setHint("Enter New Topic");

                ll.addView(cb);
            }
        });

        upload_syllabus.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Map<String, Object> syllabus = new HashMap<>();

                final String syll_title = sub_title.getText().toString();

                syllabus.put("title", syll_title);
                syllabus.put("topics", topic_list);
                syllabus.put("checkbox state", topic_state);



                db.collection("users").document("user1").collection("syllabus list").document(syll_title)
                        .set(syllabus)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "DocumentSnapshot successfully written " + topic_list, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error writing document", Toast.LENGTH_SHORT).show();
                            }
                        });

                //final DocumentReference docref = db.collection("users").document("user1").collection("Title").document("Title_d");

                final FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                rootRef.collection("users").document("user1").collection("Title").document("Title_d")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Object sub_list = documentSnapshot.get("Subject");
                                Toast.makeText(getApplicationContext(), sub_list.toString(), Toast.LENGTH_LONG).show();
                                sub_list.add(syll_title);

                                rootRef.collection("users").document("user1").collection("Title").document("Title_d")
                                        .update("Subject", sub_list)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Updated subject title list", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });


                        /*.update("Subject", syll_title)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Created document", Toast.LENGTH_SHORT).show();
                    }
                });*/


            }
        });
    }
}

package com.example.time;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class exam3 extends AppCompatActivity {

    String sub = "";                    //read subject from db or get it from calling subject
    ArrayList<String> topic_list = new ArrayList<>();
    ArrayList<String> time_list = new ArrayList<>(); //read lest of topics of given subject from db
    ArrayList<Boolean> status = new ArrayList<>();
    Bundle extras;

    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam3);

        TextView subject = (TextView) findViewById(R.id.subject_view);
        Button update_syll = (Button) findViewById(R.id.save_top);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.list_topics);

        extras = getIntent().getExtras();
        if(extras == null)
            sub = null;
        else
            sub = (String) extras.get("Exam Subject");

            Log.d("EXAMS", sub);

            subject.setText(sub);

            final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document("user1").collection("exams list").document(sub)
                .get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Toast.makeText(getApplicationContext(), "Read document", Toast.LENGTH_SHORT).show();

                topic_list = (ArrayList<String>) documentSnapshot.get("tasks");
                time_list = (ArrayList<String>) documentSnapshot.get("time");
                status = (ArrayList<Boolean>) documentSnapshot.get("checkbox state");

                for(int i = 0; i < topic_list.size(); i++)
                {
                    CheckBox cb = new CheckBox(getApplicationContext());
                    cb.setText(topic_list.get(i) + " Time: " + time_list.get(i));
                    cb.setId(i);

                    //set the checked value based on what is stored in db
                    if(status.get(i) == true)
                        cb.setChecked(true);
                    else
                        cb.setChecked(false);

                    cb.setTextColor(Color.DKGRAY);
                    //cb.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    cb.setTextSize(15);

                    linearLayout.addView(cb);
                }

                //topic_list = Arrays.asList(topics);
                Toast.makeText(getApplicationContext(), topic_list.toString(), Toast.LENGTH_SHORT).show();
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
                            status.set(i, true);
                        else
                            status.set(i, false);
                    }

                    Log.d("EXAMS", status.toString());
                    Log.d("EXAMS", topic_list.toString());
                    Log.d("EXAMS", time_list.toString());

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("checkbox state", status);
                    data.put("tasks", topic_list);
                    data.put("time",time_list);

                    db.collection("users").document("user1").collection("exams list").document(sub)
                            .update(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Exam updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

    }
}

package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class syllabus_home extends AppCompatActivity {

    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_home);
        FloatingActionButton add_syll = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        LinearLayout layout = (LinearLayout) findViewById(R.id.lay1);

        //get list of all subjects from db n create unique button for each subject
        Button new_syll = (Button) findViewById(R.id.syll_button);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document("user1").collection("Title").document("Title_d")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String str = (String) documentSnapshot.get("Subject");
                        items = Arrays.asList(str.split("\\s*,\\s*"));

                    }
                });

        for(int i = 0; i < items.size(); i++)
        {
            Button button =new Button();
            button.setId(i);
            button.setText(items.get(i));
            

        }

        add_syll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), syllabus.class);
                startActivity(intent);
            }
        });

        new_syll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send subject name as extra data to activity
                Intent intent = new Intent(getApplicationContext(), view_syllabus.class);
                startActivity(intent);
            }
        });
    }
}




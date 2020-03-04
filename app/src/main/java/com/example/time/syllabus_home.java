package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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
        final LinearLayout layout = (LinearLayout) findViewById(R.id.lay1);

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

                                              Toast.makeText(getApplicationContext(), items.toString() + items.size(), Toast.LENGTH_SHORT).show();

                                              for (int i = 0; i < items.size(); i++)
                                                  Log.d("TAG", items.get(i));

                                              int len = items.size();
                                              Log.d("TAG", String.valueOf(len));
                                              for (int i = 0; i < len; i++) {
                                                  //Toast.makeText(getApplicationContext(), "inside for loop", Toast.LENGTH_SHORT).show();
                                                  //Toast.makeText(getApplicationContext(), items.get(i), Toast.LENGTH_SHORT).show();
                                                  Button button = new Button(getApplicationContext());
                                                  button.setId(i);
                                                  button.setText(items.get(i));
                                                  button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                                  Log.d("BUTTON", items.get(i));
                                                  layout.addView(button);

                                                  button.setOnClickListener(buttonClick);
                                              }
                                          }

                                          View.OnClickListener buttonClick = new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  String name = ((Button) v).getText().toString();
                                                  Toast.makeText(getApplicationContext(), "button name " + name, Toast.LENGTH_SHORT).show();
                                                  Intent intent = new Intent(getApplicationContext(), view_syllabus.class);
                                                  intent.putExtra("Subject_name", name);
                                                  startActivity(intent);
                                              }
                                          };
                                      });
        /*for(int i = 0; i < items.size(); i++)
        {
            Button button =new Button(this);
            button.setId(i);
            button.setText(items.get(i));
            

        }*/

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




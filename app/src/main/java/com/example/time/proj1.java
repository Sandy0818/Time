package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class proj1 extends AppCompatActivity implements CallBackListener {

    List<String> items;

    public void onCallBack() {
        Toast.makeText(this,"onCallback Called",Toast.LENGTH_LONG).show();
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e){}

       getFromDb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj1);

        FloatingActionButton add_exam = findViewById(R.id.new_exam);

        getFromDb();

        add_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProject();

            }
        });

    }

    public void openProject()
    {
        project_title_popup popup_dialog = new project_title_popup();
        popup_dialog.show(getSupportFragmentManager(),"example");
    }

    public void getFromDb()
    {
        final LinearLayout layout = (LinearLayout) findViewById(R.id.list_exams);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if((layout).getChildCount() > 0)
            (layout).removeAllViews();

        db.collection("users").document("user1").collection("Title").document("Title_d")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String str = (String) documentSnapshot.get("Projects");
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

                    //need to add fab for new AAT here

                    View.OnClickListener buttonClick = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = ((Button) v).getText().toString();
                            Toast.makeText(getApplicationContext(), "button name " + name, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), aat_display.class);
                            intent.putExtra("AAT_name", name);
                            startActivity(intent);
                        }
                    };
                });
    }
}



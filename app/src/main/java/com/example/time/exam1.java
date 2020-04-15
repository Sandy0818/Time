package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class exam1 extends AppCompatActivity {

    List<String> items;
    TextView textView;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam1);
        final FloatingActionButton add_sub = findViewById(R.id.add_topic);
        layout  = findViewById(R.id.lay2);

        getFromDB();

        add_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), exam2.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Callback from exam2", Toast.LENGTH_SHORT).show();
        if (resultCode == RESULT_OK && requestCode == 1)
        {
            getFromDB();
        }
    }

    private void getFromDB()
    {

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document("user3").collection("Title").document("Title_d")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        try{
                            String str = (String) documentSnapshot.get("Exams");
                            items = Arrays.asList(str.split("\\s*,\\s*"));

                            Toast.makeText(getApplicationContext(), items.toString() + items.size(), Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < items.size(); i++)
                                Log.d("TAG", items.get(i));

                            if(layout.getChildCount() > 0)
                                layout.removeAllViews();

                            int len = items.size();
                            Log.d("TAG", String.valueOf(len));
                            for (int i = 0; i < len; i++)
                            {
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
                        catch(Exception e){}

                    }



                    View.OnClickListener buttonClick = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = ((Button) v).getText().toString();
                            //Toast.makeText(getApplicationContext(), "button name " + name, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), exam3.class);
                            intent.putExtra("Exam Subject", name);
                            startActivity(intent);
                        }
                    };
                });
    }

}

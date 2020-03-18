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

public class work1 extends AppCompatActivity implements CallBackListener {

    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work1);

        readFromDb();

    }

    @Override
    public void onCallBack() {
        Toast.makeText(this,"onCallback Called",Toast.LENGTH_LONG).show();
        try{Thread.sleep(3000);}
        catch (InterruptedException e){}
        readFromDb();
    }

    public void readFromDb()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final LinearLayout layout = findViewById(R.id.work_layout);

        if((layout).getChildCount() > 0)
            (layout).removeAllViews();

        FloatingActionButton new_workshop = (FloatingActionButton) findViewById(R.id.add_aat);

        new_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_workshop workshop = new add_workshop();
                workshop.show(getSupportFragmentManager(), "example");
            }
        });

        db.collection("users").document("user1").collection("Title").document("Title_d")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String str = (String) documentSnapshot.get("Workshop");
                        items = Arrays.asList(str.split("\\s*,\\s*"));

                        Toast.makeText(getApplicationContext(), items.toString() + ' ' + items.size(), Toast.LENGTH_SHORT).show();

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
                            Intent intent = new Intent(getApplicationContext(), work_display.class);
                            intent.putExtra("Wshop_name", name);
                            startActivity(intent);
                        }
                    };
                });
    }
}

package com.example.time;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

public class aat_display extends AppCompatActivity {

    Bundle extras;
    String aat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aat_display);

        final TextView name = (TextView) findViewById(R.id.aat_name);
        final TextView date = (TextView) findViewById(R.id.aat_date);
        final TextView desc = (TextView) findViewById(R.id.aat_desc);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        extras = getIntent().getExtras();
        if(extras == null)
            aat = null;
        else
            aat = (String) extras.get("AAT_name");

        db.collection("users").document("user4").collection("aat list").document(aat)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d("AAT", documentSnapshot.toString());
                        name.setText(documentSnapshot.get("title").toString());

                        date.setText(documentSnapshot.get("date").toString());
                        desc.setText(documentSnapshot.get("desc").toString());
                    }
                });
    }
}

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class aat1 extends AppCompatActivity implements popup_dialog.Onaataddedlistener {
    TextView aat1tv1;
    TextView aat1tv2;
    FloatingActionButton aat1but2;
    String st2;
    String st3;
    List<String> items;

    @Override
    public void onaatadded(){
        getfromdb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aat1);

        aat1tv1=findViewById(R.id.aat1tv1);
        //aat1tv2=findViewById(R.id.aat1tv2);
        aat1but2=(FloatingActionButton)findViewById(R.id.aat1but2);

        String st1=null;

        try{st1=getIntent().getExtras().getString("sub"); aat1tv2.setText(st1);}
        catch(NullPointerException ignored){}

        getfromdb();


        aat1but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openActivity1();
                openDialog();
            }
        });
    }
    public void openActivity1()
    {
        Intent intent2 = new Intent(this, aat2.class);
        startActivity(intent2);
    }

    public <activity_popup_dialog> void openDialog()
    {
        popup_dialog popup_dialog = new popup_dialog();
        popup_dialog.show(getSupportFragmentManager(),"example");
    }
   public void getfromdb(){
       final LinearLayout layout = (LinearLayout) findViewById(R.id.lay1);
       FirebaseFirestore db = FirebaseFirestore.getInstance();

       db.collection("users").document("user1").collection("Title").document("Title_d")
               .get()
               .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                       String str = (String) documentSnapshot.get("AAT");
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
                            /*Intent intent = new Intent(getApplicationContext(), view_syllabus.class);
                            intent.putExtra("AAT_name", name);
                            startActivity(intent);*/
                       }
                   };
               });
   }

}


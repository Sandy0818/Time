package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class proj2 extends AppCompatActivity {
    private TextView proj2tv1;
    private TextView proj2tv2;
    private TextView proj2tv3;
    private EditText proj2et1;
    private EditText proj2et2;
    private Button addproj;
    private Button proj2check;
    private Button proj2timeline;
    String st1;
    String st2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj2);
        proj2tv1=findViewById(R.id.proj2tv1);
        proj2tv2=findViewById(R.id.proj2tv2);
        proj2tv3=findViewById(R.id.proj2tv3);
        proj2et1=findViewById(R.id.proj2et1);
        proj2et2=findViewById(R.id.proj2et2);
        addproj=findViewById(R.id.addproj);
        proj2check=findViewById(R.id.proj2check);
        proj2timeline = findViewById(R.id.proj2timelineButtons);


        proj2check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(proj2.this,proj3.class);
                st1=proj2et1.getText().toString();
                i1.putExtra("name",st1);
                startActivity(i1);
                finish();
            }
        });


        try
        {
            proj2timeline.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getApplicationContext(), "Timeline button clicked", Toast.LENGTH_SHORT).show();

                    /*Intent i2 = new Intent(proj2.this, timeline.class);
                    st2 = proj2et1.getText().toString();
                    i2.putExtra("name",st2);
                    startActivity(i2);*/
                    //finish();


                }
            });
        }
        catch (Exception e)
        {
            Log.d("YYY",e.getLocalizedMessage());
        }


        addproj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addproj();
            }
        });
    }
    private void addproj(){
        String name=proj2et1.getText().toString().trim();
        String topic=proj2et2.getText().toString().trim();
        if(!TextUtils.isEmpty(name)){
            Toast.makeText(this,"Project added",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"Enter a project name",Toast.LENGTH_LONG).show();

        }
    }
}

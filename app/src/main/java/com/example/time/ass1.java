package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ass1 extends AppCompatActivity {
    TextView ass1tv1;
    TextView ass1tv2;
    Button ass1but2;
    String st2;
    String st3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass1);
        ass1tv1=findViewById(R.id.aat1tv1);
        ass1tv2=findViewById(R.id.ass1tv2);
        ass1but2=findViewById(R.id.aat1but2);
        String st5=null;
        try{
            st5=getIntent().getExtras().getString("sub");  }
        catch(NullPointerException ignored){}
        try{ass1tv2.setText(st5);}
        catch(NullPointerException ignored){}
       try{ ass1tv2.setText(st5);}
       catch(NullPointerException ignored){}

        ass1but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity1();
            }
        });
    }
    public void openActivity1()
    {
        Intent intent2 = new Intent(this, ass2.class);
        startActivity(intent2);
    }}


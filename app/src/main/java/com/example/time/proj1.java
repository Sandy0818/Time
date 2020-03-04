package com.example.time;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class proj1 extends AppCompatActivity {
    TextView proj1tv1;
    TextView proj1tv2;
    Button proj1but2;
    String st2;
    String st3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj1);
        proj1tv1=findViewById(R.id.proj1tv1);
        proj1tv2=findViewById(R.id.proj1tv2);
        proj1but2=findViewById(R.id.proj1but2);
        String st1=null;
        try{st1=getIntent().getExtras().getString("name");}
        catch(NullPointerException ignored){}
        proj1tv2.setText(st1);

        proj1but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity1();
            }
        });
    }
    public void openActivity1()

    {
        Intent intent2 = new Intent(this, proj2.class);
        startActivity(intent2);
    }}



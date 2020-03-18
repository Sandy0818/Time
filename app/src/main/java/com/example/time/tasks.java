package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class tasks extends AppCompatActivity {
    private Button task1;
    private Button task2;
    private Button task3;
    private Button task4;
    private Button task5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        task1=findViewById(R.id.task1);
        task3=findViewById(R.id.task3);
        task4=findViewById(R.id.task4);
        task5=findViewById(R.id.task5);

        task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity1();
            }
        });

        task3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity3();
            }
        });

        task4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity4();
            }
        });

        task5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity5();
            }
        });
    }
    public void openActivity1()
    {
        Intent intent2 = new Intent(this, aat1.class);
        startActivity(intent2);
    }
    public void openActivity2()
    {
        Intent intent2 = new Intent(this, ass1.class);
        startActivity(intent2);
    }
    public void openActivity3()
    {
        Intent intent2 = new Intent(this,proj1.class);
        startActivity(intent2);
    }
    public void openActivity4()
    {
        Intent intent2 = new Intent(this, work1.class);
        startActivity(intent2);
    }
    public void openActivity5()
    {
        Intent intent2 = new Intent(this, exam1.class);
        startActivity(intent2);
    }
}



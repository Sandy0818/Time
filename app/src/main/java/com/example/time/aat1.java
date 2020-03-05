package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class aat1 extends AppCompatActivity {
    TextView aat1tv1;
    TextView aat1tv2;
    Button aat1but2;
    String st2;
    String st3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aat1);
        aat1tv1=findViewById(R.id.aat1tv1);
        aat1tv2=findViewById(R.id.aat1tv2);
        aat1but2=findViewById(R.id.aat1but2);
        String st1=null;

        try{st1=getIntent().getExtras().getString("sub");}
        catch(NullPointerException ignored){}
        aat1tv2.setText(st1);

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

}


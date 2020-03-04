package com.example.time;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public EditText email;
    public EditText pass;
    public Button login_bt, register_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.editText4);
        pass = findViewById(R.id.edittext21);
        login_bt = findViewById(R.id.button9);
        register_bt = findViewById(R.id.button2);

        Toast.makeText(this, "Home page opened", Toast.LENGTH_LONG).show();

        login_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String lemail = email.getText().toString().trim();
                String lpass = pass.getText().toString().trim();

                if (TextUtils.isEmpty(lemail) || TextUtils.isEmpty(lpass))
                    Toast.makeText(MainActivity.this, "Fields can't be empty", Toast.LENGTH_LONG).show();

                else {
                    Intent intent = new Intent(MainActivity.this, home.class);
                    startActivity(intent);
                }
            }
        });

        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,signupform.class);
                startActivity(intent);

            }
        });
    }



    /*public void login(View view) {
        try
        {
            String lemail = email.getText().toString().trim();
            String lpass = pass.getText().toString().trim();

            if (TextUtils.isEmpty(lemail) || TextUtils.isEmpty(lpass))
                Toast.makeText(MainActivity.this, "Fields can't be empty", Toast.LENGTH_LONG).show();
            else {


                Intent intent = new Intent(MainActivity.this, home.class);
                startActivity(intent);
            }
        }
        catch (IllegalStateException e)
        {

        }

    }


    public void btn_reg(View view){
        try {
            Intent intent = new Intent(MainActivity.this,signupform.class);
            startActivity(intent);
        }
        catch (IllegalStateException e)
        {

        }

    }*/
}






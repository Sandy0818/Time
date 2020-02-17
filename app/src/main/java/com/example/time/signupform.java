package com.example.time;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class signupform extends AppCompatActivity {

    public EditText uname;
    public EditText usn;
    public EditText email1;
    public EditText pass1;
    public EditText cpass1;
    public EditText sem1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupform);

        uname = (EditText) findViewById(R.id.editText);
        email1 = (EditText) findViewById(R.id.editText);
        usn = (EditText) findViewById(R.id.editText4);
        pass1 = (EditText) findViewById(R.id.edittext21);
        cpass1 = (EditText) findViewById(R.id.editText5);
        sem1 = (EditText) findViewById(R.id.editText6);
    }
    public void home(View view) {

        String unames = uname.getText().toString().trim();
        String emails = email1.getText().toString().trim();
        String usns = usn.getText().toString().trim();
        String passs = pass1.getText().toString().trim();
        String cpasss = cpass1.getText().toString().trim();
        String sems = sem1.getText().toString().trim();

        if (TextUtils.isEmpty(unames) || TextUtils.isEmpty(emails) || TextUtils.isEmpty(usns) || TextUtils.isEmpty(passs) || TextUtils.isEmpty(cpasss) || TextUtils.isEmpty(sems))
            Toast.makeText(signupform.this, "Fields can't be empty", Toast.LENGTH_LONG).show();
        else {
            Intent intent = new Intent(signupform.this, home.class);
            startActivity(intent);
        }
    }
}

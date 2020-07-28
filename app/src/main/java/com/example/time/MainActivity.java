
package com.example.time;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonlogin;
    private TextView textViewSignup;
    ProgressBar progressbar;

    //if(firebaseAuth.getCurrentUser() != null){
      //  startActivity(new Intent(getApplicationContext(),home.class));
       // finish();
    //}

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

         if(firebaseAuth.getCurrentUser() != null){

             FirebaseUser user = firebaseAuth.getCurrentUser();
             Log.d("login", user.getUid());
        //that means user is already logged in
        //so close this activity
        finish();

        //and open profile activity
             Intent intent = new Intent(getApplicationContext(), home.class);
             intent.putExtra("user_id", user.getUid());
             startActivity(intent);
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonlogin = (Button) findViewById(R.id.buttonlogin);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);
        //progressDialog = new ProgressDialog(this);

        //attaching listener to button
        //buttonSignup.setOnClickListener(this);
        progressbar = findViewById(R.id.progressBar2);
        textViewSignup.setOnClickListener(this);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                Log.d("LOGIN", email + " " + password);

                if (TextUtils.isEmpty(email)) {
                    editTextEmail.setError("Please enter email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editTextPassword.setError("Please enter password");
                    return;
                }
                if(password.length()<6)
                {
                    editTextPassword.setError("password must be more than 6 characters");
                    return;
                }

                progressbar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.d("login", user.getUid());

                            Toast.makeText(MainActivity.this,"User Logged in", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), home.class);
                            intent.putExtra("user_id", user.getUid());
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view==textViewSignup){
            startActivity(new Intent(this, signupform.class));
        }
    }
}


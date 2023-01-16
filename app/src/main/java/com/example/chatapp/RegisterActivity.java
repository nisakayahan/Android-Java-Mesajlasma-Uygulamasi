package com.example.chatapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button button1_registerpage, button2_registerpage;
    EditText email_register, password_register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button1_registerpage = (Button) findViewById(R.id.button1_registerpage);
        button2_registerpage = (Button) findViewById(R.id.button2_registerpage);
        email_register = (EditText) findViewById(R.id.email_register);
        password_register = (EditText) findViewById(R.id.password_register);
        auth = FirebaseAuth.getInstance();

       button1_registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                String email = email_register.getText().toString();
                String password = password_register.getText().toString();

                if (email.isEmpty()) {
                    email_register.setError("Email bos olamaz");
                }
                if(password.isEmpty()) {
                    password_register.setError("Password bos olamaz");
                }
                else{
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Kayit basarili", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                //finish();
                            }

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    }
                }


        });

       button2_registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                //finish();
            }

        });






    }
}












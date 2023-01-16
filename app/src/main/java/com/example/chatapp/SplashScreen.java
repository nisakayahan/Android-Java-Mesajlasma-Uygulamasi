package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    Thread wait_thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        SplashThread();

       if(firebaseAuth.getCurrentUser() != null){
           Toast.makeText(SplashScreen.this, "daha once giris yapildi, home page sayfasina yonlendiriliyorsunuz", Toast.LENGTH_SHORT).show();
           wait_thread.start();
       }
       else{
           startActivity(new Intent(SplashScreen.this, MainActivity.class));
       }
    }

    public void SplashThread(){
         wait_thread = new Thread() {
             @Override
             public void run() {
                 try{
                     sleep(2000);
                     Intent intent = new Intent(SplashScreen.this, NavigationDrawerActivity.class);
                     startActivity(intent);
                   //  finish();
                 }
                 catch(InterruptedException e){
                     e.printStackTrace();
                 }
             }

         };
    }
}















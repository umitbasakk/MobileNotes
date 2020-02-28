package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

public class SplashScreen extends AppCompatActivity {

            private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


        ActionBar bar = getSupportActionBar();
        bar.hide();






    }

    public  void login(View view){

        Intent intent = new Intent(SplashScreen.this,UserLogin.class);
        startActivity(intent);

    }

    public void register(View view){

        Intent intent = new Intent(SplashScreen.this, UserRegister.class);
        startActivity(intent);

    }




}


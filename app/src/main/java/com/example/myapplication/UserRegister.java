package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserRegister extends AppCompatActivity {

    private FirebaseAuth mauth;
    EditText emailText;
    EditText passwordText;
    String name, password;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        mauth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        button = findViewById(R.id.button);

        emailText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.button), PorterDuff.Mode.SRC_ATOP);
        passwordText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.button), PorterDuff.Mode.SRC_ATOP);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("User Register");

    }


    public void register(View view){

        name  = emailText.getText().toString();
        password = passwordText.getText().toString();

        mauth.createUserWithEmailAndPassword(name,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getApplicationContext(),"Success, one second Redirecting...",Toast.LENGTH_SHORT).show();


                CountDownTimer timer = new CountDownTimer(1500,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }.start();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

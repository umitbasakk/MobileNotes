package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity {

    private FirebaseAuth mauth;
    static  FirebaseUser user;

    EditText emailText,passwordText;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mauth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        user = mauth.getCurrentUser();

        emailText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.button), PorterDuff.Mode.SRC_ATOP);
        passwordText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.button), PorterDuff.Mode.SRC_ATOP);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("User Login");






    }


    public void login(View view){

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        mauth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(getApplicationContext(),"Success, one second Redirecting...",Toast.LENGTH_SHORT).show();


                CountDownTimer timer = new CountDownTimer(1500,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        Intent intent = new Intent(UserLogin.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }.start();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(),"Email or password is wrong\n",Toast.LENGTH_SHORT).show();

            }
        });




    }

    public void register(View view){

        Intent intent = new Intent(getApplicationContext(),UserRegister.class);
        startActivity(intent);

    }

}

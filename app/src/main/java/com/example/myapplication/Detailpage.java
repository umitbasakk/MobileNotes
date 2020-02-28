package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Detailpage extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    TextView textView,textView2,copys;
    ClipboardManager clipboardManager;
    String title,notes;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailpage);

        textView2 = findViewById(R.id.textView2);
        textView = findViewById(R.id.textView);
        copys = findViewById(R.id.copys);

        Intent intent = getIntent();
        title =  intent.getStringExtra("title");
        notes = intent.getStringExtra("notes");
        key = intent.getStringExtra("key");

        ActionBar bar = getSupportActionBar();
        bar.setTitle(title);

         textView2.setText(notes);
         textView.setText("Key : "+key);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

    }



            public void copy(View view){



                    if(!key.equals("")){

                        ClipData clipData = ClipData.newPlainText("text",key);
                        clipboardManager.setPrimaryClip(clipData);

                        Toast.makeText(getApplicationContext(),"Key : "+key,Toast.LENGTH_SHORT).show();
                    }

            }





}

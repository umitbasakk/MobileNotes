package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class Addnotes extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private  FirebaseUser firebaseUser;
    String title,notes;


    EditText titleText;
    EditText notesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnotes);

        titleText = findViewById(R.id.titleText);
        notesText = findViewById(R.id.notesText);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Add note");


        titleText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.button), PorterDuff.Mode.SRC_ATOP);
        notesText.getBackground().mutate().setColorFilter(getResources().getColor(R.color.button), PorterDuff.Mode.SRC_ATOP);

    }


    public void save(View view){




         title = titleText.getText().toString();
         notes = notesText.getText().toString();
        String uid = firebaseUser.getUid();

        int titlelenght = title.length();
        int noteslenght = notes.length();



        if(title.matches("") && notes.matches("")){
            Toast.makeText(this,"Boş",Toast.LENGTH_SHORT).show();
        }else if(titlelenght < 5 && noteslenght < 5) {
            Toast.makeText(this,"5den falza",Toast.LENGTH_SHORT).show();

        }else {

            String key = (String) UUID.randomUUID().toString();


                HashMap<String, Object> data = new HashMap<>();
                data.put("title", title);
                data.put("notes", notes);
                data.put("uid", uid);
                data.put("data", FieldValue.serverTimestamp());
                data.put("key",key);


                firebaseFirestore.collection("Notes").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Addnotes.this, MainActivity.class);
                        startActivity(intent);

                        titleText.setText("");
                        notesText.setText("");



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                MainActivity.adapter.notifyDataSetChanged();
            }

    }






}

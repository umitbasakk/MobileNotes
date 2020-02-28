package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.Map;

public class SearchNotes extends AppCompatActivity {

    EditText keynote;
    TextView note;
    String key;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notes);

        keynote = findViewById(R.id.keynote);
        note = findViewById(R.id.textView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        keynote.getBackground().mutate().setColorFilter(getResources().getColor(R.color.button), PorterDuff.Mode.SRC_ATOP);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Search Notes");



    }


    public  void searchnote(View view){

        key = keynote.getText().toString();

        CollectionReference collectionReference = firebaseFirestore.collection("Notes");
        collectionReference.whereEqualTo("key",key).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                if(queryDocumentSnapshots != null){

                    for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){

                        Map<String,Object> data = snapshot.getData();
                        String notes = (String) data.get("notes");
                        note.setText(notes);
                    }



                }



            }
        });




    }





}

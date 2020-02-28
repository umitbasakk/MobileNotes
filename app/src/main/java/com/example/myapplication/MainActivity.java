package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mauth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> notetitles,keys;

    ArrayList<String> noteslist;
    ListView listView;
    static ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // Action Bar Name
           ActionBar bar = getSupportActionBar();
           bar.setTitle("Notes");


           // Firebase Instance
          mauth = FirebaseAuth.getInstance();
          firebaseFirestore = FirebaseFirestore.getInstance();

          // Listview Find
          listView = findViewById(R.id.listView);

          // New ArrayList for title and note
          notetitles = new ArrayList<>();
          noteslist = new ArrayList<>();
          keys = new ArrayList<>();

          FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
          String uid = firebaseUser.getUid();



          // Firebase data select
          CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Notes");
          collectionReference.whereEqualTo("uid",uid).orderBy("data", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
              @Override
              public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if(queryDocumentSnapshots != null){
                        for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){

                            Map<String,Object> data  =  snapshot.getData();

                            if( data == null){

                                System.out.println("Veri yok");

                            }else{
                                String title =  (String) data.get("title");
                                String notes = (String) data.get("notes");
                                String key = (String) data.get("key");

                                notetitles.add(title);
                                noteslist.add(notes);
                                keys.add(key);
                            }


                        }
                    }


                  adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,notetitles);
                  listView.setAdapter(adapter);


                  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                          Intent intent = new Intent(getApplicationContext(),Detailpage.class);
                          intent.putExtra("title",notetitles.get(position));
                          intent.putExtra("notes",noteslist.get(position));
                          intent.putExtra("key",keys.get(position));
                          startActivity(intent);

                      }
                  });

              }
          });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
       menuInflater.inflate(R.menu.add_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.add_menus){

            Intent intent = new Intent(MainActivity.this,Addnotes.class);
            startActivity(intent);

        } else if(item.getItemId() == R.id.signout){

            mauth.signOut();
            Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
            startActivity(intent);


        }else if(item.getItemId() == R.id.searchnote){

            Intent intent = new Intent(getApplicationContext(),SearchNotes.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}

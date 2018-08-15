package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.accueil.myfamilyphotoalbum.Controller.Constants;
import com.example.accueil.myfamilyphotoalbum.Controller.MyAdapter;
import com.example.accueil.myfamilyphotoalbum.model.Content;
import com.example.accueil.myfamilyphotoalbum.model.PictureFactory;
import com.example.accueil.myfamilyphotoalbum.model.TextFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//Package Name: "com.example.accueil.myfamilyphotoalbum"
//SHA1: "c2eacfa8a575c1e4d69862704e00b955981887ca"



    private RecyclerView recyclerView;
    private RecyclerView.Adapter rVAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Firebase references
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseFirestore db;


    //list to hold all the uploaded images
    private List<Content> rowListItem;
    private TextView uName;
    private String userName;
    private static final String TAG ="#MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uName = this.findViewById(R.id.userName);
        //FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(Constants.DATABASE_PATH_CONTENTS);
        Query query = mDatabase.child(Constants.DATABASE_PATH_ALL_UPLOADS);


        rowListItem = getAllItemList();


        updateUI(currentUser);
        uName.setText(userName);


        recyclerView = this.findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        rVAdapter = new MyAdapter(rowListItem);
        recyclerView.setAdapter(rVAdapter);

/**
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

**/
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                rowListItem.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String  = postSnapshot.getValue(Content.class);

                    rowListItem.add(content);
                    rVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        menu.findItem(R.id.album).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }



    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.myProfile :
                startActivity(new Intent(MainActivity.this,MyProfileActivity.class));
                break;

            case R.id.addPic:
                Intent intent =new Intent(MainActivity.this,AddPictureActivity.class);
                startActivity(intent);
                break;

            case R.id.addText:
                Intent intentText =new Intent(MainActivity.this,AddTextActivity.class);
                startActivity(intentText);
                break;

            case R.id.logOut:
                mAuth.signOut();
                finish();
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    private List<Content> getAllItemList(){

        final List<Content> allItems = new ArrayList<>();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Content mContent = (Content) document.getData();
                                allItems.add(mContent);
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return allItems;
    }
    private void updateUI(FirebaseUser user) {
        if (user == null) {
            finish();
            startActivity(new Intent(MainActivity.this,WelcomeActivity.class));

        } else {
            userName = user.getDisplayName();
        }
    }

}

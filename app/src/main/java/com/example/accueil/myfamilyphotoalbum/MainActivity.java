package com.example.accueil.myfamilyphotoalbum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.accueil.myfamilyphotoalbum.Controller.MyAdapter;
import com.example.accueil.myfamilyphotoalbum.model.Content;
import com.example.accueil.myfamilyphotoalbum.model.Picture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//Package Name: "com.example.accueil.myfamilyphotoalbum"
//SHA1: "c2eacfa8a575c1e4d69862704e00b955981887ca"

    static final int ID_MY_PROFILE = 1;
    static final int ID_ADD_CONTENT = 2;
    static final int ID_LOG_OUT = 3;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter rVAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Firebase references
    //private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<Content> rowListItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        recyclerView = this.findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        rVAdapter = new MyAdapter(rowListItem);
        recyclerView.setAdapter(rVAdapter);




    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add(0,ID_MY_PROFILE,0, R.string.profile);
        menu.add(0,ID_ADD_CONTENT,0, R.string.addContent);
        menu.add(0,ID_LOG_OUT,0, R.string.logOut);

        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case ID_MY_PROFILE :

                break;

            case ID_ADD_CONTENT:

                break;
            case ID_LOG_OUT:
                finish();

                break;


        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Signed in

        } else {
            finish();
            startActivity(new Intent(MainActivity.this,SplashScreen.class));

        }
    }

}

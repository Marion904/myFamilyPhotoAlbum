package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddTextActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    FirebaseAuth mAuth;
    String type;
    TextView contentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_text);

        contentType=this.findViewById(R.id.contentType);
        contentType.setText(R.string.addTextTitle);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);



    }

    @Override
    public void onStart() {
        super.onStart();


        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            finish();
            startActivity(new Intent(AddTextActivity.this,WelcomeActivity.class));

        } else {

        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        menu.findItem(R.id.addContent).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }




    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){


            case R.id.album:
                startActivity(new Intent(AddTextActivity.this,MainActivity.class));
                break;
            case R.id.myProfile :
                startActivity(new Intent(AddTextActivity.this,MyProfileActivity.class));
                break;


            case R.id.logOut:
                mAuth.signOut();
                finish();
                break;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

    }
}

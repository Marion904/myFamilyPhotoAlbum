package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    public Button signIn, signUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if (mAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(this, MainActivity.class));
        }

        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if( view == signIn){
            startActivity(new Intent(WelcomeActivity.this,SignInActivity.class));
            finish();
        }else if(view == signUp){
            startActivity(new Intent(WelcomeActivity.this,SignUpActivity.class));
            finish();
        }

    }
}

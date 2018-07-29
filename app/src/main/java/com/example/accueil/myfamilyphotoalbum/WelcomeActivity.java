package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    public Button signIn, signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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

package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIMEOUT=3000;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //initializing firebase auth object
                mAuth = FirebaseAuth.getInstance();
                //mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_USERS);



                //if getCurrentUser does not returns null
                if (mAuth.getCurrentUser() != null) {
                    //that means user is already logged in
                    //so close this activity
                                        //and open profile activity
                    finish();
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                Intent i = new Intent(SplashScreen.this,WelcomeActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);

    }

}

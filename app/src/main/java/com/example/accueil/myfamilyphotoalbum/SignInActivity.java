package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accueil.myfamilyphotoalbum.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    EditText eMailLogIn, pwdLogIn ;
    TextView logInTV;
    User user;
    Button newUserButton, logInButton, forgotPwdButton;
    private static final String TAG = "SignInActivity";
    boolean sendConnection=false;
    FirebaseAuth mAuth;
    String eMailContent,pwdContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        eMailLogIn = this.findViewById(R.id.eMailLogIn);
        pwdLogIn = this.findViewById(R.id.pwdLogIn);
        newUserButton = this.findViewById(R.id.newUser);
        logInButton = this.findViewById(R.id.connect);
        forgotPwdButton = this.findViewById(R.id.forgotPwd);
        logInTV = this.findViewById(R.id.logInTV);

        newUserButton.setOnClickListener(this);
        logInButton.setOnClickListener(this);
        forgotPwdButton.setOnClickListener(this);
        mAuth =FirebaseAuth.getInstance();


        //if getCurrentUser does not returns null
        if (mAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(SignInActivity.this, MainActivity.class));

        }

    }

    public boolean checkContents(String eMailContent,String pwdContent) {

        if (pwdContent.isEmpty()) {
            Toast.makeText(this, getString(R.string.noPwd), Toast.LENGTH_LONG).show();
            return false;
        } else {
            boolean checkEmail = EmailValidator.getInstance(false).isValid(eMailContent);
            //boolean checkUser = EmailValidator.getInstance(false).isValidUser(eMailContent);
            if (!checkEmail) {
                Toast.makeText(this, getString(R.string.incorrectEmail), Toast.LENGTH_LONG).show();
            }
            return checkEmail;
        }
    }

    public User updateUI(FirebaseUser user){
        String eMail = user.getEmail();
        String userName = user.getDisplayName();

        return new User(eMail,userName,pwdContent);
    }

    public void signInUser(String email,String pwd){
        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, getString(R.string.logInOk));
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            user = updateUI(fUser);
                            startActivity(new Intent(SignInActivity.this,MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, getString(R.string.logInKo), task.getException());
                            Toast.makeText(SignInActivity.this,getString(R.string.failure),
                                    Toast.LENGTH_SHORT).show();
                            //user=null;
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newUser:
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                finish();
                break;
            case R.id.connect:
                eMailContent = eMailLogIn.getText().toString();
                pwdContent = pwdLogIn.getText().toString();
                if(checkContents(eMailContent,pwdContent)){
                    signInUser(eMailContent,pwdContent);
                }
                break;
            case R.id.forgotPwd:
                startActivity(new Intent(SignInActivity.this,ForgotPwdActivity.class));
                finish();
                break;

        }
    }
}

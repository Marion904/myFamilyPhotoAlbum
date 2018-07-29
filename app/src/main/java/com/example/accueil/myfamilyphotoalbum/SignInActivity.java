package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.validator.routines.EmailValidator;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    EditText eMailLogIn, pwdLogIn ;
    TextView logInTV;
    Button newUserButton, logInButton, forgotPwdButton;
    boolean sendConnection=false;


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

    }

    public boolean checkContents(){
        String eMailContent = eMailLogIn.getText().toString();
        String pwdContent = pwdLogIn.getText().toString();
        if (pwdContent.isEmpty()){
            Toast.makeText(this,getString(R.string.noPwd),Toast.LENGTH_LONG).show();
            return false;
        }else {
            boolean checkEmail = EmailValidator.getInstance(false).isValid(eMailContent);
            //boolean checkUser = EmailValidator.getInstance(false).isValidUser(eMailContent);
            if(!checkEmail){
                Toast.makeText(this,getString(R.string.incorrectEmail),Toast.LENGTH_LONG).show();
            }
            return checkEmail;
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.newUser:
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                break;
            case R.id.connect:
                /**
                if(checkContents(eMailLogIn,pwdLogIn)){

                }**/
                    startActivity(new Intent(SignInActivity.this,WelcomeActivity.class));

                break;
            case R.id.forgotPwd:
                startActivity(new Intent(SignInActivity.this,ForgotPwd.class));
                break;

        }
    }
}

package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.validator.routines.EmailValidator;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText firstName,userName,eMailSignUp,pwdSignUp,pwdConfirm;
    Button signIn,register,cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstName=findViewById(R.id.firstName);
        userName=findViewById(R.id.userName);
        eMailSignUp=findViewById(R.id.eMailSignUp);
        pwdSignUp = findViewById(R.id.pwdSignUp);
        pwdConfirm = findViewById(R.id.pwdConfirm);

        signIn = findViewById(R.id.goToSignIn);
        register = findViewById(R.id.confirmSignUp);
        cancel = findViewById(R.id.cancelSignUp);

        signIn.setOnClickListener(this);
        register.setOnClickListener(this);
        cancel.setOnClickListener(this);



    }

    public boolean checkUserInfo(){
        String fName = firstName.getText().toString();
        String uName = userName.getText().toString();
        String eMailContent = eMailSignUp.getText().toString();
        String pwdContent = pwdSignUp.getText().toString();
        String pwd2 = pwdConfirm.getText().toString();
        if (pwdContent.isEmpty()){
            Toast.makeText(this,getString(R.string.noPwd),Toast.LENGTH_LONG).show();
            return false;
        } else if(uName.isEmpty()) {
            Toast.makeText(this, getString(R.string.noUserName), Toast.LENGTH_LONG).show();
            return false;
        }else if (!pwd2.equals(pwdContent)){
            Toast.makeText(this, getString(R.string.notMatchingPwd), Toast.LENGTH_LONG).show();
            return false;
        }else {
            boolean checkEmail = EmailValidator.getInstance(false).isValid(eMailContent);
            if(!checkEmail){
                Toast.makeText(this,getString(R.string.incorrectEmail),Toast.LENGTH_LONG).show();
            }
            return checkEmail;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goToSignIn:
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                break;

            case R.id.confirmSignUp:
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                break;

            case R.id.cancelSignUp:
                startActivity(new Intent(SignUpActivity.this,WelcomeActivity.class));
                break;


        }

    }
}

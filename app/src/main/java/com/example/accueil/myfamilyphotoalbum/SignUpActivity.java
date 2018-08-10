package com.example.accueil.myfamilyphotoalbum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.accueil.myfamilyphotoalbum.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.apache.commons.validator.routines.EmailValidator;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    //private DatabaseReference mDatabase;
    private static final String TAG = "SignUpActivity";
    private static final String DATABASE_PATH_USERS = "user";


    EditText firstName,userName,eMailSignUp,pwdSignUp,pwdConfirm;
    Button signIn,register,cancel;
    User user;
    String fName, uName, eMailContent, pwdContent, pwd2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_USERS);



        //if getCurrentUser does not returns null
        if (mAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(this, MainActivity.class));
        }
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

    public boolean checkUserInfo(String uName,String eMail,String pwd,String pwd2){

        if (pwd.isEmpty()){
            Toast.makeText(this,getString(R.string.noPwd),Toast.LENGTH_SHORT).show();
            return false;
        } else if(uName.isEmpty()) {
            Toast.makeText(this, getString(R.string.noUserName), Toast.LENGTH_SHORT).show();
            return false;
        }else if (!pwd2.equals(pwd)){
            Toast.makeText(this, getString(R.string.notMatchingPwd), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            boolean checkEmail = EmailValidator.getInstance(false).isValid(eMail);
            if(!checkEmail){
                Toast.makeText(this,getString(R.string.incorrectEmail),Toast.LENGTH_SHORT).show();
            }

            return checkEmail;
        }
    }

    public void createUser(User user){
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPwd())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(uName)

                                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                    .build();

                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("TAG", "User profile update.");
                                        finish();
                                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, getString(R.string.signUpFailure), task.getException());
                                        Toast.makeText(SignUpActivity.this, getString(R.string.failure), Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }
                                }
                            });
                        }


                    }
                });

        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.goToSignIn:
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                finish();
                break;

            case R.id.confirmSignUp:

                fName = firstName.getText().toString();
                uName = userName.getText().toString();
                eMailContent = eMailSignUp.getText().toString();
                pwdContent = pwdSignUp.getText().toString();
                pwd2 = pwdConfirm.getText().toString();
                if(checkUserInfo(uName,eMailContent,pwdContent,pwd2)){
                    if(fName.isEmpty()){
                        user = new User(eMailContent,uName,pwdContent);

                    }else{
                        user = new User(eMailContent,uName,fName, pwdContent);
                    }
                    createUser(user);
                }
                break;

            case R.id.cancelSignUp:
                startActivity(new Intent(SignUpActivity.this,WelcomeActivity.class));
                finish();
                break;
        }
    }


}

package com.example.accueil.myfamilyphotoalbum;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.validator.routines.EmailValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

// 1
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
// 2
@RunWith(RobolectricTestRunner.class)
public class SignUpActivityTest {

    private Activity activity;

    // 3
    @Before
    public void setup() {
        activity = Robolectric.buildActivity(SignUpActivity.class).create().get();
    }




    @Test
    public void signInButtonClick() {
        Button forgotPwdButton = activity.findViewById(R.id.goToSignIn);
        forgotPwdButton.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(SignInActivity.class.getName()));
    }

    @Test
    public void validateSignUpButtonClick() {
        Button newUserButton = activity.findViewById(R.id.confirmSignUp);
        newUserButton.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(MainActivity.class.getName()));
    }


    @Test
    public void validateCancelButtonClick() {

        Button logInButton = activity.findViewById(R.id.cancelSignUp);
        logInButton.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(WelcomeActivity.class.getName()));
    }
/**
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertEquals(EmailValidator.getInstance().isValid("name@email.com"), true);
        assertNotEquals(EmailValidator.getInstance().isValid("name@emailcom"), true);
        assertNotEquals(EmailValidator.getInstance().isValid("nameemail.com"), true);
    }
 **/

    @Test
    public void checkContentValidator() {
        SignUpActivity myActivity = Robolectric.setupActivity(SignUpActivity.class);
        EditText eMailSignUp = myActivity.findViewById(R.id.eMailSignUp);
        EditText pwdSignUp = myActivity.findViewById(R.id.pwdSignUp);
        EditText pwdConfirm = myActivity.findViewById(R.id.pwdConfirm);
        EditText fName = myActivity.findViewById(R.id.firstName);
        EditText userN = myActivity.findViewById(R.id.userName);
        eMailSignUp.setText("");
        pwdSignUp.setText("");
        pwdConfirm.setText("");
        userN.setText("");
        assertEquals(myActivity.checkUserInfo(), false);
        eMailSignUp.setText("marionmail.com");
        pwdSignUp.setText("ijiojipo");
        pwdConfirm.setText("ijiojipo");
        userN.setText("Moi");
        assertEquals(myActivity.checkUserInfo(), false);
        eMailSignUp.setText("marion@gmail.com");
        pwdSignUp.setText("ijiojipo");
        pwdConfirm.setText("ijiojipo");
        userN.setText("");
        assertEquals(myActivity.checkUserInfo(), false);
        eMailSignUp.setText("marion@gmail.com");
        pwdSignUp.setText("ijiojipo");
        pwdConfirm.setText("ijojipo");
        userN.setText("Moi");
        assertEquals(myActivity.checkUserInfo(), false);
        eMailSignUp.setText("marion@gmail.com");
        pwdSignUp.setText("ijiojipo");
        pwdConfirm.setText("ijiojipo");
        userN.setText("Moi");
        assertEquals(myActivity.checkUserInfo(), true);
    }

}
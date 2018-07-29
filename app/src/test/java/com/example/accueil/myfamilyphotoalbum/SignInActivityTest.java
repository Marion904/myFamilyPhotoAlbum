package com.example.accueil.myfamilyphotoalbum;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;


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
public class SignInActivityTest {

    private Activity activity;

    // 3
    @Before
    public void setup() {
        activity = Robolectric.buildActivity(SignInActivity.class).create().get();
    }






    @Test
    public void validateLogInTVContent() {
        TextView logInTV = activity.findViewById(R.id.logInTV);
        String logIn = activity.getResources().getString(R.string.logIn);
        // 5
        assertNotNull("TextView is null", logInTV);
        // 6
        assertTrue("TextView's text does not match.", logIn.equals(logInTV.getText().toString()));
    }


    @Test
    public void forgotPwdButtonClick() {
        Button forgotPwdButton = activity.findViewById(R.id.forgotPwd);
        forgotPwdButton.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(ForgotPwd.class.getName()));
    }

    @Test
    public void validateNewUserButtonClick() {
        Button newUserButton = activity.findViewById(R.id.newUser);
        newUserButton.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(SignUpActivity.class.getName()));
    }


    @Test
    public void validateLogInButtonClick() {

        Button logInButton = activity.findViewById(R.id.connect);
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

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertEquals(EmailValidator.getInstance().isValid("name@email.com"), true);
        assertNotEquals(EmailValidator.getInstance().isValid("name@emailcom"), true);
        assertNotEquals(EmailValidator.getInstance().isValid("nameemail.com"), true);
    }

    @Test
    public void checkContentValidator() {
        SignInActivity myActivity =Robolectric.setupActivity(SignInActivity.class);
        EditText eMailLogIn = myActivity.findViewById(R.id.eMailLogIn);
        EditText pwdLogIn = myActivity.findViewById(R.id.pwdLogIn);
        eMailLogIn.setText("");
        pwdLogIn.setText("");
        assertEquals(myActivity.checkContents(),false);
        eMailLogIn.setText("marion@gmail");
        pwdLogIn.setText("ijiopji");
        assertEquals(myActivity.checkContents(),true);
        eMailLogIn.setText("mariongmail.com");
        pwdLogIn.setText("jiojipo");
        assertEquals(myActivity.checkContents(),false);
        eMailLogIn.setText("marion@gmail.com");
        pwdLogIn.setText("");
        assertEquals(myActivity.checkContents(),false);
        eMailLogIn.setText("marion@gmail.com");
        pwdLogIn.setText("kopkpo");
        assertEquals(myActivity.checkContents(),true);

    }



}

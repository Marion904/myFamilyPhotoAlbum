package com.example.accueil.myfamilyphotoalbum;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

// 1
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
// 2
@RunWith(RobolectricTestRunner.class)
public class WelcomeActivityTest {

    private Activity activity;

    // 3
    @Before
    public void setup() {
        activity = Robolectric.buildActivity(WelcomeActivity.class).create().get();
    }


    //5
    @Test
    public void validateSignInButtonClick() {
        Button signIn = activity.findViewById(R.id.signIn);
        // 7
        signIn.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(SignInActivity.class.getName()));
    }

    //5
    @Test
    public void validateSignUpButtonClick() {
        Button signUp = activity.findViewById(R.id.signUp);
        // 7
        signUp.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(SignUpActivity.class.getName()));
    }

}

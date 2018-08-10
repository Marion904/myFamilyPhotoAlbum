package com.example.accueil.myfamilyphotoalbum;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
class SignInActivityInstrumentedTest {

    @Rule
    public IntentsTestRule<SignInActivity> mSignInIntentsTestRule = new IntentsTestRule<>(SignInActivity.class);
    //public ActivityTestRule<SignInActivity> rule = new ActivityTestRule<>(SignInActivity.class);
        private String useremail_valid="koko";
        private String correct_password ="kokokoko";
        private String wrong_password = "kokokiki";

         @Test
         private void login_success(){
                Intents.init();
                Log.e("@Test","Performing login success test");
                Espresso.onView((withId(R.id.eMailLogIn)))
                        .perform(ViewActions.typeText(useremail_valid));

                Espresso.onView(withId(R.id.pwdLogIn))
                        .perform(ViewActions.typeText(correct_password));

                Espresso.onView(withId(R.id.connect))
                        .perform(ViewActions.click());

             mSignInIntentsTestRule.launchActivity(new Intent());
             intended(hasComponent(MainActivity.class.getName()));
             intended(hasComponent(SignInActivity.class.getName()), times(0));
             Intents.release();


        }
}

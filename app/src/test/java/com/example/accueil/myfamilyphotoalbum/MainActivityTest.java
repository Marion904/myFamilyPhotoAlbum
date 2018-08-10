package com.example.accueil.myfamilyphotoalbum;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

// 1
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.O)
// 2
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private Activity activity;

    // 3
    @Before
    public void setup() {

        activity = Robolectric.buildActivity(MainActivity.class).create().get();

    }


    //5
    @Test
    public void validateOptionMenuAddContent() {
        View addContent = activity.findViewById(R.id.addContent);
        // 7
        addContent.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(AddPictureActivity.class.getName()));
    }

    //6
    @Test
    public void validateOptionMenuMyProfile() {
        View addContent = activity.findViewById(R.id.myProfile);
        // 7
        addContent.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        assertThat(shadowIntent.getIntentClass().getName(), equalTo(MyProfileActivity.class.getName()));
    }

    //7
    @Test
    public void validateOptionMenuLogOut() {
        View addContent = activity.findViewById(R.id.logOut);
        // 7
        addContent.performClick();
        // 8
        ShadowActivity shadowActivity = shadowOf(activity);
        // 9
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        // 10
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        // 11
        Assert.assertTrue(shadowActivity.isFinishing());
    }
}
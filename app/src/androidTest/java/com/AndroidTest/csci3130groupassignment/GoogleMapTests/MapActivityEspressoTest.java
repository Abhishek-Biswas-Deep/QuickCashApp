package com.AndroidTest.csci3130groupassignment.GoogleMapTests;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.GrantPermissionRule;

import com.Main.csci3130groupassignment.Maps.GoogleMaps;
import com.example.csci3130groupassignment.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;

public class MapActivityEspressoTest {
    @Rule
    public ActivityScenarioRule<GoogleMaps> activityRule = new ActivityScenarioRule<>(GoogleMaps.class);


    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION);

    @Test
    public void mapFragmentIsDisplayed() {
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    @Test
    public void activityLaunchesWithIntentAndDisplaysUIElements() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GoogleMaps.class);
        intent.putExtra("EXTRA_LATITUDE", 37.4220);
        intent.putExtra("EXTRA_LONGITUDE", -122.0841);
        intent.putExtra("EXTRA_JOB_NAME", "Googleplex");
    }

}


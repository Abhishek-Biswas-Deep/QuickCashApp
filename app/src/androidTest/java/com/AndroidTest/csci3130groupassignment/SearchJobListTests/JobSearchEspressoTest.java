package com.AndroidTest.csci3130groupassignment.SearchJobListTests;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.Main.csci3130groupassignment.Activites.JobRecyclerViewActivity;
import com.example.csci3130groupassignment.R;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JobSearchEspressoTest {

    ActivityScenario<JobRecyclerViewActivity> scenario;

    @Test
    public void JobType1(){
        scenario = ActivityScenario.launch(JobRecyclerViewActivity.class);
        scenario.onActivity(activity -> {
        });

        onView(withId(R.id.JobTypeSpinner)).perform(click());
        onView(withText("Manual Labour")).perform(click());
        onView(withId(R.id.JobSearchButton)).perform(click());
        onView(withId(R.id.JobRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void JobType2(){
        scenario = ActivityScenario.launch(JobRecyclerViewActivity.class);
        scenario.onActivity(activity -> {
        });

        onView(withId(R.id.JobTypeSpinner)).perform(click());
        onView(withText("Technical")).perform(click());
        onView(withId(R.id.JobSearchButton)).perform(click());
        onView(withId(R.id.JobRecyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void JobType3(){
        scenario = ActivityScenario.launch(JobRecyclerViewActivity.class);
        scenario.onActivity(activity -> {
        });

        onView(withId(R.id.JobTypeSpinner)).perform(click());
        onView(withText("Healthcare")).perform(click());
        onView(withId(R.id.JobSearchButton)).perform(click());
        onView(withId(R.id.JobRecyclerView)).check(matches(isDisplayed()));
    }

}


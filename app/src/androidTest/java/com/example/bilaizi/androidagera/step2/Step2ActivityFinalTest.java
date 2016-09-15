package com.example.bilaizi.androidagera.step2;

import android.content.pm.ActivityInfo;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bilaizi.androidagera.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Step2ActivityFinalTest {
    @Rule
    public ActivityTestRule<Step2ActivityFinal> mTasksActivityTestRule =
            new ActivityTestRule<>(Step2ActivityFinal.class);

    @Test
    public void incrementButtonPressed_textIsUpdated() {
        onView(withId(R.id.step2_value_tv)).check(matches(withText("0")));
        onView(withId(R.id.increment_bt)).perform(click());
        onView(withId(R.id.step2_value_tv)).check(matches(withText("1")));
    }

    @Test
    public void rotationPersistence() {
        mTasksActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onView(withId(R.id.step2_value_tv)).check(matches(withText("0")));
        onView(withId(R.id.increment_bt)).perform(click());
        onView(withId(R.id.step2_value_tv)).check(matches(withText("1")));
        mTasksActivityTestRule.getActivity().setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.increment_bt)).perform(click());
        onView(withId(R.id.step2_value_tv)).check(matches(withText("2")));
    }


}
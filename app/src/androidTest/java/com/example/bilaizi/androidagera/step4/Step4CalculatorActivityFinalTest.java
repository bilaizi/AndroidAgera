package com.example.bilaizi.androidagera.step4;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.SeekBar;

import com.example.bilaizi.androidagera.R;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.bilaizi.androidagera.step4.ThreadPoolIdlingResource.newThreadPoolIdlingResource;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Step4CalculatorActivityFinalTest {
    private IdlingResource mIdlingResource;
    @Rule
    public ActivityTestRule<CalculatorActivityFinal> mTasksActivityTestRule =
            new ActivityTestRule<CalculatorActivityFinal>(CalculatorActivityFinal.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent(InstrumentationRegistry.getInstrumentation()
                            .getTargetContext(), CalculatorActivityFinal.class);
                    intent.putExtra(CalculatorActivityFinal.ANIMATIONS_ENABLED_KEY, false);
                    return intent;
                }
            };

    @Before
    public void registerIdlingResource() {
        mIdlingResource = newThreadPoolIdlingResource(CalculatorExecutor.EXECUTOR, "resultRepo");
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

    @Test
    public void initial_shows_NA() {
        String notAvailableResource = mTasksActivityTestRule.getActivity()
                .getResources().getString(R.string.not_available);
        onView(withId(R.id.textViewResult)).check(matches(withText(notAvailableResource)));
    }

    @Test
    public void calculator_add() throws InterruptedException {
        onView(withId(R.id.radioButtonAdd)).perform(click());
        onView(withId(R.id.seekBar1)).perform(setProgress(10));
        onView(withId(R.id.seekBar2)).perform(setProgress(32));
        onView(withId(R.id.textViewResult)).check(matches(withText("42")));
    }

    @Test
    public void divisionByZero() {
        String divZeroResource = mTasksActivityTestRule.getActivity()
                .getResources().getString(R.string.div_zero);
        onView(withId(R.id.seekBar1)).perform(setProgress(50));
        onView(withId(R.id.seekBar2)).perform(setProgress(0));
        onView(withId(R.id.radioButtonDiv)).perform(click());
        onView(withId(R.id.textViewResult)).check(matches(withText(divZeroResource)));
    }

    public static ViewAction setProgress(int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }

            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }
}
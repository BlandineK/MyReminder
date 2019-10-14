package com.moringashool.myreminder;


import android.view.View;

import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.IsNot.not;
@RunWith(AndroidJUnit4.class)

public class RemindersActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<RemindersActivity> activityTestRule =
            new ActivityTestRule<>(RemindersActivity.class);

//    @Test
//    public void listItemClickDisplaysToastWithCorrectReminder() {
//        View activityDecorView = activityTestRule.getActivity().getWindow().getDecorView();
//        String reminderName = "Reminder1";
//        onData(anything())
                .inAdapterView(withId(R.id.listView))
                .atPosition(0)
                .perform(click());
        onView(withText(reminderName)).inRoot(withDecorView(not(activityDecorView)))
                .check(matches(withText(reminderName)));
    }
}

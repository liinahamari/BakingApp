package com.example.guest.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;


import com.example.guest.bakingapp.ui.DetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by l1maginaire on 5/16/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailActivityIntentTest {

    private static final String EXTRA_RECIPE_ID_KEY = "RECIPE_ID";
    private static final int EXTRA_RECIPE_ID_VALUE = 1;

    private static final String EXTRA_STEP_ID_KEY = "STEP_ID";
    private static final int EXTRA_STEP_ID_VALUE = 1;

    @Rule
    public IntentsTestRule<DetailActivity> intentsTestRule
            = new IntentsTestRule<>(DetailActivity.class);

    @Test
    public void clickOnRecyclerViewItem_runsRecipeStepActivityIntent() {

        onView(ViewMatchers.withId(R.id.detail_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(allOf(
                hasExtra(EXTRA_RECIPE_ID_KEY, EXTRA_RECIPE_ID_VALUE),
                hasExtra(EXTRA_STEP_ID_KEY, EXTRA_STEP_ID_VALUE)
        ));
    }
}

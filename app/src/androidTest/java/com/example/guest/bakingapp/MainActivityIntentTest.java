package com.example.guest.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.guest.bakingapp.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;

/**
 * Created by l1maginaire on 5/16/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityIntentTest {
    private static final String ID = "id";
    private static final int EXTRA_RECIPE_ID_VALUE = 1;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule
            = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(intentsTestRule.getActivity().getIdlingResource());
    }

    @Test
    public void clickOnRecyclerViewItem_runsRecipeDetailsActivityIntent() {
        onView(ViewMatchers.withId(R.id.main_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasExtra(ID, EXTRA_RECIPE_ID_VALUE));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(intentsTestRule.getActivity().getIdlingResource());
    }
}

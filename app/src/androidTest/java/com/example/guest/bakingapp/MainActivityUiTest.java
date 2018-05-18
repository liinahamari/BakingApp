package com.example.guest.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.guest.bakingapp.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by l1maginaire on 5/14/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUiTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(
            MainActivity.class);
    private static final String NUTELLA_PIE = "Nutella Pie";

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(activityTestRule.getActivity().getIdlingResource());
    }


    @Test
    public void recipeListIsDisplayedAfterDataFetching(){
        onView(withId(R.id.main_recycler))
                .check(matches(isDisplayed()));
    }

    @Test
    public void recipeListAfterDataFetchingHasNutellaPie() {
        onView(withId(R.id.main_recycler))
                .perform(RecyclerViewActions.scrollToPosition(0));

        onView(withText(NUTELLA_PIE))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecyclerViewItem_opensRecipeDetailsActivity() {
        onView(withId(R.id.main_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredients_tv))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(activityTestRule.getActivity().getIdlingResource());
    }
}

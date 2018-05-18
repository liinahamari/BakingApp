package com.example.guest.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.guest.bakingapp.ui.DetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by l1maginaire on 5/16/18.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DetailActivityUiTest {
    private static final int STEP_WITH_VIDEO = 0;
    private static final int STEP_WITHOUT_VIDEO = 1;

    @Rule
    public ActivityTestRule<DetailActivity> activityActivityTestRule = new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void clickOnRecyclerViewItem_opensRecipeStepActivity() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.detail_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_step_viewpager))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepWithVideo_showsVideoPlayerView() {

        onView(withId(R.id.detail_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(STEP_WITH_VIDEO, click()));

        onView(allOf(withId(R.id.video_view), withParent(withParent(withId(R.id.recipe_step_viewpager))), isDisplayed()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnStepWithoutVideo_hidesVideoPlayerView() {

        onView(withId(R.id.detail_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(STEP_WITHOUT_VIDEO, click()));
        onView(allOf(withId(R.id.video_view), withParent(withParent(withId(R.id.recipe_step_viewpager))), isDisplayed()))
                                                                                                            .check(doesNotExist());
    }
}

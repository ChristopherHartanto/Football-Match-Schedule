package com.example.user.footballmatchschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.user.footballmatchschedule.R.id.*

import com.example.user.footballmatchschedule.home.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.IdlingRegistry
import com.example.user.footballmatchschedule.util.SimpleCountingIdlingResource
import org.junit.After
import org.junit.Before



@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        IdlingRegistry.getInstance().register(SimpleCountingIdlingResource())

        onView(withId(list_event))
                .check(matches(isDisplayed()))
        onView(withId(list_event)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(list_event)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))  // akan mengskroll sampai posisi ke 10

        IdlingRegistry.getInstance().unregister(SimpleCountingIdlingResource())
    }

    @Test
    fun testAppBehaviour() {
        IdlingRegistry.getInstance().register(SimpleCountingIdlingResource())

        onView(withId(spinner))
                .check(matches(isDisplayed()))
        onView(withId(spinner)).perform(click())

        onView(withText("English Premier League")).perform(click())
        onView(withText("Mon, 05 Nov 2018"))
                .check(matches(isDisplayed()))
        onView(withText("Mon, 05 Nov 2018")).perform(click())

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())

        pressBack()

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(lastmatch)).perform(click())

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(nextmatch)).perform(click())

        onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(favorites)).perform(click())

        IdlingRegistry.getInstance().unregister(SimpleCountingIdlingResource())
    }

}
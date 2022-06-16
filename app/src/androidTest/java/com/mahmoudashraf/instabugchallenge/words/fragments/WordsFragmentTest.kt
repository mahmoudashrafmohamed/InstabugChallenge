package com.mahmoudashraf.instabugchallenge.words.fragments

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.mahmoudashraf.instabugchallenge.MainActivity
import com.mahmoudashraf.instabugchallenge.R
import com.mahmoudashraf.instabugchallenge.words.adapter.WordsAdapter
import org.junit.Rule
import org.junit.Test


class WordsFragmentTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_isWordRecyclerViewVisible_onAppLaunch() {
        Espresso.onView(withId(R.id.rv_words))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_isProgressBarVisible_onAppLaunch() {
        Espresso.onView(withId(R.id.progress_bar))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_isSortBtnVisible_onAppLaunch() {
        Espresso.onView(withId(R.id.img_sort))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_wordRecycler_scrolling() {
        Thread.sleep(7000)
        Espresso.onView(withId(R.id.rv_words))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(33)
            )
    }

    @Test
    fun test_wordRecycler_data() {
        Thread.sleep(7000)
        Espresso.onView(withId(R.id.rv_words))
            .perform(
                RecyclerViewActions.scrollTo<WordsAdapter.WordViewHolder>(
                    hasDescendant(withText("ios"))
                )
            )
    }

}
